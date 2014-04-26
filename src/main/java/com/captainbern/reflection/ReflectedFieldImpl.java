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

import com.captainbern.reflection.accessor.FieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectedFieldImpl<T> implements ReflectedField<T> {

    protected Field field;

    public ReflectedFieldImpl(Field field) {
        if(field == null)
            throw new IllegalArgumentException("Field can't be NULL!");

        this.field = field;
        try {
            this.field.setAccessible(true);
        } catch (SecurityException e) {
            // Ignore
        }
    }

    @Override
    public Field member() {
        return this.field;
    }

    @Override
    public FieldAccessor<T> getAccessor() {
        return new FieldAccessor<T>() {
            @Override
            public T get(Object instance) {
                try {
                    return (T) field.get(instance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void set(Object instance, T value) {
                try {
                    if(!Modifier.isStatic(getModifiers()) && value == null)
                        throw new IllegalArgumentException("Non-static fields require a valid instance passed-in!");

                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void transfer(Object from, Object to) {
                if(field == null)
                    throw new IllegalStateException("Field is NULL!");

                T old = get(to);
                set(to, get(from));
            }

            @Override
            public ReflectedField getField() {
                return ReflectedFieldImpl.this;
            }
        };
    }

    @Override
    public String name() {
        return this.field.getName();
    }

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ReflectedClass> getArguments() {
        return new ArrayList<ReflectedClass>();
    }

    @Override
    public ReflectedClass getType() {
        return Reflection.reflect(this.field.getType());
    }

    @Override
    public String getDescriptor() {
        return Descriptor.getClassDescriptor(getType().getReflectedClass());
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }
}
