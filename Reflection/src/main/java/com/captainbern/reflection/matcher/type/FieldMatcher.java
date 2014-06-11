package com.captainbern.reflection.matcher.type;

import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Field;

import static com.captainbern.reflection.matcher.Matchers.fromType;

public class FieldMatcher extends MemberMatcher<Field> {

    public static class Builder extends MemberMatcher.Builder<FieldMatcher> {

        @Override
        protected FieldMatcher createMatcher() {
            return new FieldMatcher();
        }

        public Builder typeMatches(Class<?> type) {
            this.matcher.typeMatch = fromType(type);
            return this;
        }

        public Builder typeMatches(AbstractMatcher<Class<?>> matcher) {
            this.matcher.typeMatch = matcher;
            return this;
        }

        @Override
        public FieldMatcher build() {
            return this.matcher;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private AbstractMatcher<Class<?>> typeMatch;

    private FieldMatcher() {
        super();
    }

    @Override
    public boolean matches(Field value) {
        if (super.matches(value)) {
            return typeMatch.matches(value.getType());
        }
        // No match
        return false;
    }
}
