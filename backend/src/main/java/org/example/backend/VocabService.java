package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;

    public List<Vocab> getAllVocabs() {
        return vocabRepo.findAll();
    }

    public Vocab createVocab(VocabDTO vocabDTO){
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocabDTO.language(), vocabDTO.reviewDates());
        return vocabRepo.save(newVocab);
    }

    public Vocab getVocab(String id){
        return vocabRepo.findById(id).orElseThrow();
    }
}
