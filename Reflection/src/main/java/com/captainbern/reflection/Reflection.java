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

import com.captainbern.reflection.impl.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    private Reflection() {}

    public static <T> SafeField<T> reflect(final Field field) {
        return new SafeFieldImpl<T>(field);
    }

    public static <T> SafeMethod<T> reflect(final Method method) {
        return new SafeMethodImpl<T>(method);
    }

    public static <T> SafeConstructor<T> reflect(final Constructor<T> constructor) {
        return new SafeConstructorImpl<T>(constructor);
    }

    public static <T> ClassTemplate<T> reflect(final Class<T> clazz, boolean forceAccess) {
        return new ClassTemplateImpl<T>(clazz, forceAccess);
    }

    public static <T> ClassTemplate<T> reflect(final Class<T> clazz) {
        return reflect(clazz, true);
    }

    public static <T> SafeObject<T> reflect(final T object) {
        return new SafeObjectImpl<>(object);
    }

    public static List<ClassTemplate<?>> reflect(final List<Class<?>> classes) {
        List<ClassTemplate<?>> classTemplates = new ArrayList<>();
        for(Class<?> clazz : classes) {
            classTemplates.add(reflect(clazz));
        }
        return classTemplates;
    }
}
