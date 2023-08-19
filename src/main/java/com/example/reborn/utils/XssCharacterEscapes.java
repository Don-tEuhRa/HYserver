package com.example.reborn.utils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;

public class XssCharacterEscapes extends CharacterEscapes {

    private final int[] asciiEscapes;


    public XssCharacterEscapes() {
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

    }
    @Override
    public int[] getEscapeCodesForAscii() {

        int[] asciiEscapesClone = null;
        if (asciiEscapes != null) {
            asciiEscapesClone = new int[asciiEscapes.length];
            for(int i = 0; i < asciiEscapes.length; i++) {
                asciiEscapesClone[i] = asciiEscapes[i];
            }
        }
        return asciiEscapesClone;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        // Check if ch is within the valid char range
        if (ch < Character.MIN_VALUE || ch > Character.MAX_VALUE) {
            throw new IllegalArgumentException("Value is out of valid char range");
        }

        return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
    }

}