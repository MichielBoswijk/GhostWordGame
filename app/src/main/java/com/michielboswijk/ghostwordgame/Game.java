/*
 * Game class
 *
 * Class implements a game object.
 * Used to create the functionality for playing a single ghost word game.
 *
 * Author: Michiel Boswijk, michiel.boswijk@gmail.com
 * Last updated: 16-10-2015
 */

/* Reference package .*/
package com.michielboswijk.ghostwordgame;

/* Necessary import. */
import java.util.Random;

public class Game {

    /* Declare class variables. */
    private int playerTurn;
    private boolean wrongGuess, madeWord;
    private String currentWord;
    private Lexicon myLexicon;

    /* Class constructor initializes class variables and picks a player. */
    public Game(Lexicon lex){
        /* Initialize class variables. */
        wrongGuess = false;
        madeWord = false;
        myLexicon = lex;
        currentWord = "";

        /* Pick a player at random. */
        Random random = new Random();
        playerTurn = random.nextInt(2);
    }

/*------------------------------------------------------------------------------------------------*/
/* Helper methods                                                                                 */
/*------------------------------------------------------------------------------------------------*/

    /* Method called when user adds a letter to the word. */
    public void guess(String guess) {
        /* Send guess to filter and update current word. */
        myLexicon.filter(guess);
        currentWord += guess;

        /* If lexicon is empty, combination can't make a word. */
        if (myLexicon.count() == 0) {
            System.out.println(myLexicon.count());
            wrongGuess = true;
        /* If it can still make a word, it may be up to 3 characters long even if it is a word. */
        } else if (currentWord.length() <= 3){
            wrongGuess = false;
        /* If more than 3 valid characters and the word is in the lexicon, a word is made. */
        } else if (myLexicon.contains(currentWord) ) {
            wrongGuess = true;
            madeWord = true;
        }
    }

    /* Method for returning which player's turn it is (0 for player1, 1 for player 2). */
    public int turn () {
        return playerTurn;
    }

    /* Method for changing the turn of the player. */
    public void changeTurn() {
        if (playerTurn == 1){
            playerTurn = 0;
        } else {
            playerTurn = 1;
        }
    }

    /* Method for returning a boolean indicating if the game is ended (caused by a wrong guess). */
    public boolean ended() {
        return wrongGuess;
    }

    /* Method for returning the player that has won. If game is not won (not ended) return -1. */
    public int winner() {
        if (ended()) {
            if (playerTurn == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    /* Method for returning a boolean indicating whether a word was made or not. */
    public boolean wordCreated() {
        return madeWord;
    }

    /* Method for setting the word of an unfinished game.
     * Method called when unfinished game is resumed.
     */
    public void setWord(String word) {
        word = word.toLowerCase();
        currentWord = word;
        myLexicon.setWord(word);
    }
}