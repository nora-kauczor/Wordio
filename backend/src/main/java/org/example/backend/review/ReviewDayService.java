package org.example.backend.review;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.UserNotFoundException;
import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.example.backend.vocab.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ReviewDayService {

    private final ReviewDayRepo reviewDayRepo;
    private final VocabService vocabService;

    public ReviewDay getReviewDay(String language, String userId, LocalDate day) {
        System.out.println("Debug: Language: " + language + ", UserId: " + userId);
        Optional<ReviewDay> optionalReviewDay = Optional.ofNullable(reviewDayRepo.getByDayAndUserId(day, userId));
        System.out.println("optionalReviewDay: " + optionalReviewDay);
        return optionalReviewDay.orElseGet(() -> {
            ReviewDay newReviewDay = null;
            try {
                newReviewDay = createReviewDay(language, userId, day);
            } catch (LanguageNotFoundException e) {
                //
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            assert newReviewDay != null;
            return reviewDayRepo.save(newReviewDay);
        });
    }

    ReviewDay createReviewDay(String language, String userId, LocalDate day) throws LanguageNotFoundException, UserNotFoundException {
        List<Vocab> allVocabsOfLanguage = vocabService.getAllVocabsOfLanguage(language, userId);
        if (allVocabsOfLanguage.isEmpty()) {
            return new ReviewDay(null, day, userId, new HashMap<>());
        }
        List<Vocab> activeVocabsOfCurrentUser = allVocabsOfLanguage.stream()
                .filter(vocab -> vocab.getDatesPerUser().containsKey(userId))
                .toList();
        if (activeVocabsOfCurrentUser.isEmpty()) {
            return new ReviewDay(null, day, userId, new HashMap<>());
        }

        List<Vocab> todaysVocabs = activeVocabsOfCurrentUser.stream()
                .filter(vocab -> vocab.getDatesPerUser().get(userId).contains(day))
                .toList();
        Map<String, Boolean> IdsOfVocabsToReview = new HashMap<>();
        for (Vocab todaysVocab : todaysVocabs) {
            String id = todaysVocab.getId();
            IdsOfVocabsToReview.put(id, false);
        }
        return new ReviewDay(null, day, userId, IdsOfVocabsToReview);
    }

    public ReviewDay setVocabReviewed(String vocabId, String userName, LocalDate day) {
        ReviewDay reviewDay = reviewDayRepo.getByDayAndUserId(day, userName);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put(vocabId, true);
        ReviewDay updatedReviewDay = new ReviewDay(reviewDay.id(), reviewDay.day(), userName, idsOfVocabsToReview);
        return reviewDayRepo.save(updatedReviewDay);
    }
}
