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

package com.captainbern.reflection.fuzzy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public class FuzzyReflection {

    private Class<?> sourceClass;
    private boolean forceAccess;

    public FuzzyReflection(Class<?> sourceClass) {
        this(sourceClass, false);
    }

    public FuzzyReflection(Class<?> sourceClass, boolean forceAccess) {
        this.sourceClass = sourceClass;
        this.forceAccess = forceAccess;
    }

    public Set<Method> getMethods() {
        return getMethods(this.forceAccess);
    }

    public Set<Method> getMethods(boolean forceAccess) {
        Set<Method> methods = new LinkedHashSet<Method>();

        if(forceAccess) {
            for(Method method : this.sourceClass.getDeclaredMethods()) {
                methods.add(method);
            }
        }

        for(Method method : this.sourceClass.getMethods()) {
            methods.add(method);
        }

        return methods;
    }

    public Set<Method> getDeclaredMethods(Class<?> excludedSuperClass) {
        if(forceAccess) {
            Set<Method> methods = new LinkedHashSet<Method>();
            Class<?> current = this.sourceClass;

            while(current != null && current != excludedSuperClass) {
                methods.addAll(Arrays.asList(current.getDeclaredMethods()));
                current = current.getSuperclass();
            }

            return methods;
        }

        return getMethods();
    }


    /**
     * Fields
     */


    public Set<Field> getFields() {
        return getFields(this.forceAccess);
    }

    public Set<Field> getFields(boolean forceAccess) {
        Set<Field> fields = new LinkedHashSet<Field>();

        if(forceAccess) {
            for(Field field : this.sourceClass.getDeclaredFields()) {
                fields.add(field);
            }
        }

        for(Field field : this.sourceClass.getFields()) {
            fields.add(field);
        }

        return fields;
    }

    public Set<Field> getDeclaredFields(Class<?> excludedSuperClass) {
        if(this.forceAccess) {
            Set<Field> fields = new LinkedHashSet<Field>();
            Class<?> currentClass = this.sourceClass;

            while(currentClass != null && currentClass != excludedSuperClass) {
                fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }

            return fields;
        }

        return getFields();
    }

    public List<Field> getFieldListByType(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();

        for(Field field : getFields()) {
            if(type.isAssignableFrom(field.getType()))
                fields.add(field);
        }

        return fields;
    }

    public Field getFieldByType(String name, Class<?> type) {
        List<Field> fields = getFieldListByType(type);

        if(fields.size() > 0) {
            if(name == null)
                return fields.get(0);

            Pattern pattern = Pattern.compile(name);

            for(Field field : fields) {
                if(pattern.matcher(field.getName()).matches()) {
                    return field;
                }
            }
        }

        throw new IllegalArgumentException("Failed to find a field with the name: " + name + " and type: " +
                type.getCanonicalName() + " in: " + this.sourceClass.getCanonicalName());
    }

    public Field getFieldByName(String name) {
        Pattern pattern = Pattern.compile(name);

        for(Field field : getFields()) {

            if(pattern.matcher(field.getName()).matches()) {
                return field;
            }
        }

        throw new IllegalArgumentException("Unable to find a field with: " + pattern + "in: " + this.sourceClass.getCanonicalName());
    }
}