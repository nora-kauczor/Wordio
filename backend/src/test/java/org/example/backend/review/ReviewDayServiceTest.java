package org.example.backend.review;

import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewDayServiceTest {
    private final ReviewDayRepo mockReviewRepo = mock(ReviewDayRepo.class);
    private final VocabService mockVocabService = mock(VocabService.class);
    private final ReviewDayService reviewService = new ReviewDayService(mockReviewRepo, mockVocabService);

    @Test
    void getReviewDay_shouldReturnReviewDayOfCurrentDate_ifItExists_whenCalled() throws LanguageNotFoundException {
        LocalDate date = LocalDate.of(2025, 1, 1);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab id", false);
        ReviewDay reviewDay = new ReviewDay("000", date, Language.SPANISH, "user id", idsOfVocabsToReview);
        mockReviewRepo.save(reviewDay);
        when(mockReviewRepo.getByDayAndUserIdAndLanguage(date, "user id", Language.SPANISH)).thenReturn(reviewDay);
        assertEquals(reviewDay, reviewService.getReviewDay("Spanish", "user id", date));
    }

    @Test
    void getReviewDay_shouldReturnNewReviewDayOfCurrentDate_ifNonExistedBefore_whenCalled() throws LanguageNotFoundException {
        LocalDate date = LocalDate.of(2025, 1, 1);
        when(mockReviewRepo.getByDayAndUserIdAndLanguage(date, "user id", Language.SPANISH)).thenReturn(null);
        // java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElseGet(java.util.function.Supplier)" because "optionalReviewDay" is null
        reviewService.getReviewDay("Spanish", "user id", date);
        verify(mockReviewRepo).save(any(ReviewDay.class));
    }

    @Test
    void getReviewDay_shouldThrowLanguageNotFoundException_whenCalledWithNonExistentLanguage()  {
        LocalDate date = LocalDate.of(2025, 1, 1);
        assertThrows(LanguageNotFoundException.class, () ->
                reviewService.getReviewDay("Esperanto", "user id", date));
    }

    @Test
    void createReviewDay() throws LanguageNotFoundException {
        LocalDate date = LocalDate.of(2025, 1, 1);
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("user id", List.of(date));
        Vocab testVocab = new Vocab("vocab id", "la prueba",
                "test", "", Language.SPANISH, datesPerUser, "Wordio");
        when(mockVocabService.getAllVocabsOfLanguage("Spanish", "user id")).thenReturn(List.of(testVocab));
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab id", false);
        ReviewDay expected = new ReviewDay(null, date,Language.SPANISH, "user id", idsOfVocabsToReview);
        ReviewDay actual = reviewService.createReviewDay( Language.SPANISH,"user id", date);
        assertEquals(expected, actual);
    }


    @Test
    void setVocabReviewed() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab id", false);
        ReviewDay oldReviewDate = new ReviewDay("123", date, Language.SPANISH,"user id", idsOfVocabsToReview);
        Map<String, Boolean> newIdsOfVocabsToReview = new HashMap<>();
        newIdsOfVocabsToReview.put("vocab id", true);
        ReviewDay newReviewDay = new ReviewDay("123", date,Language.SPANISH, "user id", newIdsOfVocabsToReview);
        when(mockReviewRepo.getByDayAndUserIdAndLanguage(date, "user id", Language.SPANISH)).thenReturn(oldReviewDate);
        when(mockReviewRepo.save(newReviewDay)).thenReturn(newReviewDay);
        ReviewDay actual = reviewService.setVocabReviewed("vocab id", "user id", date);
        verify(mockReviewRepo).save(any(ReviewDay.class));
        assertTrue(actual.idsOfVocabsToReview().get("vocab id"));
    }
}