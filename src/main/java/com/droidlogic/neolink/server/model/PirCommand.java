package com.droidlogic.neolink.server.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class PirCommand implements Command {

    private static final String[] ALLOWED_VALUES = new String[]{"on", "off"};
    public static final String PIR_COMMAND = "pir";

    private final String value;

    @Override
    public String getName() {
        return PIR_COMMAND;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.of(value);
    }

    @Override
    public boolean isValidValue(String value) {
        return Arrays.asList(ALLOWED_VALUES).contains(value);
    }
}
