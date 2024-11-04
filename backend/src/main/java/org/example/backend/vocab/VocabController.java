package org.example.backend.vocab;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/vocab")
@RequiredArgsConstructor
public class VocabController {

    private final VocabService vocabService;

    @GetMapping
    public List<Vocab> getAllVocabsOfLanguage(@RequestParam String language) throws LanguageNotFoundException {
        return vocabService.getAllVocabsOfLanguage(language);
    }

    @PostMapping
    public Vocab createVocab(@RequestBody VocabDTOCreate vocabDTO) throws LanguageNotFoundException {
        return vocabService.createVocab(vocabDTO);
    }

    @PostMapping("/activate")
    public Vocab createAndActivateVocab(@RequestBody VocabDTOCreate vocabDTO) throws LanguageNotFoundException {
        return vocabService.createAndActivateVocab(vocabDTO);
    }

    @PutMapping
    public Vocab editVocab(@RequestBody VocabDTOEdit editedVocab) throws IdNotFoundException, VocabIsNotEditableException, LanguageNotFoundException {
        return vocabService.editVocab(editedVocab);
    }

    @PutMapping("/change-dates/{_id}")
    public Vocab changeReviewDates(@PathVariable String _id, @RequestParam String user) throws IdNotFoundException {
        return vocabService.changeReviewDates(_id, user);
    }

    @PutMapping("/deactivate/{_id}")
    public Vocab deactivateVocab(@PathVariable String _id, @RequestParam String user ) throws IdNotFoundException {
        return vocabService.deactivateVocab(_id, user);
    }

    @PutMapping("/activate/{_id}")
    public Vocab activateVocab(@PathVariable String _id, @RequestParam String user) throws IdNotFoundException {
        return vocabService.activateVocab(_id, user);
    }

    @DeleteMapping("/{_id}")
    public String deleteVocab(@PathVariable String _id) throws IdNotFoundException {
        return vocabService.deleteVocab(_id);
    }

}
