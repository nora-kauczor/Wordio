package org.example.backend.check;

import org.example.backend.vocab.VocabRepo;
import org.example.backend.vocab.VocabService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CheckServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final CheckService checkService = new CheckService(mockVocabRepo);

    @Test
    void isUserAnswerCorrect() {
    }

    @Test
    void getCorrectAnswers() {
    }

    @Test
    void getInputWithoutExtraSpaces_shouldReturnInputWithoutSpacesExceptForSingleSpacesInTheMiddle_whenCalledWithUserAnswer() {
        String userAnswer = "  la     prueba   ";
        String expected = "la prueba";
        String actual = checkService.getInputWithoutExtraSpaces(userAnswer);
        assertEquals(expected, actual);
    }

}