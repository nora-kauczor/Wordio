package org.example.backend.review;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewDayController {

    private final ReviewDayService vocabsOfDayService;

    @GetMapping
    public ReviewDay getReviewDay(@RequestParam String language, @AuthenticationPrincipal OAuth2User user)  {
        return vocabsOfDayService.getReviewDay(language, user.getName(), ZonedDateTime.now().toLocalDate());
    }

    @PutMapping("/{vocabId}")
    public ReviewDay setVocabReviewed(@PathVariable String vocabId, @AuthenticationPrincipal OAuth2User user)  {
        return vocabsOfDayService.setVocabReviewed(vocabId, user.getName(), ZonedDateTime.now().toLocalDate());
    }
}
