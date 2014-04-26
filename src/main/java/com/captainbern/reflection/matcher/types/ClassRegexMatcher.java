package com.captainbern.reflection.matcher.types;

import com.captainbern.reflection.matcher.Matcher;

import java.util.regex.Pattern;

public class ClassRegexMatcher extends Matcher<Class<?>> {

    private Pattern regex;

    public ClassRegexMatcher(Pattern regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(Object parent, Class<?> value) {
        if(value == null)
            return false;
        return this.regex.matcher(value.getCanonicalName()).matches();
    }
}
