package com.droidlogic.neolink.server.model;

import java.util.Optional;

public class ReadPirCommand implements Command {
    public static final String READPIR_COMMAND = "readpir";

    @Override
    public String getName() {
        return READPIR_COMMAND;
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
