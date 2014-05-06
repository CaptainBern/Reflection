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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class AbstractAccess<T> implements Access<T> {

    protected Class<T> clazz;
    private boolean forceAccess;

    public AbstractAccess(Class<T> clazz, boolean forceAccess) {
        this.clazz = clazz;
        this.forceAccess = forceAccess;
    }

    @Override
    public Class<T> getReflectedClass() {
        return this.clazz;
    }

    @Override
    public Set<SafeField> getFields() {
        Set<SafeField> fields = new LinkedHashSet<SafeField>();

        if(forceAccess) {
            for(Field field : getReflectedClass().getDeclaredFields()) {
                fields.add(Reflection.reflect(field));
            }
        }

        for(Field field : getReflectedClass().getFields()) {
            if(!fields.contains(field))
                fields.add(Reflection.reflect(field));
        }
        return fields;
    }

    @Override
    public Set<SafeField> getDeclaredFields(Class<?> exemptedSuperClass) {
        Set<SafeField> fields = new LinkedHashSet<SafeField>();

        Class<?> current = this.clazz;

        while(current != null && current != exemptedSuperClass) {
            for(Field field : current.getDeclaredFields()) {
                fields.add(Reflection.reflect(field));
            }
            current = current.getSuperclass();
        }

        return fields;
    }

    @Override
    public List<SafeField> getFieldsByType(Class<?> type) {
        List<SafeField> fields = new ArrayList<SafeField>();

        for(SafeField field : getFields()) {
            if(type.isAssignableFrom(field.getType().getReflectedClass())) {
                fields.add(field);
            }
        }

        return fields;
    }

    @Override
    public SafeField getFieldByNameAndType(String name, Class<?> type) {
        List<SafeField> fields = getFieldsByType(type);

        if(fields.size() > 0) {
            for (SafeField field : fields) {
                if (field.getName().equals(name)) {
                    return field;
                }
            }
        }

        throw new IllegalArgumentException("Failed to find field: " + name + " in class: " + this.clazz.getCanonicalName());
    }

    @Override
    public SafeField getFieldByName(String name) {
        for(SafeField field : getFields()) {
            if(field.getName().equals(name)) {
                return field;
            }
        }

        throw new IllegalArgumentException("Failed to find field: " + name + " in class: " + this.clazz.getCanonicalName());
    }

    @Override
    public Set<SafeMethod> getMethods() {
        Set<SafeMethod> methods = new HashSet<>();

        if(forceAccess) {
            for(Method method : this.clazz.getDeclaredMethods()) {
                methods.add(Reflection.reflect(method));
            }
        }

        for(Method method : this.clazz.getMethods()) {
            if(!methods.contains(method))
                methods.add(Reflection.reflect(method));
        }

        return methods;
    }

    @Override
    public Set<SafeMethod> getDeclaredMethods(Class<?> exemptedSuperClass) {
        if(forceAccess) {
            Set<SafeMethod> methods = new LinkedHashSet<SafeMethod>();
            Class<?> current = this.clazz;

            while(current != null && current != exemptedSuperClass) {
                for(Method method : current.getDeclaredMethods()) {
                    methods.add(Reflection.reflect(method));
                }
                current = current.getSuperclass();
            }

            return methods;
        }

        return getMethods();
    }

    @Override
    public SafeMethod getMethod(String name, Class<?> returnType, Class[] arguments) {
        for(SafeMethod method : getMethods()) {
            if((name == null || method.getName().equals(name)) && (returnType == null || method.member().getReturnType().equals(returnType)) && (arguments == null || Arrays.equals(arguments, method.member().getParameterTypes()))) {
                return method;
            }
        }
        return null;
    }

    @Override
    public Set<SafeConstructor> getConstructors() {
        Set<SafeConstructor> constructors = new HashSet<>();

        if(forceAccess) {
            for(Constructor constructor : this.clazz.getDeclaredConstructors()) {
                constructors.add(Reflection.reflect(constructor));
            }
        }

        for(Constructor constructor : this.clazz.getConstructors()) {
            if(!constructors.contains(constructor))
                constructors.add(Reflection.reflect(constructor));
        }

        return constructors;
    }

    @Override
    public Set<SafeConstructor> getDeclaredConstructors(Class<?> exemptedSuperClass) {
        if(forceAccess) {
            Set<SafeConstructor> constructors = new LinkedHashSet<SafeConstructor>();
            Class<?> current = this.clazz;

            while(current != null && current != exemptedSuperClass) {
                for(Constructor constructor : current.getDeclaredConstructors()) {
                    constructors.add(Reflection.reflect(constructor));
                }
                current = current.getSuperclass();
            }

            return constructors;
        }

        return getConstructors();
    }

    @Override
    public boolean isAssignableFrom(Class<?> clazz) {
        return this.getReflectedClass().isAssignableFrom(clazz);
    }

    @Override
    public boolean isAssignableFromObject(Object object) {
        return this.getReflectedClass().isAssignableFrom(object.getClass());
    }

    @Override
    public boolean isInstanceOf(Object object) {
        return this.getReflectedClass().isInstance(object);
    }

    @Override
    public Type getType() {
        return this.getReflectedClass().getGenericSuperclass();
    }
}
