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

    public VocabIdsOfDate[][] getVocabsOfMonth2(String month, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        YearMonth nextYearMonth = YearMonth.of(yearInt, monthInt);
        return getVocabsOfMonth(nextYearMonth);
    }

    public VocabIdsOfDate[][] getVocabsOfMonth(YearMonth yearMonth) {
        List<VocabIdsOfDate> idsAndDates = new ArrayList<>();
        for (int i = 1; i < yearMonth.lengthOfMonth(); i++) {
           LocalDate day = yearMonth.atDay(i);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day);
            idsAndDates.add(idsAndDateOfDay);
        }
        // generate an empty 3-D array
        VocabIdsOfDate[][] vocabsOfMonth = new VocabIdsOfDate[5][7];
        // find out what weekday the 1st is
        DayOfWeek weekdayOfFirstDay = yearMonth.atDay(1).getDayOfWeek();
        int calendarIndexOfFirstDay = weekdayOfFirstDay.getValue() - 1;
        // place it accordingly (according to weekday) in first line of 3-D array
        vocabsOfMonth[0][calendarIndexOfFirstDay] = idsAndDates.getFirst();
        // places all following dates in the array
        // week 1
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[0][i] = idsAndDates.getFirst();
            idsAndDates.removeFirst();
        }
        // week 2-4
        for (int z = 1; z < 4; z++) {
            for (int i = 0; i < 7; i++) {
                vocabsOfMonth[z][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
        // week 5
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            if (!idsAndDates.isEmpty()) {
                vocabsOfMonth[4][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
        return vocabsOfMonth;
    }


    public VocabIdsOfDate getVocabIdsOfDate(LocalDate date) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfDate = allVocabs.stream().filter(vocab ->
                vocab.reviewDates.stream().anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        List<String> ids = vocabsOfDate.stream().map(vocab -> vocab._id).toList();
        return new VocabIdsOfDate(date, ids);
    }


}
