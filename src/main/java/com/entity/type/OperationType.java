package com.entity.type;

import com.exception.FileRowTypeParsingException;

public enum OperationType {
    BUY("buy"),
    SELL("sell");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public static OperationType getTypeFromString(String value) {
        for (OperationType type : OperationType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        throw new FileRowTypeParsingException("Type not supported yet");
    }
}
