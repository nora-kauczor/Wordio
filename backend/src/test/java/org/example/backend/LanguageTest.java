package org.example.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void getEnumByString_shouldThrowNoSuchElementException_whenCalledWithNonexistentLanguage() {
        assertThrows(NoSuchElementException.class, () -> Language.getEnumByString("Esperanto"));
    }
    @Test
    void getEnumByString_shouldReturnSPANISHEnum_whenCalledWithStringSpanish() {
        Language expected = Language.SPANISH;
        Language actual= Language.getEnumByString("Spanish");
                assertEquals(expected, actual);
    }
}