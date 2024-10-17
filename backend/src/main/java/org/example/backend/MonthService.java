package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MonthService {

    private final VocabRepo vocabRepo;

    public Month buildMonth() {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfMonth = allVocabs.stream().filter(vocab -> checkIfAnyDateOfVocabIsInYearMonth(vocab, yearMonth)).toList();
        return new Month(yearMonth, vocabsOfMonth);
    }

    public boolean checkIfAnyDateOfVocabIsInYearMonth(Vocab vocab, YearMonth yearMonth) {
        return vocab.reviewDates.stream().anyMatch(date -> yearMonth.equals(YearMonth.from(date)));
    }


}
