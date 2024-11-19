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
        datesPerUser.put("jane-doe", List.of(LocalDate.of(2024, 11, 1)));
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        Map<String, List<LocalDate>> otherDatesPerUser = new HashMap<>();
        otherDatesPerUser.put("jane-doe", List.of(LocalDate.of(2024, 11, 2)));
        Vocab changedVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, otherDatesPerUser, "Wordio");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(changedVocab);
        LocalDate expected = LocalDate.of(2024, 11, 2);
        LocalDate actual = vocabService.changeReviewDates("000", "jane-doe").getDatesPerUser().get("jane-doe").getFirst();
        assertEquals(expected, actual);
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void deactivateVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.deactivateVocab("000", "jane-doe"));
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithEmptiedReviewDates_whenCalledWithExistentId() throws IdNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of(LocalDate.of(2024, 11, 15)));
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
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "Wordio");
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab));
        assertThrows(LanguageNotFoundException.class, () -> vocabService
                .getAllVocabsOfLanguage("Esperanto", "jane-doe"));
    }

    @Test
    void getAllVocabsOfLanguage_shouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() throws LanguageNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "Wordio");
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "jane-doe");
        Vocab vocabDifferentLanguage = new Vocab("222", "la prueba", "test",
                "", Language.ITALIAN, datesPerUser, "Wordio");
        Vocab vocabByOtherUser = new Vocab("222", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "average-joe");
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab, testVocab2, vocabDifferentLanguage, vocabByOtherUser));
        List<Vocab> expected = List.of(testVocab, testVocab2);
        List<Vocab> actual = vocabService.getAllVocabsOfLanguage("Spanish", "jane-doe");
        assertEquals(expected, actual);
    }

    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() throws LanguageNotFoundException {
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("jane-doe", List.of());
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Spanish");
        Vocab testVocab = new Vocab("Id generated by MongoDB", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "jane-doe");
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(testVocab);
        assertEquals(testVocab, vocabService.createVocab(testDTO, "jane-doe"));
        verify(mockVocabRepo).save(any(Vocab.class));
    }

    @Test
    void createVocab_throwsLanguageNotFoundException_whenCalledWithVocabWithNonExistentLanguage() {
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Esperanto");
        assertThrows(LanguageNotFoundException.class, () -> vocabService.createVocab(testDTO, "jane-doe"));
    }

    @Test
    void createAndActivateVocab_shouldReturnNewVocabObjectWithReviewDates_whenCalledWithVocabDTO() throws LanguageNotFoundException {
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Spanish");
        Map<String, List<LocalDate>> datesPerUser = new HashMap<>();
        datesPerUser.put("maxi-muster", List.of(LocalDate.of(2024, 11, 1)));
        Vocab expected = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, datesPerUser, "jane-doe");
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(expected);
        Vocab actual = vocabService.createAndActivateVocab(testDTO, "jane-doe");
        assertFalse(actual.getDatesPerUser().get("maxi-muster").isEmpty());
    }

    @Test
    void createAndActivateVocab_throwsLanguageNotFoundException_whenCalledWithVocabWithNonExistentLanguage() {
        VocabDTOCreate testDTO = new VocabDTOCreate("la prueba", "test",
                "", "Esperanto");
        assertThrows(LanguageNotFoundException.class, () -> vocabService.createAndActivateVocab(testDTO, "jane-doe"));
    }


    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithVocabDTOEdit() throws IdNotFoundException, VocabIsNotEditableException {
        Vocab oldVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "jane-doe");
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish");
        Vocab expected = new Vocab("000", "la prueba", "test",
                "added infotext", Language.SPANISH, new HashMap<>(), "jane-doe");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(oldVocab));
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(expected);
        Vocab actual = vocabService.editVocab(vocabDTO, "jane-doe");
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(expected);
    }

    @Test
    void editVocab_shouldThrowIdNotFoundException_whenCalledWithVocabWithNonexistentId() {
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish");
        assertThrows(IdNotFoundException.class, () -> vocabService.editVocab(vocabDTO, "jane-doe"));
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void editVocab_shouldThrowVocabIsNotEditableException_whenCalledWithNonEditableVocab() {
        Vocab oldVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "Wordio");
        VocabDTOEdit vocabDTO = new VocabDTOEdit("000", "la prueba", "test",
                "added infotext", "Spanish");
        when(mockVocabRepo.findById(vocabDTO.id())).thenReturn(Optional.of(oldVocab));
        assertThrows(VocabIsNotEditableException.class, () -> vocabService.editVocab(vocabDTO, "jane-doe"));
    }


    @Test
    void deleteVocab_shouldTriggerDeletionOfVocab_whenCalledWithId() throws IdNotFoundException, VocabIsNotEditableException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "jane-doe");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.deleteVocab("000", "jane-doe");
        verify(mockVocabRepo).deleteById("000");
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId() throws IdNotFoundException, VocabIsNotEditableException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "jane-doe");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        String expected = "Vocab successfully deleted.";
        String actual = vocabService.deleteVocab("000", "jane-doe");
        assertEquals(expected, actual);
    }

    @Test
    void deleteVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.deleteVocab("000", "jane-doe"));
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void deleteVocab_shouldThrowVocabIsNotEditableException_whenCalledByUserWhoDidntCreateIt() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, new HashMap<>(), "Wordio");
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        assertThrows(VocabIsNotEditableException.class, () -> vocabService.deleteVocab("000", "jane-doe"));
        verify(mockVocabRepo).findById("000");
    }


}