package org.example.backend.calendar;

import lombok.RequiredArgsConstructor;
import org.example.backend.vocab.Language;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
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

    public Month getMonth(YearMonth yearMonth, String languageString, String userName) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(languageString);
        List<VocabIdsOfDate> idsAndDates = getVocabIdsOfMonth(yearMonth, language, userName);
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

    private List<VocabIdsOfDate> getVocabIdsOfMonth(YearMonth yearMonth, Language language, String userName) {
        List<VocabIdsOfDate> idsAndDates = new ArrayList<>();
            for (int i = 1; i < yearMonth.lengthOfMonth(); i++) {
            LocalDate day = yearMonth.atDay(i);
            VocabIdsOfDate idsAndDateOfDay = getVocabIdsOfDate(day, language, userName);
            idsAndDates.add(idsAndDateOfDay);
        }
        return idsAndDates;
    }

    public VocabIdsOfDate getVocabIdsOfDate(LocalDate date, Language language, String userName) {
        List<String> ids = getVocabIdsOfDateAsList(date, language, userName);
        return new VocabIdsOfDate(date, ids);
    }

    public List<String> getVocabIdsOfDateAsList(LocalDate date, Language language, String userName) {
        List<Vocab> allVocabs = vocabRepo.findAll();
        List<Vocab> vocabsOfDate = allVocabs.stream()
                .filter(vocab -> vocab.getLanguage().equals(language))
                .filter(vocab -> vocab.getDatesPerUser() != null && vocab.getDatesPerUser().containsKey(userName))
                .filter(vocab -> vocab.getDatesPerUser().get(userName).stream()
                        .anyMatch(reviewDate -> reviewDate.equals(date))).toList();
        return vocabsOfDate.stream().map(Vocab::getId).toList();
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
