package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


import static org.example.backend.Language.getEnumByString;
import static org.example.backend.Vocab.generateDates;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;


    public Vocab changeReviewDates(String _id) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        LocalDate firstDayOfOldReviewDates = vocab.reviewDates.getFirst();
        List<LocalDate> newDates = generateDates(firstDayOfOldReviewDates.plusDays(1));
        vocab.setReviewDates(newDates);
        vocabRepo.save(vocab);
        return vocabRepo.findById(_id).orElseThrow();
    }

    public Vocab deactivateVocab(String _id) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        vocab.setReviewDates(List.of());
        return vocabRepo.save(vocab);
    }

    public Vocab activateVocab(String _id) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        List<LocalDate> dates = generateDates(LocalDate.now());
        vocab.setReviewDates(dates);
        return vocabRepo.save(vocab);
    }

    public List<Vocab> getAllVocabsOfLanguage(String languageString) throws LanguageNotFoundException {
        Language language = getEnumByString(languageString);
        return vocabRepo.findAll().stream()
                .filter(vocab -> vocab.getLanguage().equals(language))
                .toList();
    }


    public List<Vocab> getAllVocabs() {
        return vocabRepo.findAll();
    }

    public Vocab getVocab(String id) throws IdNotFoundException {
        return vocabRepo.findById(id).orElseThrow(() -> new IdNotFoundException("ID not found."));
    }

    public Vocab createVocab(VocabDTO vocabDTO) {
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocabDTO.language(), List.of(), true);
        return vocabRepo.save(newVocab);
    }

    public Vocab editVocab(Vocab editedVocab) throws IdNotFoundException {
        if (!vocabRepo.existsById(editedVocab._id)) {
            throw new IdNotFoundException("ID not found.");
        }
        if (!editedVocab.editable){
            throw new
        }
        return vocabRepo.save(editedVocab);
    }

    public String deleteVocab(String _id) throws IdNotFoundException {
        if (!vocabRepo.existsById(_id)) {
            throw new IdNotFoundException("ID not found.");
        }
        vocabRepo.deleteById(_id);
        return "Vocab successfully deleted.";
    }

}
