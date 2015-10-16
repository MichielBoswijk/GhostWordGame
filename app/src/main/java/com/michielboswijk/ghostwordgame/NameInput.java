/*
 * NameInput class (Activity!)
 *
 * Class implements the name selection screen.
 * Handles all visualization and menu item presses in the activity.
 * Class handles new name inputs, and links to the player list to provide the user with
 * the option to select a previously selected name.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class NameInput extends AppCompatActivity {

    /* Declare class variables. */
    private String p1, p2;
    private PlayerList players;
    private ArrayList<String> playerNames;
    private ArrayAdapter<String> myAdapter;
    private Spinner spinnerP1, spinnerP2;
    private EditText fieldPlayer1, fieldPlayer2;

/*------------------------------------------------------------------------------------------------*/
/* Override methods                                                                               */
/*------------------------------------------------------------------------------------------------*/

    /* Method called on activity creation, sets theme of layout and fills spinner
     * with previously selected names.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        /* Set theme of the layout and link names from history to spinner. */
        setTheme();
        showNamesInSpinner();
    }

    /* Method for allowing the user to hide keyboard when pressed outside of keyboard. */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }

    /* Method for linking and adding the menu items. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_name_input, menu);
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
        /* If intro screen (or main menu), start the intro screen activity. */
        } else if (id == R.id.action_intro) {
            Intent mainMenu = new Intent(this, IntroScreen.class);
            startActivity(mainMenu);
            finish();
        /* If How to Play, create dialog showing the HtP info. */
        } else if (id == R.id.action_htp) {
            DialogFragment htp = new HowToPlay();
            htp.show(getFragmentManager(), "dialog");
        /* If acknowledgements, create dialog showing the acknowledgements.  */
        } else if (id == R.id.action_ack) {
            DialogFragment acknowledgements = new Acknowledgements();
            acknowledgements.show(getFragmentManager(), "dialog");
        }

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
    }

/*------------------------------------------------------------------------------------------------*/
/* OnClick methods                                                                                */
/*------------------------------------------------------------------------------------------------*/

    /* Method called when the user tries to proceed after selecting/entering names. */
    public void startGameplay(View view) {
        /* Tries to obtain names from the current spinner selection. */
        try {
            p1 = spinnerP1.getSelectedItem().toString();
            p2 = spinnerP2.getSelectedItem().toString();

            /* Check for duplicate names and start gameplay. */
            if (!p1.equals(p2)) {
                Intent gameplay = new Intent(this, GamePlay.class);
                gameplay.putExtra("p1", p1);
                gameplay.putExtra("p2", p2);
                startActivity(gameplay);
                finish();
            /* Display that the names are the same. */
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_different_names, Toast.LENGTH_LONG).show();
            }
        /* If no names available, either an entered name is not confirmed or not entered at all. */
        } catch (Exception e) {
            /* If some text is entered, this implies names are entered but not confirmed. */
            if (fieldPlayer1.getText().length() != 0 || fieldPlayer2.getText().length() != 0) {
                Toast.makeText(this, R.string.toast_confirm_names, Toast.LENGTH_SHORT).show();
            /* If no text is entered, user(s) should enter a name. */
            } else {
                Toast.makeText(this, R.string.toast_enter_name, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Method called when check mark is pressed to confirm a name. */
    public void enterPlayer(View view) {
        /* Declare/initialize local variables. */
        String name;
        int spinnerPosition;
        boolean firstNameField = false;

        /* If the view is the first check mark get from first input field,
         * if second from second input field.
         */
        if (view.getId() == R.id.confirm1) {
            name = String.valueOf(fieldPlayer1.getText());
            firstNameField = true;
        } else {
            name = String.valueOf(fieldPlayer2.getText());
        }

        /* If names are valid, add the player list, notify spinner,
         * set class variables and spinner selection.
         */
        if(validName(name)) {
            Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();
            players.add(new Player(name, 0, ""));
            playerNames.add(name);
            myAdapter.notifyDataSetChanged();
            spinnerPosition = myAdapter.getPosition(name);
            if(firstNameField) {
                p1 = name;
                spinnerP1.setSelection(spinnerPosition);
            } else {
                p2 = name;
                spinnerP2.setSelection(spinnerPosition);
            }
        }
    }

/*------------------------------------------------------------------------------------------------*/
/* Helper methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Method for checking whether a valid name is entered. */
    public boolean validName(String name) {
        /* Name must not be empty, must not exceed 10 characters (for display purposes)
         * and must only contain letters. Returns true is valid, false otherwise. */
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.toast_empty, Toast.LENGTH_LONG).show();
        } else if (name.length() > 10) {
            Toast.makeText(getApplicationContext(), R.string.toast_too_many, Toast.LENGTH_LONG).show();
        } else if (!name.matches("[a-zA-Z]+")) {
            Toast.makeText(getApplicationContext(), R.string.toast_letters_only, Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }

    /* Method for linking player names to the spinners. */
    public void showNamesInSpinner(){
        /* Obtain player names and put only the names in a new list. */
        players = new PlayerList(getApplicationContext());
        ArrayList<Player> playersList = players.getList();
        playerNames = new ArrayList<>();
        for (Player player : playersList) {
            playerNames.add(player.getName());
        }

        /* Initialize spinners and input fields. */
        spinnerP1 = (Spinner) findViewById(R.id.spinner1);
        spinnerP2 = (Spinner) findViewById(R.id.spinner2);
        fieldPlayer1 = (EditText) findViewById(R.id.text_field1);
        fieldPlayer2 = (EditText) findViewById(R.id.text_field2);

        /* Sort the player names. */
        Collections.sort(playerNames);

        /* Link players to adapter and link adapter to spinners. */
        myAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, playerNames);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerP1.setAdapter(myAdapter);
        spinnerP2.setAdapter(myAdapter);
    }

/*------------------------------------------------------------------------------------------------*/
/* Layout methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Set theme of the layout. */
    public void setTheme(){
        /* Load shared preferences and initialize layouts (used as dividers). */
        SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.player_line1);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.player_line2);

        /* Obtain theme color and set color of layouts. */
        if (settings.getString("theme", "222").equals("Orange")) {
            layout1.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            layout2.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            layout1.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            layout2.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        } // Add another color here
    }
}