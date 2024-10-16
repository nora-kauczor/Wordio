package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;

    public Vocab activateVocab(String _id) {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow();
        List<LocalDate> dates = vocab.generateDates(LocalDate.now());
        vocab.reviewDates.retainAll(dates);
        return vocab;
    }

    public List<Vocab> getTodaysVocabs() throws NoVocabsForTodayException {
        LocalDate today = LocalDate.now();
        List<Vocab> todaysVocabs = vocabRepo.findAll().stream()
                .filter(vocab -> vocab.reviewDates != null && vocab.reviewDates.contains(today))
                .toList();
        if (todaysVocabs.isEmpty()) {
            throw new NoVocabsForTodayException();
        }
        return todaysVocabs;
    }

    public List<Vocab> getAllVocabs() {
        return vocabRepo.findAll();
    }

    public Vocab getVocab(String id) {
        return vocabRepo.findById(id).orElseThrow();
    }

    public Vocab createVocab(VocabDTO vocabDTO) {
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocabDTO.language(), vocabDTO.reviewDates());
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
