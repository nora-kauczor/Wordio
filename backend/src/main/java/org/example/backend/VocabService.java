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


    public Vocab changeReviewDates(String _id){
        Vocab vocab = vocabRepo.findById(_id).orElseThrow();
        LocalDate firstDayOfOldReviewDates = vocab.reviewDates.getFirst();
        vocab.reviewDates = vocab.generateDates(firstDayOfOldReviewDates.plusDays(1));
        return vocabRepo.save(vocab);
    }

    public Vocab deactivateVocab(String _id) {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow();
        vocab.reviewDates = List.of();
        return vocabRepo.save(vocab);
    }

    public Vocab activateVocab(String _id) {
        Vocab vocab = vocabRepo.findById(_id).orElseThrow();
        vocab.reviewDates = vocab.generateDates(LocalDate.now());
        return vocabRepo.save(vocab);
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
