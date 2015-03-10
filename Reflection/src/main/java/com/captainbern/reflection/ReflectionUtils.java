/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.reflection;

import com.captainbern.reflection.matcher.Matcher;
import com.captainbern.reflection.matcher.Matchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This *can* be used as a "lightweight" alternative to the default reflection stuff
 */
public class ReflectionUtils {

    public static boolean INCLUDE_OBJECT = true;

    public static Set<Class<?>> getAllSuperClasses(final Class<?> clazz) {
        Set<Class<?>> result = new HashSet<>();
        if(clazz != null && (INCLUDE_OBJECT || !clazz.equals(Object.class))) {
            result.add(clazz);
            result.addAll(getAllSuperClasses(clazz.getSuperclass()));
            for(Class<?> iface : clazz.getInterfaces()) {
                result.addAll(getAllSuperClasses(iface));
            }
        }
        return result;
    }

    public static Set<Class<?>> getAllSuperClasses(final Class<?> clazz, Matcher<? super Class<?>>... matchers) {
        return match(getAllSuperClasses(clazz), matchers);
    }

    public static Set<Field> getFields(final Class<?> clazz) {
        Set<Field> result = new HashSet<>();
        Collections.addAll(result, clazz.getDeclaredFields());
        return result;
    }

    public static Set<Field> getAllFields(final Class<?> clazz) {
        Set<Field> result = new HashSet<>();
        for(Class<?> superClass : getAllSuperClasses(clazz)) {
            result.addAll(getFields(superClass));
        }
        return result;
    }

    public static Set<Field> getFields(final Class<?> clazz, Matcher<? super Field>... matchers) {
        return match(getFields(clazz), matchers);
    }

    public static Set<Field> getAllFields(final Class<?> clazz, Matcher<? super Field>... matchers) {
        return match(getAllFields(clazz), matchers);
    }

    public static Set<Constructor> getConstructors(final Class<?> clazz) {
        Set<Constructor> result = new HashSet<>();
        Collections.addAll(result, clazz.getDeclaredConstructors());
        return result;
    }

    public static Set<Constructor> getAllConstructors(final Class<?> clazz) {
        Set<Constructor> result = new HashSet<>();
        for(Class<?> superClass : getAllSuperClasses(clazz)) {
            result.addAll(getConstructors(superClass));
        }
        return result;
    }

    public static Set<Constructor> getConstructors(final Class<?> clazz, Matcher<? super Constructor>... matchers) {
        return match(getConstructors(clazz), matchers);
    }

    public static Set<Constructor> getAllConstructors(final Class<?> clazz, Matcher<? super Constructor>... matchers) {
        return match(getAllConstructors(clazz), matchers);
    }

    public static Set<Method> getMethods(final Class<?> clazz) {
        Set<Method> result = new HashSet<>();
        Collections.addAll(result, clazz.getDeclaredMethods());
        return result;
    }

    public static Set<Method> getAllMethods(final Class<?> clazz) {
        Set<Method> result = new HashSet<>();
        for(Class<?> superClass : getAllSuperClasses(clazz)) {
            result.addAll(getMethods(superClass));
        }
        return result;
    }

    public static Set<Method> getMethods(final Class<?> clazz, Matcher<? super Method>... matchers) {
        return match(getMethods(clazz), matchers);
    }

    public static Set<Method> getAllMethods(final Class<?> clazz, Matcher<? super Method>... matchers) {
        return match(getAllMethods(clazz), matchers);
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
