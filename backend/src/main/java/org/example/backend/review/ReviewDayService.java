package org.example.backend.review;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.UserNotFoundException;
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


    public ReviewDay getReviewDay(String language, String userId, LocalDate day) {
        Optional<ReviewDay> optionalReviewDay = Optional.ofNullable(reviewDayRepo.getByDayAndUserId(day, userId));
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
    if (allVocabsOfLanguage.isEmpty()){
        return new ReviewDay(null, day, userId, new HashMap<>());
    }
    List<Vocab> activeVocabsOfCurrentUser = allVocabsOfLanguage.stream()
            .filter(vocab -> vocab.getDatesPerUser().containsKey(userId))
            .toList();
    if (activeVocabsOfCurrentUser.isEmpty()) {return new ReviewDay(null, day, userId, new HashMap<>());}

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
    System.out.println("from line 51: " + reviewDay);
    Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
    idsOfVocabsToReview.put(vocabId, true);
    ReviewDay updatedReviewDay = new ReviewDay(reviewDay.id(), reviewDay.day(), userName, idsOfVocabsToReview);
    return reviewDayRepo.save(updatedReviewDay);
}
}
