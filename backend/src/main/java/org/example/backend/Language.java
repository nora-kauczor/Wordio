package org.example.backend;

import java.util.ArrayList;

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

    public Language getEnum(String language) {
        Language[] ArrayOfEnums = Language.values();
        for (int i = 1; i < ArrayOfEnums.length; i++) {
            if (ArrayOfEnums[i].getStringOfEnum().equals(language)) {
                return ArrayOfEnums[i];
            }
        }
        return null;
    }
}
