/*
 * GamePlay class (Activity!)
 *
 * Class implements the activity that the game is played in.
 * Handles all visualization and menu item presses in the activity.
 * Communicates with the Game class to provide a complete playable game.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GamePlay extends AppCompatActivity {

    /* Declare class variables. */
    private String player1, player2, currentWord;
    private TextView p1View, p2View, gameWord;
    private EditText inputField;
    private LinearLayout divider;
    private Typeface myFont;
    private SharedPreferences settings;
    private Lexicon myLexicon;
    private Game game;
    private int playerTurn;
    private boolean previousGameLoaded;

/*------------------------------------------------------------------------------------------------*/
/* Override methods                                                                               */
/*------------------------------------------------------------------------------------------------*/

    /* Method called when activity screen is created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        /* Initialize SharedPreferences containing the settings. */
        settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);

        /* Initialize the widgets, set the theme and obtain info from settings or called class. */
        initLayout();
        setTheme();
        receiveSettingsInfo();

        /* Start the game. */
        game = new Game(myLexicon);

        /* Make sure turns are correct when game is loaded. */
        if (previousGameLoaded && playerTurn == game.turn()) {
            game.changeTurn();
        }

        /* Get playerTurn, make sure the right player is displayed and update the Game State. */
        playerTurn = game.turn();
        indicatePlayer();
        updateGameState();
    }

    /* Method for linking and adding the menu items. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    /* Method for handling the menu item clicks. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Obtain id of selected item. */
        int id = item.getItemId();

        /* If exit, create a new dialog (dialog class handles button clicks). */
        if (id == R.id.action_exit) {
            DialogFragment exit = new Exit();
            exit.show(getFragmentManager(), "dialog");
            return true;
        /* If restart, create a new instance of this activity with same names. */
        } else if (id == R.id.action_restart) {
            Intent restart = new Intent(this, GamePlay.class);
            restart.putExtra("p1", player1);
            restart.putExtra("p2", player2);
            finish();
            startActivity(restart);
            return true;
        /* If intro screen (or main menu), start the intro screen activity. */
        } else if (id == R.id.action_intro) {
            Intent mainMenu = new Intent(this, IntroScreen.class);
            startActivity(mainMenu);
            finish();
            return true;
        /* If settings, go to settings and pass info indicating the origin.
         * Current activity is not closed.
         */
        } else if (id == R.id.action_settings) {
            Intent settings = new Intent(this, Settings.class);
            settings.putExtra("fromGamePlay", true);
            startActivity(settings);
            return true;
        /* If highscores, go to highscores activity and pass (empty) winner.
         * Current activity is not closed.
         */
        } else if (id == R.id.action_highscores) {
            Intent highscores = new Intent(this, Highscores.class);
            highscores.putExtra("winner", "");
            startActivity(highscores);
            return true;
        /* If How to Play, create dialog showing the HtP info. */
        } else if (id == R.id.action_htp) {
            DialogFragment htp = new HowToPlay();
            htp.show(getFragmentManager(), "dialog");
            return true;
        /* If acknowledgements, create dialog showing the acknowledgements.  */
        } else if (id == R.id.action_ack) {
            DialogFragment acknowledgements = new Acknowledgements();
            acknowledgements.show(getFragmentManager(), "dialog");
            return true;
        /* If name selection, start the Name Input activity. */
        } else if (id == R.id.action_names) {
            Intent names = new Intent(this, NameInput.class);
            startActivity(names);
            finish();
            return true;
        } // Add more menu option-handlers here

        /* Return the pressed item. */
        return super.onOptionsItemSelected(item);
    }

    /* Method for saving an unfinished state of the game. */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        /* Open Shared Preferences for saving information to device. */
        SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        /* Add relevant information and apply to save this information. */
        editor.putString("player1", player1);
        editor.putString("player2", player2);
        editor.putInt("turn", playerTurn);
        editor.putString("word", currentWord.toUpperCase());
        editor.putBoolean("savedGame", true); // Used to check if an incomplete game is present.
        editor.apply();

        // Call superclass to save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

/*------------------------------------------------------------------------------------------------*/
/* Helper Methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Method called when button is pressed after entering some input. */
    public void enterLetter(View view) {
        /* Initialize inputField and get its value as a String. */
        inputField = (EditText) findViewById(R.id.input_field);
        String input = String.valueOf(inputField.getText());

        /* If it meets the criteria (1 character and only letters),
         * handle the input, else display message.
         */
        if (input.length() == 1 && input.matches("[a-zA-Z]+")) {
            handleInput(input);
        } else {
            Toast.makeText(this, R.string.toast_single_letter, Toast.LENGTH_LONG).show();
        }
    }

    /* Method for updating Game State after valid input is entered*/
    public void handleInput(String input) {
        /* Add input to word, display it, clear input field, guess the input, and obtain winner. */
        currentWord += input;
        gameWord.setText(currentWord.toUpperCase()); // Handles formatting of the typeface
        inputField.setText("");
        game.guess(input.toLowerCase());
        int winnerNumber = game.winner();

        /* If a winner is chosen, process the endgame with right winner and loser names. */
        if (winnerNumber != -1) { // Returns -1 when game is not ended
            if (winnerNumber == 0) {
                processEndGame(player1, player2);
            } else {
                processEndGame(player2, player1);
            }
        /* If not winner is chosen, change turns and indicate them. */
        } else {
            game.changeTurn();
            indicatePlayer();
        }
    }

    /* Method for starting the winner screen activity with the right saved information. */
    public void processEndGame(String winner, String loser) {
        /* Start winner screen passing winner, loser and current word strings,
         * as well as a boolean indicating whether a word has been formed.
         * */
        Intent winnerScreen = new Intent(this, WinnerScreen.class);
        winnerScreen.putExtra("winner", winner);
        winnerScreen.putExtra("loser", loser);
        winnerScreen.putExtra("word", currentWord);
        winnerScreen.putExtra("wordCreated", game.wordCreated());
        startActivity(winnerScreen);

        /* Update information in Shared Preferences.
         * This will indicate whether an existing game must be loaded or not.
         * */
        SharedPreferences prefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("savedGame", false);
        editor.apply();
        finish();
    }

    /* Method for receiving information, either from intent or from Shared Preferences
     * The latter is true when a previously saved game is opened.
     */
    public void receiveSettingsInfo() {
        /* Initialize boolean stating  */
        previousGameLoaded = false;
        String language = settings.getString("language", "222");

        /* Attempts to get extras from called Intent.
         * If this fails, this means a game is loaded (since the activity is not started from the
         * name input screen.
         */
        try {
            /* Get players from bundle of called activities and initialize Lexicon. */
            Intent nameInputIntent = getIntent();
            player1 = nameInputIntent.getExtras().getString("p1");
            player2 = nameInputIntent.getExtras().getString("p2");
            myLexicon = new Lexicon(getApplicationContext(), language);
        } catch (NullPointerException e) {
            /* Otherwise, get settings from shared preferences. */
            player1 = settings.getString("player1", "222");
            player2 = settings.getString("player2", "222");
            playerTurn = settings.getInt("turn", -1);
            currentWord = settings.getString("word", "222");
            language = settings.getString("language", "222");
            myLexicon = new Lexicon(getApplicationContext(), language);
            previousGameLoaded = true;
        }
    }

/*------------------------------------------------------------------------------------------------*/
/* Layout methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Method for initializing variables and widgets that are important for the layout. */
    public void initLayout() {
        currentWord = "";
        myFont = Typeface.createFromAsset(getAssets(), "ghostFont.ttf");
        divider = (LinearLayout) findViewById(R.id.gameplay_divider);
        p1View = (TextView) findViewById(R.id.p1_display);
        p2View = (TextView) findViewById(R.id.p2_display);
        gameWord = (TextView) findViewById(R.id.game_word);
        gameWord.setText(currentWord);
    }

    /* Method for applying the selected theme to this activity. */
    public void setTheme() {
        String themeColor = settings.getString("theme", "222");
        gameWord.setTypeface(myFont);
        if (themeColor.equals("Orange")) {
            divider.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            gameWord.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        } else {
            divider.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            gameWord.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } // To set more themes, add here
    }

    /* Method for indicating which player's turn it currently is. */
    public void indicatePlayer() {
        int playerTurn = game.turn();

        if (playerTurn == 0) {
            p1View.setTextColor(ContextCompat.getColor(this, R.color.white));
            p2View.setTextColor(ContextCompat.getColor(this, R.color.grey));
        } else {
            p2View.setTextColor(ContextCompat.getColor(this, R.color.white));
            p1View.setTextColor(ContextCompat.getColor(this, R.color.grey));
        }
    }

    /* Method for updating the current Game State.*/
    public void updateGameState() {
        gameWord.setText(currentWord);
        game.setWord(currentWord);
        p1View.setText(player1);
        p2View.setText(player2);
    }
}