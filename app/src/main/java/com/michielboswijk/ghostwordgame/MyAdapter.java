/*
 * MyAdapter class
 *
 * Class implements an adapter used to link the list of all players to the list view where
 * the highscores are displayed.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package. */
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class MyAdapter extends ArrayAdapter<Player> {

    /* Declare class variables. */
    private ArrayList<Player> allPlayers;
    private Context context;
    private String color;

    /* Constructor initializes class variables and sorts player list. */
    public MyAdapter(Context context, ArrayList<Player> players) {
        /* Link layout to adapter. */
        super(context, R.layout.highscores_layout, players);

        /* Open shared preferences and initialize class variables. */
        SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        color = settings.getString("theme", "222");
        this.context = context;
        allPlayers = players;

        /* Sort the list of players on the scores they have. */
        Collections.sort(allPlayers);
    }

    /* Method for setting all info and layout to be displayed in a row of the highscores. */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Create and link layout inflater for linking layout to the view objects. */
        //LayoutInflater myInflater = LayoutInflater.from(getContext());
        LayoutInflater myInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View theView = myInflater.inflate(R.layout.highscores_layout, parent, false);

        /* Get name, date and score of every player.*/
        Player player = allPlayers.get(position);
        String name = player.getName();
        String date = player.getDate();
        String score = Integer.toString(player.getScore());

        /* Initialize view containing the star and determine if (and which) star must be shown. */
        ImageView star = (ImageView) theView.findViewById(R.id.hs_icon);
        if (position == 0) {         // First place
            star.setImageResource(R.drawable.star_gold);
        } else if (position == 1) { // Second place
            star.setImageResource(R.drawable.star_silver);
        } else if (position == 2){  // Third place
            star.setImageResource(R.drawable.star_bronze);
        } else {
            star.setImageResource(android.R.color.transparent);
        }

        /* Set rank, name, score and date of the rows. */
        TextView rank = (TextView) theView.findViewById(R.id.hs_rank);
        rank.setText(Integer.toString(position + 1)); // Increment by 1 since position starts at 0
        TextView nameView = (TextView) theView.findViewById(R.id.hs_name);
        nameView.setText(name);
        TextView scoreView = (TextView) theView.findViewById(R.id.hs_score);
        scoreView.setText("Score: " + score);
        TextView dateView = (TextView) theView.findViewById(R.id.hs_date);
        dateView.setText("Date: " + date);

        /* Set colored divider to chosen theme color. */
        LinearLayout divider = (LinearLayout) theView.findViewById(R.id.hs_divider);
        if (color.equals("Orange")) {
            divider.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        } else {
            divider.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } // Add another color here

        return theView;
    }
}