package com.entity.type;

import com.exception.FileRowTypeParsingException;

public enum Type {
    A("Want to sell", "ask"),
    S("No sell or buy", "spread"),
    B("Want to buy", "bid");
    final String desc;
    final String rowValue;

    Type(String desc, String rowValue) {
        this.desc = desc;
        this.rowValue = rowValue;
    }

    public static Type getTypeFromString(String value) {
        for (Type type : Type.values()) {
            if (type.rowValue.equals(value)) {
                return type;
            }
        }

        throw new FileRowTypeParsingException("Type not supported yet");
    }
}
