package org.example.backend.check;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.vocab.Vocab;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;


    @GetMapping("/{vocabId}")
    public boolean isUserAnswerCorrect(@PathVariable String vocabId, @RequestParam String userAnswer) throws LanguageNotFoundException {
        return checkService.isUserAnswerCorrect(vocabId, userAnswer);
    }


}
