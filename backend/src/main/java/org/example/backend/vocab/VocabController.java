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
    public List<Vocab> getAllVocabsOfLanguage(@RequestParam String language, @AuthenticationPrincipal OAuth2User user) throws LanguageNotFoundException {
        return vocabService.getAllVocabsOfLanguage(language, user.getName());
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

    @PutMapping("/change-dates/{_id}")
    public Vocab changeReviewDates(@PathVariable String _id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException {
        return vocabService.changeReviewDates(_id, user.getName());
    }

    @PutMapping("/deactivate/{_id}")
    public Vocab deactivateVocab(@PathVariable String _id, @AuthenticationPrincipal OAuth2User user ) throws IdNotFoundException {
        return vocabService.deactivateVocab(_id, user.getName());
    }

    @PutMapping("/activate/{_id}")
    public Vocab activateVocab(@PathVariable String _id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException {
        return vocabService.activateVocab(_id, user.getName());
    }

    @DeleteMapping("/{_id}")
    public String deleteVocab(@PathVariable String _id, @AuthenticationPrincipal OAuth2User user) throws IdNotFoundException, VocabIsNotEditableException {
        return vocabService.deleteVocab(_id, user.getName());
    }

}
