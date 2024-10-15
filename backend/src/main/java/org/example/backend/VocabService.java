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

    public String deleteVocab(String id){
       if ( !vocabRepo.existsById(id)) {throw new NoSuchElementException();
       }
        vocabRepo.deleteById(id);
        return "Vocab successfully deleted.";
    }
}
