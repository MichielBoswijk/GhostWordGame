/*
 * Lexicon class
 *
 * Class implements a game lexicon object with a dictionary as a hash set.
 * Possible dictionaries: Dutch, English
 * Used to check for words and impossible character combinations.
 *
 * Michiel Boswijk, michiel.boswijk@gmail.com
 * Date: 10-10-2015
 */

//TODO speed up process by using vincents method
//TODO check unused methods

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

        /* Declare variables and add string to current word to form the word made by the players. */
        String word;
        Iterator myIterator;
        currentWord += filterString;

        /* Iterate lexicon to remove all words that do not start with the current character
         * combination in the game.
         */
        myIterator = baseLexicon.iterator();
        while(myIterator.hasNext()) {
            word = (String) myIterator.next();
            if(!word.startsWith(currentWord)) {
                filteredLexicon.remove(word);
            }
        }
    }

    /* Method for checking whether only one result is left. */
    public String result() {
        if (count() == 1) {
            return filteredLexicon.iterator().next();
        } else {
            return null;
        }
    }

    /* Method for clearing the filtered lexicon. */
    public void reset() {
        filteredLexicon.clear();
        filteredLexicon.addAll(baseLexicon);
    }

    /* Method for returning the size of the filtered lexicon. */
    public int count() {
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