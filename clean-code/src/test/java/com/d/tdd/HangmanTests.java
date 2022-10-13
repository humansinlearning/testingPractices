package com.d.tdd;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HangmanTests {

    List<String> words = Arrays.asList("","rand","asbbvc");

    @Test
    public void guessWOrdsByEachLetter(){
//        int wordLength = words.get(1).length();
//        Hangman hangman = new Hangman();
//        assertTrue(hangman.getPlayerInput('a'));
//        assertFalse(hangman.getPlayerInput('x'));
//        assertFalse(hangman.getPlayerInput('3'));
//        assertFalse(hangman.getPlayerInput('and'));
    }

    @Test
    public void testReturnWOrdOnceGuessed(){
//        int wordLength = words.get(1).length();
//        Hangman hangman = new Hangman();
//        assertTrue(hangman.getPlayerInput('a'));
//        assertFalse(hangman.getPlayerInput('x'));
//        assertFalse(hangman.getPlayerInput('3'));
//        assertFalse(hangman.getPlayerInput('and'));
    }

    @Test
    public void returnLenghtOfDashesForGivenWord(){
        /*int wordLength = words.get(1).length();
        assertEquals(arrayOfDashes.length, wordLength);*/
    }

    @Test
    public void testCountMistakes(){
        //rand is the word
//        int wordLength = words.get(1).length();
//        Hangman hangman = new Hangman();
//        assertEquals(hangman.returnRemainedTries, hangman.NumberOfTries -1);
    }

}
