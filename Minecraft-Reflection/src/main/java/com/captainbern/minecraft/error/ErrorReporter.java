package com.captainbern.minecraft.error;

public interface ErrorReporter {

    public void report(Object sender, String method, Throwable throwable);

    public void report(Object sender, String method, Object... params);

    public void reportDebug(Object sender, Report report);

    public void reportWarning(Object sender, Report report);

}
