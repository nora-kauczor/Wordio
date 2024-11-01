package org.example.backend.vocab;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.example.backend.vocab.Language.getEnumByString;
import static org.example.backend.vocab.Vocab.generateDates;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;


    public Vocab changeReviewDates(String _id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        LocalDate firstDayOfOldReviewDates = vocab.getDatesPerUser().get(userName).getFirst();
        List<LocalDate> newDates = generateDates(firstDayOfOldReviewDates.plusDays(1));
        vocab.getDatesPerUser().put(userName, newDates);
        vocabRepo.save(vocab);
        return vocabRepo.findById(_id).orElseThrow();
    }

    public Vocab deactivateVocab(String _id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        vocab.getDatesPerUser().put(userName, List.of()); // TODO: immutable? besser arraylist.. ??
        return vocabRepo.save(vocab);
    }

    public Vocab activateVocab(String _id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        List<LocalDate> dates = generateDates(LocalDate.now());
        vocab.getDatesPerUser().put(userName, dates);
        return vocabRepo.save(vocab);
    }

    public List<Vocab> getAllVocabsOfLanguage(String languageString) throws LanguageNotFoundException {
        Language language = getEnumByString(languageString);
        return vocabRepo.findAll().stream()
                .filter(vocab -> vocab.getLanguage().equals(language))
                .toList();
    }

    public Vocab createVocab(VocabDTOCreate vocabDTO) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(vocabDTO.language());
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put(vocabDTO.createdBy(), List.of());
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), language, datesPerUser, vocabDTO.createdBy());
        return vocabRepo.save(newVocab);
    }

    public Vocab editVocab(VocabDTOEdit vocabDTO) throws IdNotFoundException, VocabIsNotEditableException, LanguageNotFoundException {
        if (!vocabRepo.existsById(vocabDTO._id())) {
            throw new IdNotFoundException("ID not found.");
        }
        if (vocabDTO.createdBy().equals("Wordio")) {
            throw new VocabIsNotEditableException("Method not allowed.");
        }
        Language language = Language.getEnumByString(vocabDTO.language());
        Vocab editedVocab = new Vocab(vocabDTO._id(), vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), language, vocabDTO.datesPerUser(), vocabDTO.createdBy());
        vocabRepo.save(editedVocab);
        return vocabRepo.findById(vocabDTO._id()).orElseThrow(() -> new IdNotFoundException("ID not found."));
    }

    public String deleteVocab(String _id) throws IdNotFoundException {
        if (!vocabRepo.existsById(_id)) {
            throw new IdNotFoundException("ID not found.");
        }
        vocabRepo.deleteById(_id);
        return "Vocab successfully deleted.";
    }

}
