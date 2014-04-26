package com.captainbern.reflection.matcher.types;

import com.captainbern.reflection.matcher.Matcher;

public class ClassTypeMatcher extends Matcher<Class<?>> {

    private Class<?> type;

    public ClassTypeMatcher(Class<?> type) {
          this.type = type;
    }

    @Override
    public boolean matches(Object parent, Class<?> value) {
        if(value == null)
            return false;
        return value.isAssignableFrom(this.type);
    }
}
