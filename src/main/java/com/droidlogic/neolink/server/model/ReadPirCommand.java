package com.droidlogic.neolink.server.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class ReadPirCommand implements Command {
    public static final String PIR_COMMAND = "pir";

    @Override
    public String getName() {
        return PIR_COMMAND;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.empty();
    }

    @Override
    public boolean isValidValue(String value) {
        return true;
    }
}
