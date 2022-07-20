package com.droidlogic.neolink.server.model;

public class PirCommand extends Command {

    private static final String[] ALLOWED_VALUES = new String[]{"on", "off"};
    public static final String PIR_COMMAND = "pir";

    public PirCommand(String value) {
        super(PIR_COMMAND, value);
    }

    @Override
    String[] getAllowedValues() {
        return ALLOWED_VALUES;
    }
}
