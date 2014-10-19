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

package com.captainbern.reflection.impl;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.EnumModifier;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumModifierImpl<T extends Enum<T>> implements EnumModifier {

    private static FieldAccessor ENUM_CONSTANTS_ACCESSOR = null;
    private static FieldAccessor ENUM_CONSTANT_DIRECTORY_ACCESSOR = null;

    private static MethodAccessor<Object> CONSTRUCTOR_ACCESSOR_ACCESSOR = null;
    private static MethodAccessor<Void> ACQUIRE_CONSTRUCTOR_ACCESSOR = null;
    private static MethodAccessor<Object> NEW_INSTANCE_ACCESSOR = null;

    private final Reflection reflection;
    private final Class<T> enumType;
    private FieldAccessor valuesField;

    public EnumModifierImpl(Reflection reflection, Class<T> enumType) {
        this.reflection = reflection;

        initializeFields();
        fixFields();

        this.enumType = enumType;
        this.initializeValuesField();
    }

    private static void initializeFields() {
        if (ENUM_CONSTANTS_ACCESSOR != null && ENUM_CONSTANT_DIRECTORY_ACCESSOR != null
                && CONSTRUCTOR_ACCESSOR_ACCESSOR != null && ACQUIRE_CONSTRUCTOR_ACCESSOR != null)
            return;

        ClassTemplate<Class> classTemplate = new Reflection().reflect(Class.class);

        ENUM_CONSTANTS_ACCESSOR = classTemplate.getSafeFieldByName("enumConstants").getAccessor();
        ENUM_CONSTANT_DIRECTORY_ACCESSOR = classTemplate.getSafeFieldByName("enumConstantDirectory").getAccessor();

        ClassTemplate<Constructor> constructorClassTemplate = new Reflection().reflect(Constructor.class);

        CONSTRUCTOR_ACCESSOR_ACCESSOR = constructorClassTemplate.getSafeMethod("getConstructorAccessor").getAccessor();
        ACQUIRE_CONSTRUCTOR_ACCESSOR = constructorClassTemplate.getSafeMethod("acquireConstructorAccessor").getAccessor();
        NEW_INSTANCE_ACCESSOR = new Reflection().reflect(sun.reflect.ConstructorAccessor.class).getSafeMethod("newInstance", Object[].class).getAccessor();
    }

    private static void fixFields() {
        int mods;

        mods = ENUM_CONSTANTS_ACCESSOR.getField().getModifiers();
        ENUM_CONSTANTS_ACCESSOR.getField().setModifiers(mods & -17);

        mods = ENUM_CONSTANT_DIRECTORY_ACCESSOR.getField().getModifiers();
        ENUM_CONSTANT_DIRECTORY_ACCESSOR.getField().setModifiers(mods & -17);
    }

    private void initializeValuesField() {
        if (this.valuesField != null)
            return;

        this.valuesField = this.reflection.reflect(this.enumType).getSafeFieldByName("$VALUES").getAccessor();
        int mods = this.valuesField.getField().getModifiers();
        this.valuesField.getField().setModifiers(mods & -17);
    }

    @Override
    public void addEnumValue(String name, Object... args) {
        T[] oldValues = (T[]) this.valuesField.get(this.enumType);
        List<T> values = new ArrayList<>(Arrays.asList(oldValues));

        T newValue = bake(name, values.size(), args);

        values.add(newValue);

        this.valuesField.getField().setModifiers(Modifier.STATIC);
        this.valuesField.set(this.enumType, values.toArray((T[]) Array.newInstance(this.enumType, 0)));

        cleanEnumCache();
    }

    private Constructor<T> getConstructor(Object... args) {
        final Object[] parameters = new Object[2 + args.length];
        parameters[0] = String.class;
        parameters[1] = int.class;

        System.arraycopy(args, 0, parameters, 2, args.length);

        List<SafeConstructor<T>> constructors = new Reflection().reflect(this.enumType).getSafeConstructors(new AbstractMatcher<Constructor>() {
            @Override
            public boolean matches(Constructor type) {
                if (type.getParameterTypes().length != parameters.length)
                    return false;

                Class<?>[] paramTypes = type.getParameterTypes();
                for (int i = 0; i < parameters.length; i++) {
                    if (!paramTypes[i].isAssignableFrom(parameters[i] instanceof Class ? (Class<?>) parameters[i] : parameters[i].getClass()))
                        return false;
                }

                return true;
            }
        });

        if (constructors.size() > 0)
            return constructors.get(0).member();

        if (args.length > 0) {
            String argBuilder = args[0].getClass().getCanonicalName();
            for (int i = 1; i < args.length; i++) {
                argBuilder += ", " + args[i].getClass().getCanonicalName();
            }

            throw new IllegalStateException("Failed to find the enum-constructor for: " + argBuilder);
        } else {
            throw new IllegalStateException("Failed to find the enum-constructor!");
        }
    }

    private T bake(String name, int ordinal, Object... args) {
        Object[] params = new Object[2 + args.length];
        params[0] = name;
        params[1] = ordinal;
        System.arraycopy(args, 0, params, 2, args.length);

        Constructor<T> constructor = getConstructor(args);
        ACQUIRE_CONSTRUCTOR_ACCESSOR.invoke(constructor);
        Object accessorInstance = CONSTRUCTOR_ACCESSOR_ACCESSOR.invoke(constructor);

        return (T) NEW_INSTANCE_ACCESSOR.invoke(accessorInstance, new Object[]{ params });
    }

    private void cleanEnumCache() {
        initializeFields();

        ENUM_CONSTANTS_ACCESSOR.set(this.enumType, null);
        ENUM_CONSTANT_DIRECTORY_ACCESSOR.set(this.enumType, null);
    }
}