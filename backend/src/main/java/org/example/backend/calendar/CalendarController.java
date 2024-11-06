package org.example.backend.calendar;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    public Month getMonth(@RequestParam String year, @RequestParam String month, @RequestParam String language, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return calendarService.getMonth(YearMonth.of(Integer.parseInt(year), Integer.parseInt(month)), language,user.getName());
    }
}
