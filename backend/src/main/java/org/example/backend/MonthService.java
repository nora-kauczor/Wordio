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

   public List<Vocab> getVocabsPerDate(String date){
       List<Vocab> allVocabs = vocabRepo.findAll();
       return allVocabs.stream().filter(vocab ->
               vocab.reviewDates.stream().anyMatch(date)).toList();
}


}
