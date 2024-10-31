package org.example.backend.calendar;

import lombok.RequiredArgsConstructor;
import org.example.backend.Language;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.Vocab;
import org.example.backend.VocabRepo;
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


    public Month getMonth(YearMonth yearMonth, String languageString) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        List<VocabIdsOfDate> idsAndDates = getVocabIdsOfMonth(yearMonth, language);
        VocabIdsOfDate[][] vocabIdsOfMonth = createEmptyCalendar();
        DayOfWeek weekdayOfFirstDay = yearMonth.atDay(1).getDayOfWeek();
        int calendarIndexOfFirstDay = weekdayOfFirstDay.getValue() - 1;
        fillWeek1(vocabIdsOfMonth, idsAndDates, calendarIndexOfFirstDay);
        fillWeek2To4(vocabIdsOfMonth, idsAndDates);
        fillWeek5(vocabIdsOfMonth, idsAndDates);
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("MMMM yyyy");
        String yearMonthName = yearMonth.format(formatter);
        return new Month(yearMonthName, vocabIdsOfMonth);
    }

    private List<VocabIdsOfDate> getVocabIdsOfMonth(YearMonth yearMonth, Language language) {
        List<VocabIdsOfDate> idsAndDates = new ArrayList<>();
        for (int i = 1; i < yearMonth.lengthOfMonth(); i++) {
            LocalDate day = yearMonth.atDay(i);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day, language);
            idsAndDates.add(idsAndDateOfDay);
        }
        return idsAndDates;
    }

    public VocabIdsOfDate getVocabIdsOfDate(LocalDate date, Language language) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        System.out.println(allVocabs);
        List<Vocab> vocabsOfDate = allVocabs.stream()
                .filter(vocab -> vocab.getLanguage().equals(language))
                .filter(vocab ->
                vocab.getReviewDates().stream().anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        List<String> ids = vocabsOfDate.stream().map(vocab -> vocab.get_id()).toList();
        return new VocabIdsOfDate(date, ids);
    }

   private VocabIdsOfDate[][] createEmptyCalendar() {
        return new VocabIdsOfDate[5][7];
    }

   private void fillWeek1(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates, int calendarIndexOfFirstDay) {
        for (int i = calendarIndexOfFirstDay; i < 7; i++) {
            vocabsOfMonth[0][i] = idsAndDates.getFirst();
            idsAndDates.removeFirst();
        }
    }

    private void fillWeek2To4(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates) {
        for (int z = 1; z < 4; z++) {
            for (int i = 0; i < 7; i++) {
                vocabsOfMonth[z][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
    }

    private void fillWeek5(VocabIdsOfDate[][] vocabsOfMonth, List<VocabIdsOfDate> idsAndDates) {
            for (int i = 0; i < 7; i++) {
                if (!idsAndDates.isEmpty()) {
                vocabsOfMonth[4][i] = idsAndDates.getFirst();
                idsAndDates.removeFirst();
            }
        }
    }







}
