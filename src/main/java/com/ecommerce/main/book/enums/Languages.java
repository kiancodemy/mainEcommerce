package com.ecommerce.main.book.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Languages {
    ENGLISH,
    PERSIAN,
    ARABIC,
    FRENCH,
    GERMAN,
    SPANISH,
    ITALIAN,
    PORTUGUESE,
    DUTCH,
    JAPANESE,
    CHINESE;

    @JsonCreator
    public static Languages from(String value) {
        if (value == null) return null;
        return Languages.valueOf(value.trim().toUpperCase());
    }
}
