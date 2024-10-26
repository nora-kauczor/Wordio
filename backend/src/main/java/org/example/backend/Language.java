package org.example.backend;

public enum Language {
    SPANISH("Spanish"),
    FRENCH("French"),
    ITALIAN("Italian");

    private final String value;

    Language(String value){this.value = value;}

    public String getValue() {
        return value;
    }
}
