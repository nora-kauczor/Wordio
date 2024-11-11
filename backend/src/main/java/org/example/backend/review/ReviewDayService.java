package org.example.backend.review;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class ReviewDayService {
    private final ReviewDayRepo reviewDayRepo;
    private final VocabService vocabService;

    public ReviewDay getReviewDay(String language, String userName, LocalDate day) {
        Optional<ReviewDay> optionalReviewDay = Optional.of(reviewDayRepo.getByDayAndUserId(day, userName));
        return optionalReviewDay.orElseGet(() -> {
            try {
                return createReviewDay(language, userName, day);
            } catch (LanguageNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ReviewDay createReviewDay(String language, String userName, LocalDate day) throws LanguageNotFoundException {
        List<Vocab> allVocabsOfLanguage = vocabService.getAllVocabsOfLanguage(language, userName);
        List<Vocab> todaysVocabs = allVocabsOfLanguage.stream()
                .filter(vocab -> vocab.getDatesPerUser().get(userName).contains(day))
                .toList();
        Map<String, Boolean> IdsOfVocabsToReview = new HashMap<>();
        for (Vocab todaysVocab : todaysVocabs) {
            String id = todaysVocab.getId();
            IdsOfVocabsToReview.put(id, false);
        }
        return new ReviewDay(null, day, userName, IdsOfVocabsToReview);
    }

    public ReviewDay setVocabReviewed(String vocabId, String userName, LocalDate day) {
        ReviewDay reviewDay = reviewDayRepo.getByDayAndUserId(day, userName);
        System.out.println("from line 51: "+reviewDay);
        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
        idsOfVocabsToReview.put(vocabId, true);
        ReviewDay updatedReviewDay = new ReviewDay(reviewDay.id(), reviewDay.day(), userName, idsOfVocabsToReview);
        return reviewDayRepo.save(updatedReviewDay);
    }
}
