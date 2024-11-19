package org.example.backend.check;

import lombok.RequiredArgsConstructor;
import org.example.backend.vocab.Language;
import org.example.backend.vocab.Vocab;
import org.example.backend.vocab.VocabRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static org.example.backend.check.CorrectAnswers.getCorrectAnswers;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final VocabRepo vocabRepo;

    public boolean isUserAnswerCorrect(String vocabId, String userAnswer) {
        Optional<Vocab> vocabOptional = vocabRepo.findById(vocabId);
        Vocab vocab = vocabOptional.orElseThrow();
        String word = vocab.getWord();
        Language language = vocab.getLanguage();
        List<String> correctAnswers = getCorrectAnswers(word, language);
        List<String> correctAnswersLowerCase = correctAnswers.stream()
                .map(String::toLowerCase)
                .toList();
        String inputWithoutExtraSpaces = getInputWithoutExtraSpaces(userAnswer);
        return correctAnswersLowerCase.stream()
                .anyMatch(answer -> answer.equals(inputWithoutExtraSpaces.toLowerCase()));
    }


    public String getInputWithoutExtraSpaces(String userAnswer) {
        String trimmedInput = userAnswer.trim();
        StringBuilder result = new StringBuilder();
        boolean previousWasSpace = false;
        for (int i = 0; i < trimmedInput.length(); i++) {
            char currentChar = trimmedInput.charAt(i);
            if (currentChar == ' ') {
                if (previousWasSpace) {
                    continue;
                }
                previousWasSpace = true;
            } else {
                previousWasSpace = false;
            }

            result.append(currentChar);
        }
        return result.toString();
    }
}
