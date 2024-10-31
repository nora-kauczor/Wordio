package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/vocab")
@RequiredArgsConstructor
public class VocabController {

    private final VocabService vocabService;

    @PutMapping("/change-dates/{_id}")
    public Vocab changeReviewDates(@PathVariable String _id) throws IdNotFoundException {
        return vocabService.changeReviewDates(_id);
    }

    @PutMapping("/deactivate/{_id}")
    public Vocab deactivateVocab(@PathVariable String _id) throws IdNotFoundException {
        return vocabService.deactivateVocab(_id);
    }

    @PutMapping("/activate/{_id}")
    public Vocab activateVocab(@PathVariable String _id) throws IdNotFoundException {
       return vocabService.activateVocab(_id);
    }

    @GetMapping("/language")
    public List<Vocab> getAllVocabsOfLanguage(@RequestParam String language) throws LanguageNotFoundException {
        return vocabService.getAllVocabsOfLanguage(language);
    }

    @GetMapping
    public List<Vocab> getAllVocabs(){
        return vocabService.getAllVocabs();
    }

    @GetMapping("/{_id}")
    public Vocab getVocab(@PathVariable String _id) throws IdNotFoundException {
        return vocabService.getVocab(_id);
    }

    @PostMapping
    public Vocab createVocab(@RequestBody VocabDTOCreate vocabDTO) throws LanguageNotFoundException {
        return vocabService.createVocab(vocabDTO);
    }

    @PutMapping("/{_id}")
    public Vocab editVocab(@RequestBody VocabDTOEdit editedVocab, @PathVariable String _id) throws IdNotFoundException, VocabIsNotEditableException, LanguageNotFoundException {
        return vocabService.editVocab(editedVocab);
    }

    @DeleteMapping("/{_id}")
    public String deleteVocab(@PathVariable String _id) throws IdNotFoundException {
        return vocabService.deleteVocab(_id);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(VocabIsNotEditableException.class)
    public ErrorMessage handleVocabIsNotEditableException() {
        return new ErrorMessage("Method not allowed");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorMessage handleIdNotFoundException() {
        return new ErrorMessage("ID not found.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LanguageNotFoundException.class)
    public ErrorMessage handleLanguageNotFoundException() {
        return new ErrorMessage("Language not found.");
    }



}
