package org.example.backend.check;

import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CheckService checkService = new CheckService(mockVocabRepo);

    @Test
    void isUserAnswerCorrect_ShouldReturnTrue_whenCalledWithIdOfBeauBelleAndUserAnswerBeau() {
        Vocab testVocab = new Vocab("vocab id", "beau/belle", "beautiful",
                "", Language.FRENCH, new HashMap<>(), "Wordio");
        String userAnswer = "beau";
        when(mockVocabRepo.findById("vocab id")).thenReturn(Optional.of(testVocab));
        assertTrue(checkService.isUserAnswerCorrect("vocab id", userAnswer));
    }

    @Test
    void isUserAnswerCorrect_ShouldReturnFalse_whenCalledWithIdOfBeauBelleAndUserAnswerBaeu() {
        Vocab testVocab = new Vocab("vocab id", "beau/belle", "beautiful",
                "", Language.FRENCH, new HashMap<>(), "Wordio");
        String userAnswer = "baeu";
        when(mockVocabRepo.findById("vocab id")).thenReturn(Optional.of(testVocab));
        assertFalse(checkService.isUserAnswerCorrect("vocab id", userAnswer));
    }

    @Test
    void getInputWithoutExtraSpaces_shouldReturnInputWithoutSpacesExceptForSingleSpacesInTheMiddle_whenCalledWithUserAnswer() {
        String userAnswer = "  la     prueba   ";
        String expected = "la prueba";
        String actual = checkService.getInputWithoutExtraSpaces(userAnswer);
        assertEquals(expected, actual);
    }

}