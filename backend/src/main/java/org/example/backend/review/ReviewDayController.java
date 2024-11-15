package org.example.backend.review;

import lombok.RequiredArgsConstructor;

import org.example.backend.exception.LanguageNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewDayController {

    private final ReviewDayService reviewDayService;

    @GetMapping
    public ReviewDay getReviewDay(@RequestParam String language, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return reviewDayService.getReviewDay(language, user.getName(), ZonedDateTime.now().toLocalDate());
    }

    @PutMapping("/{vocabId}")
    public ReviewDay setVocabReviewed(@PathVariable String vocabId, @RequestParam String language, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return reviewDayService.setVocabReviewed(vocabId, language, user.getName(), ZonedDateTime.now().toLocalDate());
    }

}
