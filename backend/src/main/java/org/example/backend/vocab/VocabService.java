package org.example.backend.vocab;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.backend.vocab.Language.getEnumByString;
import static org.example.backend.vocab.Vocab.generateDates;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;

    public Vocab changeReviewDates(String id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        LocalDate firstDayOfOldReviewDates = vocab.getDatesPerUser().get(userName).getFirst();
        List<LocalDate> newDates = generateDates(firstDayOfOldReviewDates.plusDays(1));
        vocab.getDatesPerUser().put(userName, newDates);
        return vocabRepo.save(vocab);
    }

    public Vocab deactivateVocab(String id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        vocab.getDatesPerUser().put(userName, List.of());
        return vocabRepo.save(vocab);
    }

    public Vocab activateVocab(String id, String userName) throws IdNotFoundException {
        Vocab vocab = vocabRepo.findById(id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        LocalDate today = ZonedDateTime.now().toLocalDate();
        List<LocalDate> dates = generateDates(today);
        vocab.getDatesPerUser().put(userName, dates);
        return vocabRepo.save(vocab);
    }

    public List<Vocab> getAllVocabsOfLanguage(String languageString, String userName) throws LanguageNotFoundException {
        Language language = getEnumByString(languageString);
        return vocabRepo.findAll().stream()
                .filter(vocab -> vocab.getCreatedBy().equals("Wordio") || vocab.getCreatedBy().equals(userName))
                .filter(vocab -> vocab.getLanguage().equals(language))
                .toList();
    }

    public Vocab createVocab(VocabDTOCreate vocabDTO, String userName) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(vocabDTO.language());
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), language, new HashMap<>(), userName);
        return vocabRepo.save(newVocab);
    }

    public Vocab createAndActivateVocab(VocabDTOCreate vocabDTO, String userName) throws LanguageNotFoundException {
        Language language = Language.getEnumByString(vocabDTO.language());
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        LocalDate today = ZonedDateTime.now().toLocalDate();
        List<LocalDate> dates = generateDates(today);
        datesPerUser.put(userName, dates);
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), language, datesPerUser, userName);
        return vocabRepo.save(newVocab);
    }

    public Vocab editVocab(VocabDTOEdit vocabDTO, String userName) throws IdNotFoundException, VocabIsNotEditableException {
        Vocab vocab = vocabRepo.findById(vocabDTO.id()).orElseThrow(() -> new IdNotFoundException("ID not found."));
        if (!vocab.getCreatedBy().equals(userName)) {
            throw new VocabIsNotEditableException("Method not allowed.");
        }
        Vocab editedVocab = new Vocab(vocab.getId(), vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocab.getLanguage(), vocab.getDatesPerUser(), vocab.getCreatedBy());
        return vocabRepo.save(editedVocab);
    }

    public String deleteVocab(String id, String userName) throws IdNotFoundException, VocabIsNotEditableException {
        Vocab vocab = vocabRepo.findById(id).orElseThrow(() -> new IdNotFoundException("ID not found."));
        if (!vocab.getCreatedBy().equals(userName)) {
            throw new VocabIsNotEditableException("Method not allowed.");
        }
        vocabRepo.deleteById(id);
        return "Vocab successfully deleted.";
    }

}
