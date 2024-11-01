package org.example.backend.vocab;

import org.example.backend.exception.IdNotFoundException;
import org.example.backend.exception.LanguageNotFoundException;
import org.example.backend.exception.VocabIsNotEditableException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VocabServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final VocabService vocabService = new VocabService(mockVocabRepo);

    @Test
    void changeReviewDates_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.changeReviewDates("000", "jane-doe"));
    }

    @Test
    void changeReviewDates_shouldReturnVocabWithChangedDates_whenCalledWithId() throws IdNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of(LocalDate.of(2024,11, 1)));
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        LocalDate expected = LocalDate.of(2024,11, 2);
        LocalDate actual = vocabService.changeReviewDates("000", "jane-doe").getDatesPerUser().get("jane-doe").getFirst();
        assertEquals(expected, actual);
        verify(mockVocabRepo, times(2)).findById("000");
    }


    @Test
    void deactivateVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.deactivateVocab("000", "jane-doe"));
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithEmptiedReviewDates_whenCalledWithExistentId() throws IdNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of(LocalDate.of(2024,11, 15)));
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.deactivateVocab("000", "jane-doe");
        assertTrue(testVocab.getDatesPerUser().get("jane-doe").isEmpty());
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void activateVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.activateVocab("000", "jane-doe"));
    }

    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws IdNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.activateVocab("000", "jane-doe");
        assertFalse(testVocab.getDatesPerUser().get("jane-doe").isEmpty());
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getAllVocabsOfLanguage_shouldThrowLanguageNotFoundException_whenCalledWithNonexistentLanguage() {
        assertThrows(LanguageNotFoundException.class, () -> vocabService.getAllVocabsOfLanguage("Esperanto"));
    }


    @Test
    void getAllVocabsOfLanguage_shouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() throws LanguageNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.ITALIAN, datesPerUser, "Wordio");
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab,testVocab2,testVocab3));
        List<Vocab> expected = List.of(testVocab, testVocab2);
        List<Vocab> actual = vocabService.getAllVocabsOfLanguage("Spanish");
        assertEquals(expected, actual);
    }

    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() throws LanguageNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Spanish", "maxi-muster");
        Vocab testVocab = new Vocab("Id generated by MongoDB", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "maxi-muster");
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(testVocab);
        assertEquals(testVocab, vocabService.createVocab(testDTO));
        verify(mockVocabRepo).save(any(Vocab.class));
    }

    @Test
    void createVocab_throwsLanguageNotFoundException_whenCalledWithVocabWithNonExistentLanguage(){
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Esperanto", "maxi-muster");
        assertThrows(LanguageNotFoundException.class, () -> vocabService.createVocab(testDTO));
    }

    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithVocabDTOEdit() throws IdNotFoundException, VocabIsNotEditableException, LanguageNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish", datesPerUser, "maxi-muster");
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        Vocab expected = new Vocab("000", "la prueba", "test",
                "added infotext", Language.SPANISH, datesPerUser, "maxi-muster");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(expected));
        Vocab actual = vocabService.editVocab(vocabDTO);
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(expected);
    }

    @Test
    void editVocab_shouldThrowIdNotFoundException_whenCalledWithVocabWithNonexistentId() {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish", datesPerUser, "maxi-muster");
        assertThrows(IdNotFoundException.class, () -> vocabService.editVocab(vocabDTO));
        verify(mockVocabRepo).existsById("000");
    }

    @Test
    void editVocab_shouldThrowVocabIsNotEditableException_whenCalledWithNonEditableVocab() {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish", datesPerUser, "Wordio");
        when(mockVocabRepo.existsById(vocabDTO._id())).thenReturn(true);
        assertThrows(VocabIsNotEditableException.class, () -> vocabService.editVocab(vocabDTO));
    }

    @Test
    void editVocab_throwsLanguageNotFoundException_whenCalledWithVocabWithNonExistentLanguage(){
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOEdit testDTO = new VocabDTOEdit("000", "la prueba", "test",
                "", "Esperanto", datesPerUser, "maxi-muster");
        when(mockVocabRepo.existsById(testDTO._id())).thenReturn(true);
        assertThrows(LanguageNotFoundException.class, () -> vocabService.editVocab(testDTO));
    }

    @Test
    void deleteVocab_shouldTriggerDeletionOfVocab_whenCalledWithId() throws IdNotFoundException {
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        vocabService.deleteVocab("000");
        verify(mockVocabRepo).deleteById("000");
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId() throws IdNotFoundException {
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        String expected = "Vocab successfully deleted.";
        String actual = vocabService.deleteVocab("000");
        assertEquals(expected, actual);
    }

    @Test
    void deleteVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.deleteVocab("000"));
        verify(mockVocabRepo).existsById("000");
    }


}