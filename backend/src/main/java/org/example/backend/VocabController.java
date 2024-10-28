package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/vocab")
@RequiredArgsConstructor
public class VocabController {

    private final VocabService vocabService;

    @PutMapping("/change-dates/{_id}")
    public Vocab changeReviewDates(@PathVariable String _id) {
        return vocabService.changeReviewDates(_id);
    }

    @PutMapping("/deactivate/{_id}")
    public Vocab deactivateVocab(@PathVariable String _id){
        return vocabService.deactivateVocab(_id);
    }

    @PutMapping("/activate/{_id}")
    public Vocab activateVocab(@PathVariable String _id){
       return vocabService.activateVocab(_id);
    }

    @GetMapping("/language")
    public List<Vocab> getAllVocabsOfLanguage(@RequestParam String language){
        return vocabService.getAllVocabsOfLanguage(language);
    }

    @GetMapping
    public List<Vocab> getAllVocabs(){
        return vocabService.getAllVocabs();
    }

    @GetMapping("/{_id}")
    public Vocab getVocab(@PathVariable String _id){
        return vocabService.getVocab(_id);
    }

    @PostMapping
    public Vocab createVocab(@RequestBody VocabDTO vocabDTO){
        return vocabService.createVocab(vocabDTO);
    }

    @PutMapping("/{_id}")
    public Vocab editVocab(@RequestBody Vocab editedVocab, @PathVariable String _id) {
        return vocabService.editVocab(editedVocab);
    }

    @DeleteMapping("/{_id}")
    public String deleteVocab(@PathVariable String _id) {
        return vocabService.deleteVocab(_id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleElementNotFoundException() {
        return new ErrorMessage("No matches for given ID.");
    }

}
