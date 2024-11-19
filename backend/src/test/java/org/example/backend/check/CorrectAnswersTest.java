package org.example.backend.check;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrectAnswersTest {


    @Test
    void getLeftSideOfSlash() {
        String word = "beau/belle";
        String expected = "beau";
        String actual = CorrectAnswers.getLeftSideOfSlash(word);
        assertEquals(expected, actual);
    }

    @Test
    void getRightSideOfSlash() {
        String word = "beau/belle";
        String expected = "belle";
        String actual = CorrectAnswers.getRightSideOfSlash(word);
        assertEquals(expected, actual);
    }
}