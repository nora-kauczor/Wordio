package org.example.backend.vocab;

import org.example.backend.exception.LanguageNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.backend.vocab.Language.getEnumByString;

@Repository
public interface VocabRepo extends MongoRepository<Vocab, String> {
    List<Vocab> getByLanguage(Language language);

//    public static List<Vocab> getAllNEWTEST(String languageString, String userId) throws LanguageNotFoundException {
//        Language language = getEnumByString(languageString);
//        return vocabRepo.findAll().stream()
//                .filter(vocab -> vocab.getCreatedBy().equals("Wordio") || vocab.getCreatedBy().equals(userId))
//                .filter(vocab -> vocab.getLanguage().equals(language))
//                .toList();
//    }
}
