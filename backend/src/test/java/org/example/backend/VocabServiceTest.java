package org.example.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VocabServiceTest {
    private final VocabRepo mockVocabRepo = mock(VocabRepo.class);
    private final VocabService vocabService = new VocabService(mockVocabRepo);

    @Test
    void getAllVocabs_ShouldReturnAllVocabs_whenCalled() {
        Vocab testVocab = new Vocab("000", "la prueba", "test",
                "", "Spanish", List.of());
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
                "", "Spanish", List.of());
        when(mockVocabRepo.findById("000")).thenReturn(Optional.of(testVocab));
        Optional<Vocab> expected = Optional.of(testVocab);
        Optional<Vocab> actual = mockVocabRepo.findById("000");
        assertEquals(expected, actual);
        verify(mockVocabRepo).findById("000");
    }

    @Test
    void getVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId() {
        when(mockVocabRepo.findById("000")).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> mockVocabRepo.findById("000"));
        verify(mockVocabRepo).findById("000");
    }


    @Test
    void deleteVocab_shouldTriggerDeletionOfVocab_whenCalledWithId(){
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        vocabService.deleteVocab("000");
        verify(mockVocabRepo).deleteById("000");
    }

    @Test
    void deleteVocab_shouldReturnString_whenCalledWithId(){
        when(mockVocabRepo.existsById("000")).thenReturn(true);
        String expected = "Vocab successfully deleted.";
        String actual = vocabService.deleteVocab("000");
        assertEquals(expected, actual);
    }

    @Test
    void deleteVocab_shouldThrowNoSuchElementException_whenCalledWithNonexistentId(){
        assertThrows(NoSuchElementException.class, () -> vocabService.deleteVocab("000"));
       verify(mockVocabRepo).existsById("000");
    }


}