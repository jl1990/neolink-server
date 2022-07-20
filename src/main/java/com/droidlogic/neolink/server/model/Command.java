package com.droidlogic.neolink.server.model;

import lombok.Data;

import java.util.Arrays;

@Data
public abstract class Command {

    private final String name;
    private final String value;

    public Command(String name, String value) {
        this.name = name;
        this.value = value;
        validateValue();
    }

    void validateValue() {
        boolean isValidValue = Arrays.stream(getAllowedValues())
                .anyMatch(allowedValue -> allowedValue.equalsIgnoreCase(value));
        if (!isValidValue) {
            throw new IllegalArgumentException("Invalid value " + value + " for command " + name);
        }
    }

    abstract String[] getAllowedValues();
}
