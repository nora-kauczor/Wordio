package org.example.backend.review;


import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.UserNotFoundException;

import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;

import org.example.backend.vocab.VocabService;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ReviewDayService {

    private final ReviewDayRepo reviewDayRepo;
    private final VocabService vocabService;

    public ReviewDay getReviewDay(String languageString, String userId, LocalDate day) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        Optional<ReviewDay> optionalReviewDay = Optional.ofNullable(reviewDayRepo.getByDayAndUserIdAndLanguage(day, userId, language));
        return optionalReviewDay.orElseGet(() -> {
            try {
                ReviewDay newReviewDay = createReviewDay(language, userId, day);
                if (newReviewDay == null) {
                    throw new IllegalStateException("Failed to create a new ReviewDay.");
                }
                return reviewDayRepo.save(newReviewDay);
            } catch (LanguageNotFoundException e) {
                throw new RuntimeException("Language not found: " + language, e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException("User not found: " + userId, e);
            }
        });
    }


    ReviewDay createReviewDay(Language language, String userId, LocalDate day) throws LanguageNotFoundException, UserNotFoundException {
        List<Vocab> allVocabsOfLanguage = vocabService.getAllVocabsOfLanguage(language.getStringOfEnum(), userId);
        if (allVocabsOfLanguage.isEmpty()) {
            return new ReviewDay(null, day, language, userId, new HashMap<>());
        }
        List<Vocab> activeVocabs = allVocabsOfLanguage.stream()
                .filter(vocab -> vocab.getDatesPerUser().containsKey(userId))
                .toList();
        if (activeVocabs.isEmpty()) {
            return new ReviewDay(null, day, language, userId, new HashMap<>());
        }
        List<Vocab> todaysVocabs = activeVocabs.stream()
                .filter(vocab -> vocab.getDatesPerUser().get(userId).contains(day))
                .toList();
        Map<String, Boolean> idsToReview = new HashMap<>();
        for (Vocab vocab : todaysVocabs) {
            idsToReview.put(vocab.getId(), false);
        }
        return new ReviewDay(null, day, language, userId, idsToReview);
    }


    public ReviewDay setVocabReviewed(String vocabId, String userName, LocalDate day) {
        ReviewDay reviewDay = reviewDayRepo.getByDayAndUserIdAndLanguage(day, userName, Language.SPANISH);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put(vocabId, true);
        ReviewDay updatedReviewDay = new ReviewDay(reviewDay.id(), reviewDay.day(), Language.SPANISH, userName, idsOfVocabsToReview);
        return reviewDayRepo.save(updatedReviewDay);
    }
}
