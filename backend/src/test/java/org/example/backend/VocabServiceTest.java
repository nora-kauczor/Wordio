package org.example.backend;

import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertEquals(testListOfVocabs, vocabService.getAllVocabs());
        verify(mockVocabRepo).findAll();
    }
}