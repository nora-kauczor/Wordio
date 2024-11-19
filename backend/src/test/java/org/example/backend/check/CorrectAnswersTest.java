package org.example.backend.check;

import org.example.backend.vocab.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrectAnswersTest {

    @Test
    void getLeftSideOfSlash_shouldReturnBeau_whenCalledWithBeauBelle() {
        String word = "beau/belle";
        String expected = "beau";
        String actual = CorrectAnswers.getLeftSideOfSlash(word);
        assertEquals(expected, actual);
    }

    @Test
    void getLeftSideOfSlash_shouldReturnLaLiberte_whenCalledWithLaLiberte() {
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getLeftSideOfSlash(word);
        assertEquals(expected, actual);
    }

    @Test
    void getRightSideOfSlash_shouldReturnBelle_whenCalledWithBeauBelle() {
        String word = "beau/belle";
        String expected = "belle";
        String actual = CorrectAnswers.getRightSideOfSlash(word);
        assertEquals(expected, actual);
    }

    @Test
    void getRightSideOfSlash_shouldReturnLaLiberte_whenCalledWithLaLiberte() {
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getRightSideOfSlash(word);
        assertEquals(expected, actual);
    }

    @Test
    void getLeftSideOfSlashWithEndingOfRightSide_shouldReturnRoja_whenCalledWithRojoA() {
        String word = "rojo/-a";
        String expected = "roja";
        String actual = CorrectAnswers.getLeftSideOfSlashWithEndingOfRightSide(word);
        assertEquals(expected, actual);
    }

    @Test
    void getLeftSideOfSlashWithEndingOfRightSide_shouldReturnBeauBelle_whenCalledWithBeauBelle() {
        String word = "beau/belle";
        String expected = "beau/belle";
        String actual = CorrectAnswers.getLeftSideOfSlashWithEndingOfRightSide(word);
        assertEquals(expected, actual);
    }

    @Test
    void
    getWordWithoutArticle_shouldReturnSueno_whenCalledWithElSuenoAndSpanish(){
        String word = "el sueño";
        String expected = "sueño";
        String actual = CorrectAnswers.getWordWithoutArticle(word, Language.SPANISH);
        assertEquals(expected, actual);
    }

    @Test
    void
    getWordWithoutArticle_shouldReturnElSueno_whenCalledWithElSuenoAndFrench(){
        String word = "el sueño";
        String expected = "el sueño";
        String actual = CorrectAnswers.getWordWithoutArticle(word, Language.FRENCH);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBrackets_shouldReturnContente_whenCalledWithContente(){
        String word = "content(e)";
        String expected = "contente";
        String actual = CorrectAnswers.getWordWithoutBrackets(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBrackets_shouldReturnLaLiberte_whenCalledWithLaLiberte(){
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getWordWithoutBrackets(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBracketsAndWithoutBracketsContent_shouldReturnContent_whenCalledWithContente(){
        String word = "content(e)";
        String expected = "content";
        String actual = CorrectAnswers.getWordWithoutBracketsAndWithoutBracketsContent(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBracketsAndWithoutBracketsContent_shouldReturnLaLiberte_whenCalledWithLaLiberte(){
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getWordWithoutBracketsAndWithoutBracketsContent(word);
        assertEquals(expected, actual);
    }

}