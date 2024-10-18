package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarService {


    private final VocabRepo vocabRepo;

    public VocabIdsOfDate getVocabIdsOfDate(String date) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfDate = allVocabs.stream().filter(vocab ->
                vocab.reviewDates.stream().anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        List<String> ids = vocabsOfDate.stream().map(vocab -> vocab._id).toList();
        return new VocabIdsOfDate(date, ids);
    }


}
