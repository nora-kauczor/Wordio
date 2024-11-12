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


//    ReviewDay createReviewDay(Language language, String userId, LocalDate day) throws LanguageNotFoundException, UserNotFoundException {
//        List<Vocab> allVocabsOfLanguage = vocabService.getAllVocabsOfLanguage(language.toString(), userId);
//        System.out.println(allVocabsOfLanguage);
//        // wenn es gar keine vokabeln der sprache gibt, gib einen review day mit leerer id-map zurück
//        if (allVocabsOfLanguage.isEmpty()) {
//            return new ReviewDay(null, day, language, userId, new HashMap<>());
//        }
//        List<Vocab> activeVocabsOfCurrentUser = allVocabsOfLanguage.stream()
//                .filter(vocab -> vocab.getDatesPerUser().containsKey(userId))
//                .toList();
//        System.out.println("activeVocabsOfCurrentUser: "+ activeVocabsOfCurrentUser);
//        // wenn keine vokabeln dieser sprache für diesen user aktiv sind, gib einen review day mit leerer id-map zurück
//        if (activeVocabsOfCurrentUser.isEmpty()) {
//            return new ReviewDay(null, day, language, userId, new HashMap<>());
//        }
//
//        List<Vocab> todaysVocabs = activeVocabsOfCurrentUser.stream()
//                .filter(vocab -> vocab.getDatesPerUser().get(userId).contains(day))
//                .toList();
//        System.out.println("todaysVocab: "+todaysVocabs);
//        Map<String, Boolean> IdsOfVocabsToReview = new HashMap<>();
//        for (Vocab todaysVocab : todaysVocabs) {
//            String id = todaysVocab.getId();
//            System.out.println(id);
//            IdsOfVocabsToReview.put(id, false);
//        }
//        return new ReviewDay(null, day, language, userId, IdsOfVocabsToReview);
//    }

//    public ReviewDay setVocabReviewed(String vocabId, String userName, LocalDate day) {
//        ReviewDay reviewDay = reviewDayRepo.getByDayAndUserId(day, userName);
//        Map<String, Boolean> idsOfVocabsToReview = new HashMap<>();
//        idsOfVocabsToReview.put(vocabId, true);
//        ReviewDay updatedReviewDay = new ReviewDay(reviewDay.id(), reviewDay.day(), userName, idsOfVocabsToReview);
//        return reviewDayRepo.save(updatedReviewDay);
//    }
}
