/*
 * Highscores class (Activity!)
 *
 * Class implements the activity that displays the highscores.
 * Handles all visualization and menu item presses in the activity.
 * Communicates with the PlayerList class to obtain the current player information.
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class Highscores extends AppCompatActivity {

    /* Declare class variables. */
    private PlayerList playerList;
    private ArrayList<Player> players;

/*------------------------------------------------------------------------------------------------*/
/* Override methods                                                                               */
/*------------------------------------------------------------------------------------------------*/

    /* Method called when activity screen is created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        /* Initialize playerList object and get the list of names from the object. */
        playerList = new PlayerList(getApplicationContext());
        players = playerList.getList();

        /* Get winner from called Intent. */
        Intent winnerScreenIntent = getIntent();
        String winner = winnerScreenIntent.getExtras().getString("winner");

        /* If a winner is available, clear data and update it (adds 1 to winners score).
         * The playerList object is re-created to make sure it is updated.
         */
        if (winner != null && !winner.equals("")) {
            clearData();
            updateData(winner);
            playerList = new PlayerList(getApplicationContext());
        }

        /* Set link the custom adapter to the playerList and the listView widget. */
        ArrayAdapter myAdapter = new MyAdapter(Highscores.this, playerList.getList());
        ListView highscoresView = (ListView) findViewById(R.id.highscores);
        highscoresView.setAdapter(myAdapter);
    }

    /* Method for linking and adding the menu items. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
        return true;
    }

    /* Method for handling the menu item clicks. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Obtain id of selected item. */
        int id = item.getItemId();

        /* If new game, start the name input activity. */
        if (id == R.id.action_new_game) {
            Intent names = new Intent(this, NameInput.class);
            startActivity(names);
            finish();
            return true;
        /* If acknowledgements, create dialog showing the acknowledgements.  */
        } else if (id == R.id.action_ack) {
            DialogFragment acknowledgements = new Acknowledgements();
            acknowledgements.show(getFragmentManager(), "dialog");
            return true;
        /* If exit, create a new dialog (dialog class handles button clicks). */
        } else if (id == R.id.action_exit) {
            DialogFragment exit = new Exit();
            exit.show(getFragmentManager(), "dialog");
            return true;
        } // Add more menu option-handlers here

        /* Return the pressed item. */
        return super.onOptionsItemSelected(item);
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

/*------------------------------------------------------------------------------------------------*/
/* OnClick methods                                                                                */
/*------------------------------------------------------------------------------------------------*/

    /* Method for navigating to the intro screen/main menu. */
    public void startIntroScreen(View view) {
        Intent mainMenu = new Intent(this, IntroScreen.class);
        startActivity(mainMenu);
        finish();
    }

/*------------------------------------------------------------------------------------------------*/
/* Helper methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Clear the file containing names, scores and dates. */
    public void clearData() {
        SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String fileName = settings.getString("fileName", "222");
        File dir = getFilesDir();
        File file = new File(dir, fileName);
        boolean deleted = file.delete();
        if (deleted) {
            Toast.makeText(getApplicationContext(), "Score updated.", Toast.LENGTH_SHORT).show();
        }
    }

    /* Refill the file containing names, scores and dates. For every player in the player list,
     * add the player to the file. If the player is the winner, duplicate the player with a new
     * date and incremented score (by 1).
     */
    public void updateData(String winner) {
        for (Player player : players) {
            if (player.getName().equals(winner)) {
                player.setDate();
                Player updatedPlayer = new Player(winner, (player.getScore() + 1), player.getDate());
                playerList.add(updatedPlayer); // This writes the new data to the txt file.
            } else {
                playerList.add(player); // This writes the new data to the txt file.
            }
        }
    }
}