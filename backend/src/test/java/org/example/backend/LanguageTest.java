package org.example.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void getEnumByString_shouldThrowLanguageNotFoundException_whenCalledWithNonexistentLanguage() {
        assertThrows(LanguageNotFoundException.class, () -> Language.getEnumByString("Esperanto"));
    }
    @Test
    void getEnumByString_shouldReturnSPANISHEnum_whenCalledWithStringSpanish() throws LanguageNotFoundException {
        Language expected = Language.SPANISH;
        Language actual= Language.getEnumByString("Spanish");
                assertEquals(expected, actual);
    }
}