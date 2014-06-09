package com.captainbern.minecraft.error;

import java.io.PrintStream;

public class BasicErrorReporter implements ErrorReporter {

    private PrintStream output;

    public BasicErrorReporter() {
        this(System.out);
    }

    public BasicErrorReporter(PrintStream out) {
        this.output = out;
    }

    @Override
    public void report(Object sender, String method, Throwable throwable) {
        this.output.print("An exception occurred when executing method \"" + method + "\" in class " + sender.getClass().getCanonicalName());
        throwable.printStackTrace(this.output);
    }

    @Override
    public void report(Object sender, String method, Object... params) {

    }

    @Override
    public void reportDebug(Object sender, Report report) {

    }

    @Override
    public void reportWarning(Object sender, Report report) {

    }
}
