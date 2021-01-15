package com.entity.type;

import com.exception.FileRowTypeParsingException;

public enum FileRowType {
    UPDATE('u'),
    QUERY('q'),
    OPERATION('o');

    private final Character rowType;

    FileRowType(Character literal) {
        rowType = literal;
    }

    public static FileRowType getFileRowTypeFromChar(char literal) {
        for (FileRowType type : FileRowType.values()) {
            if (type.rowType.equals(literal)) {
                return type;
            }
        }

        throw new FileRowTypeParsingException("Type not supported yet");
    }
}
