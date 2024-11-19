package org.example.backend.review;


import lombok.RequiredArgsConstructor;
import org.example.backend.calendar.CalendarService;
import org.example.backend.exception.LanguageNotFoundException;

import org.example.backend.vocab.Language;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;



@RequiredArgsConstructor
@Service
public class ReviewDayService {

    private final ReviewDayRepo reviewDayRepo;
    private final CalendarService calendarService;

    public ReviewDay getReviewDay(String languageString, String userId, LocalDate day) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        List<String> idList = calendarService.getVocabIdsOfDateAsList(day, language, userId);
        Optional<ReviewDay> optionalReviewDay = Optional.ofNullable(reviewDayRepo.getByDayAndUserIdAndLanguage(day, userId, language));
        ReviewDay reviewDay = optionalReviewDay.map(oldReviewDay -> {
                    List<String> idsOfReviewedVocabs = oldReviewDay.idsOfVocabsToReview().entrySet().stream()
                            .filter(Map.Entry::getValue)
                            .map(Map.Entry::getKey)
                            .toList();
                    Map<String, Boolean> idMap = new HashMap<>();
                    for (String id : idList) {
                        if (idsOfReviewedVocabs.contains(id))
                        {idMap.put(id, true);} else {
                            idMap.put(id, false);
                        }
                    }
                    ReviewDay newReviewDay = new ReviewDay(oldReviewDay.id(), oldReviewDay.day(),
                            oldReviewDay.language(), oldReviewDay.userId(), idMap);
                    return reviewDayRepo.save(newReviewDay);
                })
                .orElseGet(() -> {
                    Map<String, Boolean> idMap = new HashMap<>();
                    for (String id : idList) {
                        idMap.put(id, false);
                    }
                    ReviewDay newReviewDay = new ReviewDay(null, day,
                            language, userId, idMap);
                    return reviewDayRepo.save(newReviewDay);
                });
        return reviewDayRepo.save(reviewDay);
    }

    public ReviewDay setVocabReviewed(String vocabId, String languageString, String userId, LocalDate day) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        Optional<ReviewDay> oldReviewDay = Optional.of(reviewDayRepo.getByDayAndUserIdAndLanguage(day, userId, language));
        Map<String, Boolean> newIdsOfVocabsToReview = oldReviewDay.get().idsOfVocabsToReview();
        newIdsOfVocabsToReview.put(vocabId, true);
        ReviewDay updatedReviewDay = new ReviewDay(oldReviewDay.get().id(), oldReviewDay.get().day(), language, userId, newIdsOfVocabsToReview);
        return reviewDayRepo.save(updatedReviewDay);
    }

    // TODO methode die einmal im monat oder öfter die alten review day objekte löscht, woher gecallt?
}
