/*
 * RestoreDialog class
 *
 * Class implements an alert dialog for prompting the user to load a previous unfinished game.
 *
 * Michiel Boswijk, michiel.boswijk@gmail.com
 * Date: 10-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class RestoreDialog extends DialogFragment {

    /* Declare class variable. */
    private Context context;

    /* Method called on creation of the dialog. */
    @Override
    public Dialog onCreateDialog (Bundle SaveInstanceState) {
        /* Get context from current activity.*/
        context = getActivity();

        /* Create new dialog and set message. */
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(context);
        String message = getString(R.string.restore);
        exitAlert.setMessage(message);

        /* Create cancel button in dialog. */
        exitAlert.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferences settings = context.getSharedPreferences("settings",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                /* Reset saved player1 value to null so dialog in into screen won't show again. */
                editor.putString("player1", null);
                editor.apply();
            }
        });

        /* Create confirm button in dialog. */
        exitAlert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* Start gameplay class. */
                Intent gamePlay = new Intent(context, GamePlay.class);
                startActivity(gamePlay);
            }
        });

        /* Set title and show dialog. */
        exitAlert.setTitle(R.string.confirm);
        exitAlert.create();

        return exitAlert.create();
    }
}