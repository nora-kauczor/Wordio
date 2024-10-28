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
    void changeReviewDates_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        when(mockVocabRepo.findById("000")).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> vocabService.changeReviewDates("000"));
    }

    @Test
    void changeReviewDates_shouldReturnVocabWithChangedDates_whenCalledWithId(){
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of(LocalDate.of(2024,11, 1)));
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.changeReviewDates("000");
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void deactivateVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        when(mockVocabRepo.findById("000")).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> vocabService.deactivateVocab("000"));
    }

    @Test
    void deactivateVocab_shouldReturnVocabWithEmptiedReviewDates_whenCalledWithExistentId(){
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of(LocalDate.of(2024,11,15)));
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.deactivateVocab("000");
        assertTrue(testVocab.reviewDates.isEmpty());
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void activateVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        when(mockVocabRepo.findById("000")).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> vocabService.activateVocab("000"));
    }

    @Test
    void activateVocab_shouldReturnVocabWithReviewDates_whenCalledWithExistentId() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, Collections.emptyList());
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        vocabService.activateVocab("000");
        assertFalse(testVocab.reviewDates.isEmpty());
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getAllVocabsOfLanguage_shouldThrowNoSuchElementException_whenCalledWithNonexistentLanguage() {
        assertThrows(NoSuchElementException.class, () -> vocabService.getAllVocabsOfLanguage("Esperanto"));
    }


    @Test
    void getAllVocabsOfLanguage_shouldReturnAllVocabsOfLanguage_whenCalledWithExistentLanguage() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of());
        Vocab testVocab2 = new Vocab("111", "la prueba", "test",
                "", Language.SPANISH, List.of());
        Vocab testVocab3 = new Vocab("222", "la prueba", "test",
                "", Language.ITALIAN, List.of());
        when(mockVocabRepo.findAll()).thenReturn(List.of(testVocab,testVocab2,testVocab3));
        List<Vocab> expected = List.of(testVocab, testVocab2);
        List<Vocab> actual = vocabService.getAllVocabsOfLanguage("Spanish");
        assertEquals(expected, actual);
    }


    @Test
    void getAllVocabs_ShouldReturnAllVocabs_whenCalled() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", Language.SPANISH, List.of());
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
                "", Language.SPANISH, List.of());
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        Optional<Vocab> expected = Optional.of(testVocab);
        Optional<Vocab> actual = mockVocabRepo.findById("000");
        assertEquals(expected, actual);
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        when(mockVocabRepo.findById("000")).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> vocabService.getVocab("000"));
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void createVocab_shouldReturnNewVocabObject_whenCalledWithVocabDTO() {
        VocabDTO testDTO = new VocabDTO("la prueba", "test",
                "", Language.SPANISH);
        Vocab testVocab = new Vocab("Id generated by MongoDB", "la prueba", "test",
                "", Language.SPANISH, List.of());
        when(mockVocabRepo.save(any(Vocab.class))).thenReturn(testVocab);
        Vocab actual = vocabService.createVocab(testDTO);
        Vocab expected = testVocab;
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(any(Vocab.class));
    }

    @Test
    void editVocab_shouldReturnEditedVocab_whenCalledWithThisVeryVocab() {
        Vocab editedVocab = new Vocab("000", "la prueba", "test",
                "added infotext", Language.SPANISH, List.of());
        when(mockVocabRepo.save(editedVocab)).thenReturn(editedVocab);
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        Vocab expected = editedVocab;
        Vocab actual = vocabService.editVocab(expected);
        assertEquals(expected, actual);
        verify(mockVocabRepo).save(editedVocab);
    }

    @Test
    void editVocab_shouldThrowNoSuchElementException_whenCalledWithVocabWithNonexistentId() {
        Vocab testVocab = new Vocab("ID that is not in the DB", "la prueba", "test",
                "", Language.SPANISH, List.of());
        assertThrows(NoSuchElementException.class, () -> vocabService.editVocab(testVocab));
        verify(mockVocabRepo).existsById("ID that is not in the DB");
    }

    @Test
    void deleteVocab_shouldTriggerDeletionOfVocab_whenCalledWithId() {
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        vocabService.deleteVocab("000");
        verify(mockVocabRepo).deleteById("000");
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId() {
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        String expected = "Vocab successfully deleted.";
        String actual = vocabService.deleteVocab("000");
        assertEquals(expected, actual);
    }

    @Test
    void deleteVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        assertThrows(NoSuchElementException.class, () -> vocabService.deleteVocab("000"));
        verify(mockVocabRepo).existsById("000");
    }


}