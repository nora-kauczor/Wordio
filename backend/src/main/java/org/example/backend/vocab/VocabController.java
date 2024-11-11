package org.example.backend.vocab;

import lombok.RequiredArgsConstructor;
import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/vocab")
@RequiredArgsConstructor
public class VocabController {

    private final VocabService vocabService;

    @GetMapping
    public List<Vocab> getAllVocabsOfLanguageByUserName(@RequestParam String language, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return vocabService.getAllVocabsOfLanguageByUserName(language, user.getName());
    }

    @PostMapping
    public Vocab createVocab(@RequestBody VocabDTOCreate vocabDTO, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return vocabService.createVocab(vocabDTO, user.getName());
    }

    @PostMapping("/activate")
    public Vocab createAndActivateVocab(@RequestBody VocabDTOCreate vocabDTO, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return vocabService.createAndActivateVocab(vocabDTO, user.getName());
    }

    @PutMapping
    public Vocab editVocab(@RequestBody VocabDTOEdit editedVocab, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException, VocabIsNotEditableException, LanguageNotFoundException {
        return vocabService.editVocab(editedVocab, user.getName());
    }

    @PutMapping("/change-dates/{id}")
    public Vocab changeReviewDates(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException {
        return vocabService.changeReviewDates(id, user.getName());
    }

    @PutMapping("/deactivate/{id}")
    public Vocab deactivateVocab(@PathVariable String id, @AuthenticationPrincipal OAuth2User user ) throws IdNotFoundException {
        return vocabService.deactivateVocab(id, user.getName());
    }

    @PutMapping("/activate/{id}")
    public Vocab activateVocab(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException {
        return vocabService.activateVocab(id, user.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteVocab(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException, VocabIsNotEditableException {
        return vocabService.deleteVocab(id, user.getName());
    }

}
