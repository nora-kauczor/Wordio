package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class VocabService {
    private final VocabRepo vocabRepo;

    public List<Vocab> getAllVocabs() {
        return vocabRepo.findAll();
    }

    public Vocab getVocab(String id){
        return vocabRepo.findById(id).orElseThrow();
    }

    public Vocab createVocab(VocabDTO vocabDTO){
        Vocab newVocab = new Vocab(null, vocabDTO.word(), vocabDTO.translation(),
                vocabDTO.info(), vocabDTO.language(), vocabDTO.reviewDates());
        return vocabRepo.save(newVocab);
    }

    public Vocab editVocab(Vocab editedVocab){
        return vocabRepo.save(editedVocab);
    }


    public String deleteVocab(String id){
       if ( !vocabRepo.existsById(id)) {throw new NoSuchElementException();
       }
        vocabRepo.deleteById(id);
        return "Vocab successfully deleted.";
    }
}
