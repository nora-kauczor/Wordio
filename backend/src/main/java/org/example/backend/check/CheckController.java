package org.example.backend.check;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;


    @GetMapping("/{vocabId}")
    public boolean isUserAnswerCorrect(@PathVariable String vocabId, @RequestParam String answer)  {
        return checkService.isUserAnswerCorrect(vocabId, answer);
    }

}
