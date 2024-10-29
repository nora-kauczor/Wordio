package org.example.backend.calendar;

import lombok.RequiredArgsConstructor;
import org.example.backend.LanguageNotFoundException;
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

    @GetMapping
    public Month getMonth(@RequestParam String month, @RequestParam String year, @RequestParam String language) throws LanguageNotFoundException {
        return calendarService.getMonth(YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)), language);
    }
}