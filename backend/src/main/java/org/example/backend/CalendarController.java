package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/current-month")
    public VocabIdsOfDate[][] getVocabsOfCurrentMonth() {
        return calendarService.getVocabsOfMonth(YearMonth.now());
    }

    @GetMapping("/other-month")
    public VocabIdsOfDate[][] getVocabsOfMonth(@RequestParam String month, @RequestParam String year) {
        return calendarService.getVocabsOfMonth(YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)));
    }
}
