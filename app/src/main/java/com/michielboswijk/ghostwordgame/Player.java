/*
 * Player class
 *
 * Class implements a player object.
 * A player has a name, score and 'last played' date.
 *
 * Michiel Boswijk, michiel.boswijk@gmail.com
 * Date: 10-10-2015
 */

/* Reference package .*/
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Player implements Comparable<Player>{

    /* Declare class variables. */
    private String name;
    private int score;
    private String date;

    /* Constructor initializes class variables passed as arguments. */
    public Player(String playerName, int playerScore, String lastPlayed){
        name = playerName;
        score = playerScore;
        date = lastPlayed;
    }

    /* Method for returning the date of the player. */
    public String getDate() {
        return date;
    }

    /* Method for setting the current date to the players date property. */
    public void setDate() {
        /* Obtain current date and format into dd-MMM-yyyy. */
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        date = df.format(c.getTime());
    }

    /* Method for getting the name if the player. */
    public String getName() {
        return name;
    }

    /* Method for setting the name if the player. */
    public void setName(String name) {
        this.name = name;
    }

    /* Method for getting the score of the player. */
    public int getScore() {
        return score;
    }

    /* Method assists the sorting of player objects. Causes the players to be sorted by score. */
    public int compareTo(@NonNull Player comparePlayer){
        int compareScore = comparePlayer.getScore();
        return compareScore - score;
    }
}