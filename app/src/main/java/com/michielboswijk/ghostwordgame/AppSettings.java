/*
 * AppSettings class
 *
 * Class implements a settings object.
 * This class is used to initialize the settings in the corresponding activity.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.io.Serializable;

public class AppSettings implements Serializable{

    /* Declare class variables. */
    private Context usedContext;
    private String language, theme, fileName;

    /* Constructor of the class initializes the class variables to its default values. */
    public AppSettings(Context context) {
        language = "EN";
        theme = "Green";
        usedContext = context;
        fileName = "playerInfo.txt";
    }

/*------------------------------------------------------------------------------------------------*/
/* Getters and Setters
/*------------------------------------------------------------------------------------------------*/

    /* Method for setting dictionary language. */
    public void setLanguage(String lanChoice) {
        language = lanChoice;
    }

    /* Method for getting dictionary language. */
    public String getLanguage() {
        return language;
    }

    /* Method for setting theme. */
    public void setTheme(String themeChoice) {
        theme = themeChoice;
    }

    /* Method for getting theme. */
    public String getTheme() {
        return theme;
    }

    /* Method for getting name of the file containing player data. */
    public String getFileName() {
        return fileName;
    }

/*------------------------------------------------------------------------------------------------*/
/* Helper Methods
/*------------------------------------------------------------------------------------------------*/

    /* Method for clearing the player info file (create a new file overwriting the previous one). */
    public void clearAll() {
        File dir = usedContext.getFilesDir();
        File file = new File(dir, fileName);
        boolean deleted = file.delete();
        if (deleted) {
            Toast.makeText(usedContext, R.string.deleted_toast, Toast.LENGTH_SHORT).show();
        }
    }
}