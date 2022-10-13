package com.c.refactoring.movie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTest {
    @Test
    void testValidBRatings() {
        assertFalse(new Movie("B5").isValidRating());
        assertTrue(new Movie("B3").isValidRating());
        assertFalse(new Movie("B0").isValidRating());
    }

    @Test
    void testValidARatings() {
        assertTrue(new Movie("A99").isValidRating());
        assertFalse(new Movie("A099").isValidRating());
        assertTrue(new Movie("A01").isValidRating());
        assertFalse(new Movie("A").isValidRating());
    }

    @Test
    void testNullRating_returnsFalse() {
        Movie movie = new Movie(null);
        assertFalse(movie.isValidRating());
    }

    @Test
    void testOtherLetterRatings_returnFalse() {
        assertFalse(new Movie("C12").isValidRating());
        assertFalse(new Movie("M1").isValidRating());
    }
}