package com.captainbern.minecraft.error;

import java.io.PrintStream;

public class BasicErrorReporter {

    private PrintStream output;

    public BasicErrorReporter() {
        this(System.out);
    }

    public BasicErrorReporter(PrintStream out) {

    }
}
