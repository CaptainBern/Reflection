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

import com.captainbern.reflection.provider.ReflectionProvider;
import com.captainbern.reflection.provider.impl.DefaultReflectionProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    private ReflectionProvider reflectionProvider;

    public Reflection() {
        this(new DefaultReflectionProvider());
    }

    public Reflection(final ReflectionProvider reflectionProvider) {
        this.reflectionProvider = reflectionProvider;
    }

    public ReflectionProvider getReflectionProvider() {
        return reflectionProvider;
    }

    public void setReflectionProvider(final ReflectionProvider reflectionProvider) {
        if (reflectionProvider == null) {
            throw new IllegalArgumentException("Cannot set the ReflectionProvider to NUL!");
        }
        this.reflectionProvider = reflectionProvider;
    }

    public <T> ClassTemplate<T> reflect(final Class<T> clazz, boolean forceAccess) {
        if (clazz == null) {
            throw new IllegalArgumentException("Given class may not be NULL!");
        }
        return getReflectionProvider().getClassProvider(this, clazz, forceAccess).asClassTemplate();
    }

    public <T> ClassTemplate<T> reflect(final Class<T> clazz) {
        return reflect(clazz, false);
    }

    public <T> ClassTemplate<T> reflect(final String className, boolean forceAccess) {
        if (className == null) {
            throw new IllegalArgumentException("Class name may not be NULL!");
        }
        return getReflectionProvider().getClassProvider(this, className, forceAccess).asClassTemplate();
    }

    public <T> ClassTemplate<T> reflect(final String className) {
        return reflect(className, false);
    }

    public <T> SafeConstructor<T> reflect(final Constructor<T> constructor) {
        if (constructor == null) {
            throw new IllegalArgumentException("Constructor may not be NULL!");
        }
        return getReflectionProvider().getConstructorProvider(this, constructor).asSafeConstructor();
    }

    public <T> SafeConstructor<T> reflect(final Class<T> clazz, final Class... args) {
        if (clazz == null) {
            throw new IllegalArgumentException("can't retrieve the constructor out of NULL!");
        }
        return getReflectionProvider().getConstructorProvider(this, clazz, args).asSafeConstructor();
    }

    public <T> SafeField<T> reflect(final Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field may not be NULL!");
        }
        return getReflectionProvider().getFieldProvider(this, field).asSafeField();
    }

    public <T> SafeField<T> reflect(final Class<?> clazz, final String name) {
        if (clazz == null || name == null) {
            throw new IllegalArgumentException("Class and field-name may not be NULL!");
        }
        return getReflectionProvider().getFieldProvider(this, clazz, name).asSafeField();
    }

    public <T> SafeMethod<T> reflect(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method may not be NULL!");
        }
        return getReflectionProvider().getMethodProvider(this, method).asSafeMethod();
    }

    public <T> SafeMethod<T> reflect(final Class<?> clazz, final String name, final Class... args) {
        if (clazz == null || name == null) {
            throw new IllegalArgumentException("Class and method-name may not be NULL!");
        }
        return getReflectionProvider().getMethodProvider(this, clazz, name, args).asSafeMethod();
    }

    /**
     * Eww methods
     */
    public List<ClassTemplate<?>> reflectClasses(final List<Class<?>> classes) {
        List<ClassTemplate<?>> classTemplates = new ArrayList<>();
        for(Class<?> clazz : classes) {
            classTemplates.add(reflect(clazz));
        }
        return classTemplates;
    }

    public List<SafeField<?>> reflectFields(final List<Field> fields) {
        List<SafeField<?>> safeFields = new ArrayList<>();
        for (Field field : fields) {
            safeFields.add(reflect(field));
        }
        return safeFields;
    }

    public List<SafeMethod<?>> reflectMethods(final List<Method> methods) {
        List<SafeMethod<?>> safeMethods = new ArrayList<>();
        for (Method method : methods) {
            safeMethods.add(reflect(method));
        }
        return safeMethods;
    }

    public List<SafeConstructor<?>> reflectConstructors(final List<Constructor> constructors) {
        List<SafeConstructor<?>> safeConstructors = new ArrayList<>();
        for(Constructor constructor : constructors) {
            safeConstructors.add(reflect(constructor));
        }
        return safeConstructors;
    }
}
