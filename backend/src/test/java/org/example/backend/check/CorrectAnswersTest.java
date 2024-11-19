package org.example.backend.check;

import org.example.backend.vocab.Language;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorrectAnswersTest {

    @Test
    void getCorrectAnswers_shouldReturnListOfCorrectAnswers_whenCalledWithWordAndLanguage() {
        String word = "un(e) professeur(e)";
        Language language = Language.FRENCH;
        List<String> expected = List.of(word, word, "professeur(e)", word, "professeur(e)", word,
                "professeur(e)",
                "professeure",
                "une professeure",
                "professeur",
                "un professeur");
        List<String> actual = CorrectAnswers.getCorrectAnswers(word, language);
        assertEquals(expected, actual);
    }

    @Test
    void getCorrectAnswers_shouldReturnListOfCorrectAnswers_whenCalledWithWordAndLanguage2() {
        String word = "une table";
        Language language = Language.FRENCH;
        List<String> expected = List.of(word, word, "table", word, "table", word,
                "table", "table",
                word, "table", word);
        List<String> actual = CorrectAnswers.getCorrectAnswers(word, language);
        assertEquals(expected, actual);
    }

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
    getWordWithoutArticle_shouldReturnSueno_whenCalledWithElSuenoAndSpanish() {
        String word = "el sueño";
        String expected = "sueño";
        String actual = CorrectAnswers.getWordWithoutArticle(word, Language.SPANISH);
        assertEquals(expected, actual);
    }


    @Test
    void
    getWordWithoutArticle_shouldReturnProfesseure_whenCalledWithUneProfesseure() {
        String word = "un(e) professeur(e)";
        String expected = "professeur(e)";
        String actual = CorrectAnswers.getWordWithoutArticle(word, Language.SPANISH);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBrackets_shouldReturnContente_whenCalledWithContente() {
        String word = "content(e)";
        String expected = "contente";
        String actual = CorrectAnswers.getWordWithoutBrackets(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBrackets_shouldReturnLaLiberte_whenCalledWithLaLiberte() {
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getWordWithoutBrackets(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBracketsAndWithoutBracketsContent_shouldReturnContent_whenCalledWithContente() {
        String word = "content(e)";
        String expected = "content";
        String actual = CorrectAnswers.getWordWithoutBracketsAndWithoutBracketsContent(word);
        assertEquals(expected, actual);
    }

    @Test
    void getWordWithoutBracketsAndWithoutBracketsContent_shouldReturnLaLiberte_whenCalledWithLaLiberte() {
        String word = "la liberté";
        String expected = "la liberté";
        String actual = CorrectAnswers.getWordWithoutBracketsAndWithoutBracketsContent(word);
        assertEquals(expected, actual);
    }

}