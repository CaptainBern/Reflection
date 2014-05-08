package com.captainbern.reflection;

import com.captainbern.reflection.matcher.Matcher;
import com.captainbern.reflection.matcher.Matchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

    public static boolean INCLUDE_OBJECT = true;

    public static Set<Class<?>> getAllSuperClasses(final Class<?> clazz) {
        Set<Class<?>> result = new HashSet<>();
        if(clazz != null && (INCLUDE_OBJECT || !clazz.equals(Object.class))) {
            result.add(clazz);
            result.addAll(getAllSuperClasses(clazz));
            for(Class<?> iface : clazz.getInterfaces()) {
                result.addAll(getAllSuperClasses(iface));
            }
        }
        return result;
    }

    public static Set<Class<?>> getAllSuperClasses(final Class<?> clazz, Matcher<? super Class<?>> matchers) {
        return match(getAllSuperClasses(clazz), matchers);
    }

    public static <T> Set<T> match(final Set<T> classes, final Matcher<? super T>... matchers) {
        if(classes.isEmpty()) {
            return classes;
        } else {
            Set<T> elements = new HashSet<>();
            Matcher<T> combinedMatcher = Matchers.combine(Arrays.asList(matchers));
            for(T clazz : classes) {
                 if(combinedMatcher.matches(clazz)) {
                     elements.add(clazz);
                 }
            }
            return elements;
        }
    }
}
