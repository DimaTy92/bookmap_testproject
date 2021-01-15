package com.entity.type;

import com.exception.FileRowTypeParsingException;

public enum QueryType {
    BEST_BID("best_bid"),
    BEST_ASK("best_ask"),
    SIZE("size");

    private String value;

    QueryType(String value) {
        this.value = value;
    }

    public static QueryType getQueryTypeFromString(String value) {
        for (QueryType type : QueryType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new FileRowTypeParsingException("Type not supported yet");
    }

    public String getValue() {
        return value;
    }
}
