package org.example.backend.review;


import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;

import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;

import org.example.backend.vocab.VocabService;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReviewDayService {

    private final ReviewDayRepo reviewDayRepo;
    private final VocabService vocabService;

    public ReviewDay getReviewDay(String languageString, String userId, LocalDate day) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        Map<String, Boolean> idsOfTodaysVocabs = new HashMap<>();
        try {
            idsOfTodaysVocabs = getIdsOfTodaysVocabs(language, userId, day);
        } catch (
                LanguageNotFoundException languageNotFoundException) {
            throw new RuntimeException("Couldn't create ReviewDay because Language was not found.", languageNotFoundException);
        }
        Optional<ReviewDay> optionalReviewDay = Optional.ofNullable(reviewDayRepo.getByDayAndUserIdAndLanguage(day, userId, language));
        Map<String, Boolean> finalIdsOfTodaysVocabs = idsOfTodaysVocabs;
        Map<String, Boolean> finalIdsOfTodaysVocabs1 = idsOfTodaysVocabs;
        ReviewDay reviewDay = optionalReviewDay.map(oldReviewDay -> {
                    List<String> idsOfReviewedVocabs = oldReviewDay.idsOfVocabsToReview().entrySet().stream()
                            .filter(Map.Entry::getValue)
                            .map(Map.Entry::getKey)
                            .toList();
                    Map<String, Boolean> idsOfNonReviewedVocabs = finalIdsOfTodaysVocabs.entrySet()
                            .stream().filter(pair -> !idsOfReviewedVocabs.contains(pair.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    ReviewDay newReviewDay = new ReviewDay(oldReviewDay.id(), oldReviewDay.day(),
                            oldReviewDay.language(), oldReviewDay.userId(), idsOfNonReviewedVocabs);
                    return reviewDayRepo.save(newReviewDay);
                })
                .orElseGet(() -> {
                        ReviewDay newReviewDay = new ReviewDay(null, day,
                                language, userId, finalIdsOfTodaysVocabs1);
                        return reviewDayRepo.save(newReviewDay);
                });
        return reviewDayRepo.save(reviewDay);
    }

    public Map<String, Boolean> getIdsOfTodaysVocabs(Language language, String userId, LocalDate day) throws LanguageNotFoundException {
        List<Vocab> allVocabsOfLanguage = vocabService.getAllVocabsOfLanguage(language.getStringOfEnum(), userId);
        if (allVocabsOfLanguage.isEmpty()) {
            return new HashMap<>();
        }
        List<Vocab> activeVocabs = allVocabsOfLanguage.stream()
                .filter(vocab -> vocab.getDatesPerUser().containsKey(userId))
                .toList();
        if (activeVocabs.isEmpty()) {
            return new HashMap<>();
        }
        List<Vocab> todaysVocabs = activeVocabs.stream()
                .filter(vocab -> vocab.getDatesPerUser().get(userId).contains(day))
                .toList();
        Map<String, Boolean> idsToReview = new HashMap<>();
        for (Vocab vocab : todaysVocabs) {
            idsToReview.put(vocab.getId(), false);
        }
        return idsToReview;
    }


    public ReviewDay setVocabReviewed(String vocabId, String languageString, String userId, LocalDate day) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        Optional<ReviewDay> oldReviewDay = Optional.of(reviewDayRepo.getByDayAndUserIdAndLanguage(day, userId, language));
        Map<String, Boolean> newIdsOfVocabsToReview = oldReviewDay.get().idsOfVocabsToReview();
        newIdsOfVocabsToReview.put(vocabId, true);
        ReviewDay updatedReviewDay = new ReviewDay(oldReviewDay.get().id(), oldReviewDay.get().day(), language, userId, newIdsOfVocabsToReview);
        return reviewDayRepo.save(updatedReviewDay);
    }
}
