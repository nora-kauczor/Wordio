package org.example.backend.check;

import lombok.RequiredArgsConstructor;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final VocabRepo vocabRepo;

    public boolean isUserAnswerCorrect(String vocabId, String userAnswer) {
        Optional<Vocab> vocabOptional = vocabRepo.findById(vocabId);
        Vocab vocab = vocabOptional.orElseThrow();
        String word = vocab.getWord();
        List<String> correctAnswers = getCorrectAnswers(word);
    }

    public List<String> getCorrectAnswers(String word) {

    }
}
