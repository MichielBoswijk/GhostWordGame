/*
 * WinnerScreen class (Activity!)
 *
 * Class implements the screen displayed when the game is ended and a winner has been chosen.
 * Handles all visualization and menu item presses in the activity.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class WinnerScreen extends AppCompatActivity {

    /* Declare class variables. */
    private String winner, loser, word;
    private boolean wordCreated;

    /* Method called on creation of the class. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_screen);

        /* Get info from called Intent. */
        getInfo();

        /* Display winner and loser, together with the reason for losing/winning. */
        TextView winnerField = (TextView) findViewById(R.id.winner_display);
        winnerField.setText(winner);
        TextView reasonField = (TextView) findViewById(R.id.winner_reason);
        if (wordCreated) {
            reasonField.setText(loser + " has made a word " + "(" + word.toLowerCase() + ")");
        } else {
            reasonField.setText(loser + " has made a non-existing combination " + "(" + word.toLowerCase() + ")");
        }
    }

    /* Method for creating the menu bar with inflater (for devices lacking a menu button). */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_winner_screen, menu);
        return true;
    }

    /* Method for handling the menu item selection. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Obtain id of selected item. */
        int id = item.getItemId();

        /* If acknowledgements, create dialog showing the acknowledgements.  */
        if (id == R.id.action_exit) {
            DialogFragment exit = new Exit();
            exit.show(getFragmentManager(), "dialog");
            return true;
        /* If intro screen (or main menu), start the intro screen activity. */
        } else if (id == R.id.action_intro) {
            Intent mainMenu = new Intent(this, IntroScreen.class);
            startActivity(mainMenu);
            finish();
        }

        /* Return the pressed item. */
        return super.onOptionsItemSelected(item);
    }

    /* Method for starting the highscores screen after the button is pressed. */
    public void startHighscores(View view) {
        Intent highscores = new Intent(this, Highscores.class);
        highscores.putExtra("winner", winner);
        startActivity(highscores);
        finish();
    }

    /* Method called when the back button on the device is pressed. */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /* Start the main activity. */
        Intent mainMenu = new Intent(this, IntroScreen.class);
        startActivity(mainMenu);
        finish();
    }

    public void getInfo() {
        /* Get word, winner, loser, and boolean indicating whether a word was created from called intent. */
        Intent gamePlayIntent = getIntent();
        winner = gamePlayIntent.getExtras().getString("winner");
        loser = gamePlayIntent.getExtras().getString("loser");
        word = gamePlayIntent.getExtras().getString("word");
        wordCreated = gamePlayIntent.getExtras().getBoolean("wordCreated");
    }
}