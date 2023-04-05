package com.droidlogic.neolink.server.model;

import java.util.Optional;

public interface Command {

    String getName();
    Optional<String> getValue();
    boolean isValidValue(String value);
}
