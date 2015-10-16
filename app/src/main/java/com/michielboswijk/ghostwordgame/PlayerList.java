/*
 * PlayerList class
 *
 * Class implements a player object.
 * A player has a name, score and 'last played' date.
 *
 * Note: An ArrayList was chosen since it is dynamic in size, and is easy to read. Even though
 * a HashSet might be faster, the ArrayList is chosen since the loss in lookup speed is marginal
 * compared to the readability it provides in the code.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package .*/
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerList {

    /* Declare class variables. */
    private ArrayList<Player> players;
    private Context context;
    private String fileName;

    /* Constructor initializes class variables and reads names from txt file. */
    public PlayerList(Context appContext) {
        /* Get name of the file containing all player info from shared preferences. */
        SharedPreferences settings = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
        fileName = settings.getString("fileName", "222");

        /* Initialize context, player list and read the names from the txt file. */
        context = appContext;
        players =  new ArrayList<>();
        readNames();
    }

    /* Method for reading names from the txt file. */
    public void readNames() {
        /* Declare local variables. */
        Scanner myScanner;
        String playerInfo, name, date;
        int score;

        /* Try to open the text file, obtain the data and write them as players to player list. */
        try {
            myScanner = new Scanner(context.openFileInput(fileName));
            while (myScanner.hasNextLine()) {
                playerInfo = myScanner.nextLine();
                /* Since names, scores and dates are divided by a space in the txt file,
                 * the line is split on spaces and the data is added in a new player to the list.
                 */
                String[] split = playerInfo.split("\\s+");
                name = split[0];
                score = Integer.parseInt(split[1]);
                date = split[2];
                players.add(new Player(name, score, date));
            }
        /* When file is not found, display message stating no players are in memory. */
        } catch (FileNotFoundException e) {
            Toast.makeText(context, R.string.toast_no_players, Toast.LENGTH_LONG).show();
        }
    }

    /* Method for adding a player to the text file. */
    public void add(Player player) {
        PrintStream output;

        /* Try to open the file and write data to it. */
        try {
            /* Get name, score and date from player. */
            output = new PrintStream(context.openFileOutput(fileName, Context.MODE_APPEND));
            String playerName = player.getName();
            String playerScore = Integer.toString(player.getScore());
            if (player.getDate().equals("")) { // Makes sure only new players have their date updated.
                player.setDate();
            }
            String playerDate = player.getDate();

            /* Write data (space-separated) as a line to the file. */
            String line = (playerName + " " + playerScore + " " + playerDate);
            output.println(line);
            output.close();

        /* Display message when file can not be opened. */
        } catch (FileNotFoundException e) {
            Toast.makeText(context, R.string.toast_error_file, Toast.LENGTH_SHORT).show();
        }
    }

    /* Return the list of players as an ArrayList. */
    public ArrayList<Player> getList () {
        return players;
    }
}
