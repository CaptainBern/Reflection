package com.captainbern.reflection.matcher.type;

import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Method;

import static com.captainbern.reflection.matcher.Matchers.fromType;

public class MethodMatcher extends MemberMatcher<Method> {

    private AbstractMatcher<Class<?>> returnTypeMatcher;

    private MethodMatcher() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean matches(Method method) {
        if (super.matches(method)) {
            // TODO Finish this
        }
        return false;
    }

    public static class Builder extends MemberMatcher.Builder<MethodMatcher> {

        public Builder withReturnType(Class<?> returnType) {
            this.matcher.returnTypeMatcher = fromType(returnType);
            return this;
        }

        public Builder withReturnType(AbstractMatcher<Class<?>> returnType) {
            this.matcher.returnTypeMatcher = returnType;
            return this;
        }

        @Override
        protected MethodMatcher createMatcher() {
            return new MethodMatcher();
        }

        @Override
        public MethodMatcher build() {
            return this.matcher;
        }
    }
}
