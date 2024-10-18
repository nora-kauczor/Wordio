package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarService {


    private final VocabRepo vocabRepo;

    public VocabIdsOfDate[][] getVocabsOfMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E-dd-MM-yyyy");
        YearMonth yearMonth = YearMonth.now();
        List<VocabIdsOfDate> idsAndDates = new ArrayList<>();
        for (int i = 1; i < yearMonth.lengthOfMonth(); i++) {
            String day = yearMonth.atDay(i).format(formatter);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day);
            idsAndDates.add(idsAndDateOfDay);
        }
        VocabIdsOfDate[][] vocabsOfMonth = new VocabIdsOfDate[5][4];
        DayOfWeek weekdayOfFirstDay = yearMonth.atDay(1).getDayOfWeek();
        int calendarIndexOfFirstDay = weekdayOfFirstDay.getValue() - 1;
        vocabsOfMonth[0][calendarIndexOfFirstDay] = idsAndDates.getFirst();
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[0][i] = idsAndDates.getFirst();
            idsAndDates.removeFirst();
        }
        for (int z = 1; z < 4; z++) {
            for (int i = 0; i < 7; i++) {
                vocabsOfMonth[z][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[4][i] = idsAndDates.getFirst();
            idsAndDates.removeFirst();
        }
        return vocabsOfMonth;
    }


    public VocabIdsOfDate getVocabIdsOfDate(String date) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfDate = allVocabs.stream().filter(vocab ->
                vocab.reviewDates.stream().anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        List<String> ids = vocabsOfDate.stream().map(vocab -> vocab._id).toList();
        return new VocabIdsOfDate(date, ids);
    }


}
