package org.example.backend.review;

import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.UserNotFoundException;
import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
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
        ReviewDay reviewDay = new ReviewDay("000", date, "user id", idsOfVocabsToReview);
        mockReviewRepo.save(reviewDay);
        when(mockReviewRepo.getByDayAndUserId(date, "user id")).thenReturn(reviewDay);
        assertEquals(reviewDay, reviewService.getReviewDay("Spanish", "user id", date));
    }

    @Test
    void getReviewDay_shouldReturnNewReviewDayOfCurrentDate_ifNonExistedBefore_whenCalled() throws LanguageNotFoundException {
        LocalDate date = LocalDate.of(2025, 1, 1);
        when(mockReviewRepo.getByDayAndUserId(date, "user id")).thenReturn(null);
        reviewService.getReviewDay("Spanish", "user id", date);
        verify(mockReviewRepo).save(any(ReviewDay.class));
    }

    @Test
    void createReviewDay() throws LanguageNotFoundException, UserNotFoundException {
        LocalDate date = LocalDate.of(2025, 1, 1);
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("user id", List.of(date));
        Vocab testVocab = new Vocab("vocab id", "la prueba",
                "test", "", Language.SPANISH, datesPerUser, "Wordio");
        when(mockVocabService.getAllVocabsOfLanguage("Spanish", "user id")).thenReturn(List.of(testVocab));
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab id", false);
        ReviewDay expected = new ReviewDay(null, date, "user id", idsOfVocabsToReview);
        ReviewDay actual = reviewService.createReviewDay("Spanish", "user id", date);
        assertEquals(expected, actual);
    }


    @Test
    void setVocabReviewed() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put("vocab id", false);
        ReviewDay oldReviewDate = new ReviewDay("123", date, "user id", idsOfVocabsToReview);
        Map<String, Boolean> newIdsOfVocabsToReview = new HashMap<>();
        newIdsOfVocabsToReview.put("vocab id", true);
        ReviewDay newReviewDay = new ReviewDay("123", date, "user id", newIdsOfVocabsToReview);
        when(mockReviewRepo.getByDayAndUserId(date, "user id")).thenReturn(oldReviewDate);
        when(mockReviewRepo.save(newReviewDay)).thenReturn(newReviewDay);
        ReviewDay actual = reviewService.setVocabReviewed("vocab id", "user id", date);
        System.out.println(actual.idsOfVocabsToReview());
        verify(mockReviewRepo).save(any(ReviewDay.class));
        assertTrue(actual.idsOfVocabsToReview().get("vocab id"));
    }
}