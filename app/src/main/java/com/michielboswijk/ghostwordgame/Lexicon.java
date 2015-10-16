/*
 * Lexicon class
 *
 * Class implements a game lexicon object with a dictionary as a hash set.
 * Possible dictionaries: Dutch, English.
 * Used to check for words and impossible character combinations.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Lst updated: 16-10-2015
 */

/* Reference package .*/
package com.michielboswijk.ghostwordgame;

/* Necessary imports. */
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashSet;
import java.util.Iterator;

public class Lexicon {

    /* Declare class variables. */
    private Context context;
    private String lan, currentWord;
    private HashSet<String> baseLexicon, filteredLexicon;

    /* Constructor of class initializes class variables.
     * Creates a complete lexicon with the chosen dictionary and a lexicon that will be filtered
     * depending on the word in the game.
     */
    public Lexicon(Context appContext, String language) {
        currentWord = "";
        context = appContext;
        lan = language;

        initBaseLex();
        filteredLexicon = new HashSet<>();
        filteredLexicon.addAll(baseLexicon);
    }


    /* Method for initializing hash set containing the dictionary. */
    public void initBaseLex() {
        /* Declare variables and initialize empty lexicon. */
        String dictType;
        String word;
        baseLexicon = new HashSet<>();

        /* Select right dictionary file. */
        if (lan.equals("NL")) {
            dictType = "dutch.txt";
        }
        else {
            dictType = "english.txt";
        } // Add any more files here (they should be in the assets folder)

        /* Read all words from the dictionary file and add them to the lexicon. */
        try {
            InputStream stream = context.getResources().getAssets().open(dictType);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            while ((word = reader.readLine()) != null) {
                baseLexicon.add(word);
            }
            reader.close();
        } catch (IOException e) {
            Toast.makeText(context, R.string.toast_dict, Toast.LENGTH_LONG).show();
        }
    }

    /* Method for filtering the lexicon to one only containing words starting with the given
     * word combination.
     */
    public void filter(String filterString) {
        /* Declare variables, add string to current word to form the word made by the players,
         * and initialize temp lexicon.
         */
        String word;
        Iterator myIterator;
        currentWord += filterString;

        /* Iterate filtered lexicon to make sure only relevant words are iterated. The words that
         * can still be made (that start with the current word) are added to a third lexicon
         * that is then set to the filtered lexicon. This allows for a fast program since
         * it minimizes the addition of items to any lexicons. Note: an efficient way to implement
         * the iteration was discussed with classmate Vincent Erich!
         */
        HashSet<String> tempLexicon = new HashSet<>();
        myIterator = filteredLexicon.iterator();
        while (myIterator.hasNext()) {
            word = (String) myIterator.next();
            if (word.startsWith(currentWord)) {
                tempLexicon.add(word);
            }
        }
        filteredLexicon = tempLexicon;
    }

    /* Method for returning the size of the filtered lexicon. */
    public int count() {
        System.out.println(filteredLexicon.size());
        return filteredLexicon.size();
    }

    /* Method for checking whether the lexicon contains the current word. */
    public boolean contains(String word) {
        return baseLexicon.contains(word);
    }

    /* Method for setting the current word. Used when game is reloaded.*/
    public void setWord(String word) {
        currentWord = word;
    }
}