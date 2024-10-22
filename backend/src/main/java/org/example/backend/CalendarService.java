package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarService {


    private final VocabRepo vocabRepo;

    public VocabIdsOfDate[][] getVocabsOfMonth(String month, String year) {
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        YearMonth nextYearMonth = YearMonth.of(yearInt, monthInt);
        return getVocabsOfMonth(nextYearMonth);
    }

    public VocabIdsOfDate[][] getVocabsOfMonth(YearMonth yearMonth) {
        List<VocabIdsOfDate> idsAndDates = getVocabIdsOfMonth(yearMonth);
        VocabIdsOfDate[][] vocabsOfMonth = createEmptyCalendar();
        DayOfWeek weekdayOfFirstDay = yearMonth.atDay(1).getDayOfWeek();
        int calendarIndexOfFirstDay = weekdayOfFirstDay.getValue() - 1;
        fillWeek1(vocabsOfMonth, idsAndDates, calendarIndexOfFirstDay);
        fillWeek2To4(vocabsOfMonth, idsAndDates);
        fillWeek5(vocabsOfMonth, idsAndDates, calendarIndexOfFirstDay);
        return vocabsOfMonth;
    }

    private List<VocabIdsOfDate> getVocabIdsOfMonth(YearMonth yearMonth) {
        List<VocabIdsOfDate> idsAndDates = new ArrayList<>();
        for (int i = 1; i < yearMonth.lengthOfMonth(); i++) {
            LocalDate day = yearMonth.atDay(i);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day);
            idsAndDates.add(idsAndDateOfDay);
        }
        return idsAndDates;
    }

    private VocabIdsOfDate getVocabIdsOfDate(LocalDate date) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfDate = allVocabs.stream().filter(vocab ->
                vocab.reviewDates.stream().anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        List<String> ids = vocabsOfDate.stream().map(vocab -> vocab._id).toList();
        return new VocabIdsOfDate(date, ids);
    }

    private VocabIdsOfDate[][] createEmptyCalendar() {
        return new VocabIdsOfDate[5][7];
    }

    private void fillWeek1(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates, int calendarIndexOfFirstDay) {
        vocabsOfMonth[0][calendarIndexOfFirstDay] = idsAndDates.getFirst();
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) {
            vocabsOfMonth[0][i] = idsAndDates.getFirst();
            idsAndDates.removeFirst();
        }
    }

    ;

    private void fillWeek2To4(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates) {
        for (int z = 1; z < 4; z++) {
            for (int i = 0; i < 7; i++) {
                vocabsOfMonth[z][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
    }

    ;

    private void fillWeek5(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates, int calendarIndexOfFirstDay) {
        for (int i = calendarIndexOfFirstDay + 1; i < 7; i++) { // TODO warum calendar IndexOfFirstDAy????? Aber hatte funktioniert
            if (!idsAndDates.isEmpty()) {
                vocabsOfMonth[4][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
    }

    ;







}
