package com.captainbern.reflection.matcher.type;

import java.lang.reflect.Constructor;

public class ConstructorMatcher extends MemberMatcher<Constructor> {

    private ConstructorMatcher() {
    }

    @Override
    public boolean matches(Constructor constructor) {
        if (super.matches(constructor)) {

        }

        return false;
    }

    public static class Builder extends MemberMatcher.Builder<ConstructorMatcher> {

        @Override
        protected ConstructorMatcher createMatcher() {
            return new ConstructorMatcher();
        }

        @Override
        public ConstructorMatcher build() {
            return this.matcher;
        }
    }
}
