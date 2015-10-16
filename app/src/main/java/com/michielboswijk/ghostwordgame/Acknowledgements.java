/*
 * Acknowledgements class
 *
 * Class implements an alert dialog for displaying the acknowledgements.
 * All code/icons designed by others are credited in this dialog.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class Acknowledgements extends DialogFragment {
    /* Method called on creation of the dialog. */
    @Override
    public Dialog onCreateDialog (Bundle SaveInstanceState) {
        /* Get context from current activity.*/
        Context context = getActivity();

        /* Create new dialog, and set message. */
        AlertDialog.Builder ackAlert = new AlertDialog.Builder(context);
        String message = getString(R.string.ack);
        ackAlert.setMessage(message);

        /* Create button in dialog. */
        ackAlert.setNeutralButton(R.string.got_it, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        /* Set title and show dialog. */
        ackAlert.setTitle(R.string.ack_title);
        ackAlert.create();

        return ackAlert.create();
    }
}