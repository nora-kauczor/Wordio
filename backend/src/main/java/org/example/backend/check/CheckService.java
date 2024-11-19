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

//    public boolean isUserAnswerCorrect(String vocabId, String userAnswer) {
//        Optional<Vocab> vocabOptional = vocabRepo.findById(vocabId);
//        Vocab vocab = vocabOptional.orElseThrow();
//        String word = vocab.getWord();
//        List<String> correctAnswers = getCorrectAnswers(word);
//        List<String> correctAnswersLowerCase = correctAnswers.stream()
//                .map(String::toLowerCase)
//                .toList();
//        String inputWithoutExtraSpaces = getInputWithoutExtraSpaces(userAnswer);
//        return correctAnswersLowerCase.stream()
//                .anyMatch(answer -> answer.equals(inputWithoutExtraSpaces.toLowerCase()));
//    }



    public String getInputWithoutExtraSpaces(String userAnswer) {
        String trimmedInput = userAnswer.trim();
        StringBuilder result = new StringBuilder();

        // Variable to keep track of whether the previous character was a space
        boolean previousWasSpace = false;

        // Iterate through each character of the trimmed string
        for (int i = 0; i < trimmedInput.length(); i++) {
            char currentChar = trimmedInput.charAt(i);
            if (currentChar == ' ') {
                // If the previous character was a space, skip this one
                if (previousWasSpace) {
                    continue;
                }
                previousWasSpace = true;
            } else {
                previousWasSpace = false;
            }

            // Append current character to the result
            result.append(currentChar);
        }

        return result.toString();
    }
    }
