package org.example.backend.review;

import lombok.RequiredArgsConstructor;

import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Vocab;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

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
    public ReviewDay setVocabReviewed(@PathVariable String vocabId, @AuthenticationPrincipal OAuth2User user)  {
        return reviewDayService.setVocabReviewed(vocabId, user.getAttribute("sub"), ZonedDateTime.now().toLocalDate());
    }
}
