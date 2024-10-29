package org.example.backend;

import com.fasterxml.jackson.annotation.JsonValue;



public enum Language {
    SPANISH("Spanish"),
    FRENCH("French"),
    ITALIAN("Italian");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    @JsonValue
    public String getStringOfEnum() {
        return value;
    }

    public static Language getEnumByString(String language) throws LanguageNotFoundException {
        Language[] ArrayOfEnums = Language.values();
        Language searchedEnum = null;

        for (int i = 0; i < ArrayOfEnums.length; i++) {
            if (ArrayOfEnums[i].getStringOfEnum().equals(language)) {
                searchedEnum = ArrayOfEnums[i];
            }
        }
        if (searchedEnum == null){throw new LanguageNotFoundException("Parameter invalid.");
        }
        return searchedEnum;
    }
}
