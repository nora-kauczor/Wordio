package org.example.backend;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public enum Language {
    SPANISH("Spanish"),
    FRENCH("French"),
    ITALIAN("Italian");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getStringOfEnum() {
        return value;
    }

    public static Language getEnumByString(String language) {
        Language[] ArrayOfEnums = Language.values();
        Language searchedEnum = null;

        for (int i = 0; i < ArrayOfEnums.length; i++) {
            if (ArrayOfEnums[i].getStringOfEnum().equals(language)) {
                searchedEnum = ArrayOfEnums[i];
            }
        }
        if (searchedEnum == null){throw new NoSuchElementException();
        }
        return searchedEnum;
    }
}
