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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.*;

public class AbstractAccess<T> implements Access<T> {

    public static boolean INCLUDE_OBJECT = false;

    protected final Reflection reflection;
    protected Class<T> clazz;
    private boolean forceAccess;

    public AbstractAccess(final Reflection reflection, final Class<T> clazz, final boolean forceAccess) {
        this.reflection = reflection;
        this.clazz = clazz;
        this.forceAccess = forceAccess;
    }

    @Override
    public Class<T> getReflectedClass() {
        return this.clazz;
    }

    @Override
    public List<Class<?>> getAllSuperClasses() {
        return getAllSuperClasses(this.clazz);
    }

    @Override
    public List<Class<?>> getAllSuperClasses(final Matcher<? super Class<?>>... matchers) {
        return match(getAllSuperClasses(), matchers);
    }

    /**
     * Fields
     */

    @Override
    public List<Field> getFields() {
        List<Field> fields = new ArrayList<>();

        if (forceAccess) {
            fields.addAll(getAllFields(this.clazz));
        } else {
            fields.addAll(getFields(this.clazz));
        }

        return fields;
    }

    @Override
    public List<SafeField<?>> getSafeFields() {
        List<Field> fields = getFields();

        if (fields.size() < 0)
            return null;

        List<SafeField<?>> safeFields = new ArrayList<>();
        for (Field field : fields) {
            safeFields.add(this.reflection.reflect(field));
        }

        return safeFields;
    }

    @Override
    public List<Field> getFields(final Matcher<? super Field>... matchers) {
        return match(getFields(), matchers);
    }

    public List<SafeField<?>> getSafeFields(final Matcher<? super Field>... matchers) {
        List<Field> fields = getFields(matchers);

        if (fields.size() < 0)
            return null;

        List<SafeField<?>> safeFields = new ArrayList<>();
        for (Field field : fields) {
            safeFields.add(this.reflection.reflect(field));
        }

        return safeFields;
    }

    @Override
    public Field getFieldByName(final String name) {
        List<Field> fields = getFields(withExactName(name));

        if (fields.size() > 0) {
            // It's probably the first field...
            return fields.get(0);
        }

        return null;
    }

    @Override
    public <T> SafeField<T> getSafeFieldByName(final String name) {
        Field field = getFieldByName(name);
        return field == null ? null : (SafeField<T>) this.reflection.reflect(field);
    }

    @Override
    public Field getFieldByType(Class<?> type) {
        List<Field> fields = getFields(withType(type));

        if (fields.size() > 0) {
            return fields.get(0);
        }

        return null;
    }

    @Override
    public <T> SafeField<T> getSafeFieldByType(Class<T> type) {
        List<SafeField<?>> fields = getSafeFields(withType(type));
        if (fields.size() > 0) {
            return (SafeField<T>) fields.get(0);
        }

        return null;
    }

    @Override
    public Field getFieldByNameAndType(final String name, final Class<?> type) {
        List<Field> fields = getFields(withExactName(name));

        if (fields.size() > 0) {
            for (Field field : fields) {
                if (withType(type).matches(field)) {
                    return field;
                }
            }
        }

        return null;
    }

    public <T> SafeField<T> getSafeFieldByNameAndType(final String name, final Class<T> type) {
        Field field = getFieldByNameAndType(name, type);
        return field == null ? null : (SafeField<T>) this.reflection.reflect(field);
    }

    /**
     * Methods
     */

    @Override
    public List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();

        if (forceAccess) {
            methods.addAll(getAllMethods(this.clazz));
        } else {
            methods.addAll(getMethods(this.clazz));
        }

        return methods;
    }

    @Override
    public List<SafeMethod<?>> getSafeMethods() {
        List<Method> methods = getMethods();

        if (methods.size() < 0)
            return null;

        List<SafeMethod<?>> safeMethods = new ArrayList<>();
        for (Method method : methods) {
            safeMethods.add(this.reflection.reflect(method));
        }

        return safeMethods;
    }

    @Override
    public List<Method> getMethods(final Matcher<? super Method>... matchers) {
        return match(getMethods(), matchers);
    }

    @Override
    public List<SafeMethod<?>> getSafeMethods(final Matcher<? super Method>... matchers) {
        List<Method> methods = getMethods(matchers);

        if (methods.size() < 0)
            return null;

        List<SafeMethod<?>> safeMethods = new ArrayList<>();
        for (Method method : methods) {
            safeMethods.add(this.reflection.reflect(method));
        }

        return safeMethods;
    }

    @Override
    public Method getMethod(final String name) {
        List<Method> methods = getMethods(withExactName(name));
        if (methods.size() > 0) {
            return methods.get(0); // suppose it's the first one
        }
        return null;
    }

    @Override
    public SafeMethod getSafeMethod(final String name) {
        List<SafeMethod<?>> methods = getSafeMethods(withExactName(name));
        if (methods.size() > 0) {
            return methods.get(0);
        }
        return null;
    }

    @Override
    public Method getMethod(final String name, final Class... params) {
        List<Method> methods = getMethods(withExactName(name), withArguments(params));
        if (methods.size() > 0) {
            return methods.get(0);
        }
        return null;
    }

    @Override
    public SafeMethod getSafeMethod(final String name, final Class... params) {
        List<SafeMethod<?>> methods = getSafeMethods(withExactName(name), withArguments(params));
        if (methods.size() > 0) {
            return methods.get(0);
        }
        return null;
    }

    /**
     * Constructors
     */

    @Override
    public List<Constructor> getConstructors() {
        List<Constructor> constructors = new ArrayList<>();

        if (forceAccess) {
            constructors.addAll(getAllConstructors(this.clazz));
        } else {
            constructors.addAll(getConstructors(this.clazz));
        }

        return constructors;
    }

    @Override
    public List<SafeConstructor<T>> getSafeConstructors() {
        List<Constructor> constructors = getConstructors();

        if (constructors.size() < 0)
            return null;

        List<SafeConstructor<T>> safeConstructors = new ArrayList<>();
        for (Constructor constructor : constructors) {
            safeConstructors.add(this.reflection.reflect(constructor));
        }

        return safeConstructors;
    }

    // TODO: Improve these.
    @Override
    public Constructor<T> getConstructor(Class... params) {
        try {
            return (Constructor<T>) this.clazz.getConstructor(params);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    // TODO: Improve
    @Override
    public SafeConstructor<T> getSafeConstructor(Class... params) {
        return (SafeConstructor<T>) this.reflection.reflect(getConstructor(params));
    }

    @Override
    public List<Constructor> getConstructors(final Matcher<? super Constructor>... matchers) {
        return match(getConstructors(), matchers);
    }

    @Override
    public List<SafeConstructor<T>> getSafeConstructors(final Matcher<? super Constructor>... matchers) {
        List<Constructor> constructors = getConstructors(matchers);

        if (constructors.size() < 0)
            return null;

        List<SafeConstructor<T>> safeConstructors = new ArrayList<>();
        for (Constructor constructor : constructors) {
            safeConstructors.add(this.reflection.reflect(constructor));
        }

        return safeConstructors;
    }

    /**
     * Other things
     */

    @Override
    public boolean isAssignableFrom(final Class<?> clazz) {
        return this.getReflectedClass().isAssignableFrom(clazz);
    }

    @Override
    public boolean isAssignableFromObject(final Object object) {
        return this.getReflectedClass().isAssignableFrom(object.getClass());
    }

    @Override
    public boolean isInstanceOf(final Object object) {
        return this.getReflectedClass().isInstance(object);
    }

    @Override
    public Type getType() {
        return this.getReflectedClass().getGenericSuperclass();
    }

    /**
     * Utility methods...
     */

    protected static List<Class<?>> getAllSuperClasses(final Class<?> clazz) {
        List<Class<?>> result = new ArrayList<>();
        if (clazz != null && (INCLUDE_OBJECT || !clazz.equals(Object.class))) {
            result.add(clazz);
            result.addAll(getAllSuperClasses(clazz.getSuperclass()));
            for (Class<?> iface : clazz.getInterfaces()) {
                result.addAll(getAllSuperClasses(iface));
            }
        }
        return result;
    }

    protected static List<Field> getFields(final Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, clazz.getDeclaredFields());
        return fields;
    }

    protected static List<Field> getAllFields(final Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (final Class<?> superClass : getAllSuperClasses(clazz)) {
            fields.addAll(getFields(superClass));
        }
        return fields;
    }

    protected static List<Method> getMethods(final Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        return methods;
    }

    protected static List<Method> getAllMethods(final Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        for (final Class<?> superClass : getAllSuperClasses(clazz)) {
            methods.addAll(getMethods(superClass));
        }
        return methods;
    }

    protected static List<Constructor> getConstructors(final Class<?> clazz) {
        List<Constructor> constructors = new ArrayList<>();
        Collections.addAll(constructors, clazz.getDeclaredConstructors());
        return constructors;
    }

    protected static List<Constructor> getAllConstructors(final Class<?> clazz) {
        List<Constructor> constructors = new ArrayList<>();
        for (Class<?> superClass : getAllSuperClasses(clazz)) {
            constructors.addAll(getConstructors(superClass));
        }
        return constructors;
    }

    protected static <T> List<T> match(final List<T> classes, final Matcher<? super T>... matchers) {
        if (classes.isEmpty()) {
            return classes;
        } else {
            List<T> elements = new ArrayList<>();
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
