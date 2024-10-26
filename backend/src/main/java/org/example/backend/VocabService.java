package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.backend.Language.getEnumByString;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;

    public Vocab activateVocab(String _id) {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow();
        vocab.reviewDates = vocab.generateDates(LocalDate.now());
        return vocabRepo.save(vocab);
    }

    public List<Vocab> getAllVocabsOfLanguage(String languageString) {
        Language language = getEnumByString(languageString);
        System.out.println("language :"+language);
        List<Vocab> vocabsOfLanguage = vocabRepo.findAll().stream()
                .filter(vocab -> vocab.language.equals(language))
                .toList();
        System.out.println("vocabsOfLanguage :"+vocabsOfLanguage);
        if (vocabsOfLanguage.isEmpty()) {
            throw new NoSuchElementException();
        }
        return vocabsOfLanguage;
    }

    public List<Vocab> getAllVocabs() {
        return vocabRepo.findAll();
    }

    public Vocab getVocab(String id) {
        return vocabRepo.findById(id).orElseThrow();
    }

    public Vocab createVocab(VocabDTO vocabDTO) {
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocabDTO.language(), List.of());
        return vocabRepo.save(newVocab);
    }

    public Vocab editVocab(Vocab editedVocab) {
        if (!vocabRepo.existsById(editedVocab._id)) {
            throw new NoSuchElementException();
        }
        return vocabRepo.save(editedVocab);
    }

    public String deleteVocab(String _id) {
        if (!vocabRepo.existsById(_id)) {
            throw new NoSuchElementException();
        }
        vocabRepo.deleteById(_id);
        return "Vocab successfully deleted.";
    }

}
