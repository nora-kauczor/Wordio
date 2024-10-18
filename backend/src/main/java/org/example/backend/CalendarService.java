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
        List<String> daysOfMonth = new ArrayList<>();
        // alle tage erzeugen
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            daysOfMonth.add(yearMonth.atDay(day).format(formatter));
        }
        // objekte mit date und den ids zu dem tag erzeugen
        List<VocabIdsOfDate> idsAndDatesForAllDaysOfMonth = new ArrayList<>();
        for (int i = 1; i < daysOfMonth.size(); i++) {
            String day = daysOfMonth.get(i);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day);
            idsAndDatesForAllDaysOfMonth.add(idsAndDateOfDay);
        }
        VocabIdsOfDate[][] vocabsOfMonth = new VocabIdsOfDate[5][4];
        DayOfWeek weekdayOfFirstDay = yearMonth.atDay(1).getDayOfWeek();
        int calendarIndexOfFirstDay = weekdayOfFirstDay.getValue() - 1;
        vocabsOfMonth[0][calendarIndexOfFirstDay] = idsAndDatesForAllDaysOfMonth.getFirst();
        // Woche 1
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[0][i] = idsAndDatesForAllDaysOfMonth.getFirst();
            idsAndDatesForAllDaysOfMonth.removeFirst();
        }
        // Woche 2-4
        for (int z = 1; z < 4; z++) {
            for (int i = 0; i < 7; i++) {
                vocabsOfMonth[z][i] = idsAndDatesForAllDaysOfMonth.getFirst();
                idsAndDatesForAllDaysOfMonth.removeFirst();
            }
        }
        // Woche 5
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[4][i] = idsAndDatesForAllDaysOfMonth.getFirst();
            idsAndDatesForAllDaysOfMonth.removeFirst();
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
