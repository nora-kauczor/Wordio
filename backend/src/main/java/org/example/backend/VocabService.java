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

    public Vocab getVocab(String id){
        return vocabRepo.findById(id).orElseThrow();
    }

    public void deleteVocab(String id){
        vocabRepo.deleteById(id);
    }
}
