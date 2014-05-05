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
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    public static <T> ReflectedField<T> reflect(Field field) {
        return new ReflectedFieldImpl<T>(field);
    }

    public static <T> ReflectedMethod<T> reflect(Method method) {
        return new ReflectedMethodImpl<T>(method);
    }

    public static ReflectedConstructor reflect(Constructor constructor) {
        return new ReflectedConstructorImpl(constructor);
    }

    public static <T> ReflectedClass<T> reflect(Class<T> clazz, boolean forceAccess) {
        return new ReflectedClassImpl<>(clazz, forceAccess);
    }

    public static <T> ReflectedClass<T> reflect(Class<T> clazz) {
        return reflect(clazz, true);
    }

    public static <T> ReflectedObject<T> reflect(T object) {
        return new ReflectedObjectImpl<>(object);
    }

    public static List<ReflectedClass<?>> reflect(List<Class<?>> classes) {
        List<ReflectedClass<?>> reflectedClasses = new ArrayList<>();
        for(Class<?> clazz : classes) {
            reflectedClasses.add(reflect(clazz));
        }
        return reflectedClasses;
    }
}
