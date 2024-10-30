package org.example.backend;

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
        assertThrows(IdNotFoundException.class, () -> vocabService.changeReviewDates("000"));
    }

    @Test
    void changeReviewDates_shouldReturnVocabWithChangedDates_whenCalledWithId() throws IdNotFoundException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(LocalDate.of(2024,11, 1)), false);
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        LocalDate expected = LocalDate.of(2024,11, 2);
        LocalDate actual = vocabService.changeReviewDates("000").reviewDates.getFirst();
        assertEquals(expected, actual);
        verify(mockVocabRepo, times(2)).findById("000");
    }


    @Test
    void deactivateVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.deactivateVocab("000"));
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithEmptiedReviewDates_whenCalledWithExistentId() throws IdNotFoundException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(LocalDate.of(2024,11,15)), false);
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.deactivateVocab("000");
        assertTrue(testVocab.reviewDates.isEmpty());
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void activateVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.activateVocab("000"));
    }

    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() throws IdNotFoundException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, Collections.emptyList(), false);
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.activateVocab("000");
        assertFalse(testVocab.reviewDates.isEmpty());
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getAllVocabsOfLanguage_shouldThrowLanguageNotFoundException_whenCalledWithNonexistentLanguage() {
        assertThrows(LanguageNotFoundException.class, () -> vocabService.getAllVocabsOfLanguage("Esperanto"));
    }


    @Test
    void getAllVocabsOfLanguage_shouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() throws LanguageNotFoundException {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(), false);
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, List.of(), false);
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.ITALIAN, List.of(), false);
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab,testVocab2,testVocab3));
        List<Vocab> expected = List.of(testVocab, testVocab2);
        List<Vocab> actual = vocabService.getAllVocabsOfLanguage("Spanish");
        assertEquals(expected, actual);
    }


    @Test
    void getAllVocabs_ShouldReturnAllVocabs_whenCalled() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(), false);
        List<Vocab> testListOfVocabs = List.of(testVocab);
        when(mockVocabRepo.findAll()).thenReturn(testListOfVocabs);
        List<Vocab> expected = testListOfVocabs;
        List<Vocab> actual = vocabService.getAllVocabs();
        assertEquals(expected, actual);
        verify(mockVocabRepo).findAll();
    }

    @Test
    void getVocab_shouldReturnSpecificVocab_whenCalledWithItsId() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of(), false);
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        Optional<Vocab> expected = Optional.of(testVocab);
        Optional<Vocab> actual = mockVocabRepo.findById("000");
        assertEquals(expected, actual);
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getVocab_shouldThrowIdNotFoundException_whenCalledWithNonexistentId() {
        assertThrows(IdNotFoundException.class, () -> vocabService.getVocab("000"));
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() {
        VocabDTO testDTO = new VocabDTO("la prueba", "test",
                "", Language.SPANISH);
        Vocab testVocab = new Vocab("Id generated by MongoDB", "la prueba", "test",
                "", Language.SPANISH, List.of(), true);
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(testVocab);
        Vocab actual = vocabService.createVocab(testDTO);
        Vocab expected = testVocab;
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(any(Vocab.class));
    }

    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithThisVeryVocab() throws IdNotFoundException {
        Vocab editedVocab = new Vocab("000", "la prueba", "test",
                "added infotext", Language.SPANISH, List.of(), true);
        when(mockVocabRepo.save(editedVocab)).thenReturn(editedVocab);
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        Vocab expected = editedVocab;
        Vocab actual = vocabService.editVocab(expected);
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(editedVocab);
    }

    @Test
    void editVocab_shouldThrowIdNotFoundException_whenCalledWithVocabWithNonexistentId() {
        Vocab testVocab = new Vocab("ID that is not in the DB", "la prueba", "test",
                "", Language.SPANISH, List.of(), false);
        assertThrows(IdNotFoundException.class, () -> vocabService.editVocab(testVocab));
        verify(mockVocabRepo).existsById("ID that is not in the DB");
    }

//    @Test
//    void editVocab_shouldThrow_whenCalledWithNonEditableVocab() {
//        Vocab testVocab = new Vocab("ID of non editable vocab", "la prueba", "test",
//                "", Language.SPANISH, List.of(), false);
//        assertThrows(.class, () -> vocabService.editVocab(testVocab));
//        verify(mockVocabRepo).existsById("ID of non editable vocab");
//    }

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