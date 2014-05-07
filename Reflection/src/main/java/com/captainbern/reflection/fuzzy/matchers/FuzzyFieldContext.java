package com.captainbern.reflection.fuzzy.matchers;

public class FuzzyFieldContext extends FuzzyMemberContext {

    @Override
    public boolean matches(Object type) {
        return false;
    }

    public static class Builder extends FuzzyMemberContext.Builder<FuzzyFieldContext> {

        @Override
        protected FuzzyFieldContext prepareContext() {
            return null;
        }
    }
}
