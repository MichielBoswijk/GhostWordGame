/*
 * IntroScreen class (Activity!)
 *
 * Class implements the initial screen of the application (Main Menu).
 * Class contains buttons for navigating to the name input, settings, how to play and highscores.
 *
 * Michiel Boswijk, michiel.boswijk@gmail.com
 * Date: 10-10-2015
 */

// TODO add icons with appropriate colors
// TODO check if onRestoreInstanceState is necessary

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/* Class for the Main Menu of the application. */
public class IntroScreen extends AppCompatActivity {

    /* Declare class variables. */
    private SharedPreferences settings;
    private TextView title, title2;

/*------------------------------------------------------------------------------------------------*/
/* Override methods                                                                               */
/*------------------------------------------------------------------------------------------------*/

    /* Method called on activity creation. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        /* Open Shared Preferences to obtain any potentially saved settings. */
        settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);

        /* Handle settings and set layout of the activity. */
        checkSavedSettings();
        initAndSetLayout();
    }

    /* Method called when activity is reopened after an interruption. */
    @Override
    public void onRestoreInstanceState(@NonNull Bundle inState) {
        super.onRestoreInstanceState(inState);
        settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        setColors();

    }

    /* Method for creating the menu bar with inflater (for devices lacking a menu button). */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_intro_screen, menu);
        return true;
    }

    /* Method for handling the menu item selection. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Obtain id of selected item. */
        int id = item.getItemId();

        /* If exit, create a new dialog (dialog class handles button clicks). */
        if (id == R.id.action_ack) {
            DialogFragment acknowledgements = new Acknowledgements();
            acknowledgements.show(getFragmentManager(), "dialog");
        /* If acknowledgements, create dialog showing the acknowledgements.  */
        } else if(id == R.id.action_exit) {
            DialogFragment exit = new Exit();
            exit.show(getFragmentManager(), "dialog");
        }

        /* Return the pressed item. */
        return super.onOptionsItemSelected(item);
    }

/*------------------------------------------------------------------------------------------------*/
/* OnClick Methods                                                                                */
/*------------------------------------------------------------------------------------------------*/

    /* Method for starting the name selection activity. */
    public void startNameInput(View view) {
        Intent nameInput = new Intent(this, NameInput.class);
        startActivity(nameInput);
        finish();
    }

    /* Method for starting the settings activity. */
    public void startSettings(View view) {
        Intent settings = new Intent(this, Settings.class);
        settings.putExtra("fromGamePlay", false);
        startActivity(settings);
        finish();
    }

    /* Method for starting the highscores activity. */
    public void startHighscores(View view) {
        Intent highscores = new Intent(this, Highscores.class);
        highscores.putExtra("winner", ""); // Empty to indicate no winner has been selected yet.
        startActivity(highscores);
        finish();
    }

    /* Method for showing the 'How to Play' dialog. */
    public void startHowToPlay(View view) {
        DialogFragment howToPlay = new HowToPlay();
        howToPlay.show(getFragmentManager(), "dialog");
    }

/*------------------------------------------------------------------------------------------------*/
/* Layout and Settings Methods                                                                    */
/*------------------------------------------------------------------------------------------------*/

    /* Method for setting colors of visible widgets in the screen. */
    public void setColors() {
        String themeColor = settings.getString("theme", "222");
        if(themeColor.equals("Orange")) {
            title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            title2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        } else {
            title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            title2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        }
        // Add any more colors here
    }

    /* Method for saving settings in the case they have not been saved yet. */
    public void checkSavedSettings() {
        /* Initialize settings object to get default settings. */
        AppSettings mySettings = new AppSettings(getApplicationContext());

        /* If language is not saved yet, save it for next time. */
        if (settings.getString("language", "222").equals("222")){
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("language", mySettings.getLanguage());
            editor.apply();
        }

        /* If chosen theme is not saved yet, save it for next time. */
        if (settings.getString("theme", "222").equals("222")){
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("theme", mySettings.getTheme());
            editor.apply();
        }

        /* If file name is not saved yet, save it for next time. */
        if (settings.getString("fileName", "222").equals("222")){
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("fileName", mySettings.getFileName());
            editor.apply();
        }

        /* If a saved game is set, load the game. */
        if (settings.getBoolean("savedGame", false)) {
            DialogFragment restore = new RestoreDialog();
            restore.show(getFragmentManager(), "dialog");
        }
    }

    /* Method for modifying the layout of the activity. */
    public void initAndSetLayout() {
        /* Set font for title and subtitle. */
        Typeface myFont = Typeface.createFromAsset(getAssets(), "ghostFont.ttf");

        /* Set font and color for title and subtitle. */
        title = (TextView) findViewById(R.id.first_title);
        title2 = (TextView) findViewById(R.id.second_title);
        title.setTypeface(myFont);
        title2.setTypeface(myFont);
        setColors();
    }
}