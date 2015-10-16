/*
 * Settings class (Activity!)
 *
 * Class implements the activity that displays the settings.
 * Handles all visualization and menu item presses in the activity.
 * Handles the toggling of different settings (language, and theme), as well as a clear data option.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

//TODO: Fix back option.
//TODO: check onOptionsSelected????

/* Necessary imports. */
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    /* Declare class variables. */
    private AppSettings usedSettings;
    private Boolean fromGamePlay;
    private LinearLayout layout1, layout2, layout3;
    private ToggleButton toggleTheme, toggleLanguage;
    private Button applyButton;

    /* Method called on creation of the settings class. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call constructor superclass and link layout file. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /* Check whether settings is accessed from game play screen. */
        SharedPreferences settings = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        fromGamePlay = extras.getBoolean("fromGamePlay");

        /* Initialize views and check if buttons should be disabled (if accessed from game play). */
        initViews();
        if (fromGamePlay) {
            disableButtons();
        }

        /* Initialize settings if no previous settings have been assigned. */
        if (usedSettings == null) {
            usedSettings = new AppSettings(getApplicationContext());
        }

        /* Get language and theme from settings and setup listeners for both. */
        usedSettings.setLanguage(settings.getString("language", "222"));
        usedSettings.setTheme(settings.getString("theme", "222"));
        initLanguageListener();
        initThemeListener();
    }

    /* Method for applying a preference passed as a string. */
    public void applyPreferences(String pref) {
        /* Open the shared preferences editor. */
        SharedPreferences prefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        /* Switch handles different settings options by saving them in the shared preferences. */
        switch(pref) {
            case "NL":
            case "EN":
                editor.putString("language", pref);
                break;
            case "Green":
            case "Orange":
                editor.putString("theme", pref);
                break;
            case "ON":
                editor.putBoolean("sound", true);
                break;
            case "OFF":
                editor.putBoolean("sound", false);
                break;
        }
        editor.apply();
    }

    /* Method called when back button of the device if pressed. */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fromGamePlay) {
            Intent gamePlay = new Intent(this, GamePlay.class);
            startActivity(gamePlay);
            finish();
        } else {
            Intent mainMenu = new Intent(this, IntroScreen.class);
            startActivity(mainMenu);
            finish();
        }
    }

/*------------------------------------------------------------------------------------------------*/
/* OnClick/Toggle methods                                                                         */
/*------------------------------------------------------------------------------------------------*/

    /* Method for setting up the listener for the language toggle button. */
    public void initLanguageListener() {
        /* change display of button depending on the saved language choice. */
        if (usedSettings.getLanguage().equals("NL")) {
            toggleLanguage.setChecked(true);
        }
        else {
            toggleLanguage.setChecked(false);
        }

        /* Set listener for applying changes when button is toggled. */
        toggleLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    usedSettings.setLanguage("NL");
                    applyPreferences("NL");
                } else {
                    usedSettings.setLanguage("EN");
                    applyPreferences("EN");
                }
            }
        });
    }

    /* Method for setting up the listener for the theme toggle button. */
    public void initThemeListener() {
        if (usedSettings.getTheme().equals("Orange")) {
            toggleTheme.setChecked(true);
            applyTheme("Orange");
        } else {
            toggleTheme.setChecked(false);
            applyTheme("Green");
        }

        /* Set listener for applying changes when button is toggled. */
        toggleTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    usedSettings.setTheme("Orange");
                    applyPreferences("Orange");
                    applyTheme("Orange");
                } else {
                    usedSettings.setTheme("Green");
                    applyPreferences("Green");
                    applyTheme("Green");
                } // Add another color here
            }
        });
    }

    /* Method called when the apply button of the clear all option is pressed. */
    public void clearAll(View view) {
        /* Start dialog (which will handle its button presses). */
        DialogFragment deleteData = new DeleteDataDialog();
        deleteData.show(getFragmentManager(), "dialog");
    }

/*------------------------------------------------------------------------------------------------*/
/* Layout methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Method for setting the theme of the current screen. */
    public void applyTheme(String color) {

        /* Set the dividers to the chosen theme color. */
        if (color.equals("Orange")) {
            layout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            layout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
            layout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        } else {
            layout1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            layout2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            layout3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } // Add another color here
    }

    /* Method for disabling buttons in settings. This means that the settings can not be changed
     * while in-game but they can be viewed (e.g. when the user forgets which language is set).
     */
    public void disableButtons() {
        if (fromGamePlay) {
            Toast.makeText(getApplicationContext(), R.string.toast_settings_application, Toast.LENGTH_LONG).show();
            toggleTheme.setEnabled(false);
            applyButton.setEnabled(false);
            toggleLanguage.setEnabled(false);
        }
    }

    /* Method for initializing the views. */
    public void initViews() {
        /* Initialize layouts used as dividers. */
        layout1 = (LinearLayout) findViewById(R.id.colored_line1);
        layout2 = (LinearLayout) findViewById(R.id.colored_line2);
        layout3 = (LinearLayout) findViewById(R.id.colored_line4);

        /* Initialize settings buttons. */
        toggleLanguage = (ToggleButton) findViewById(R.id.language_button);
        toggleTheme = (ToggleButton) findViewById(R.id.theme_button);
        applyButton = (Button) findViewById(R.id.clear_button);
    }
}