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
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.conversion.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SafeFieldImpl<T> implements SafeField<T> {

    protected final Reflection reflection;
    protected Field field;

    protected Converter<T> converter;

    public SafeFieldImpl(final Reflection reflection, final Field field) {
        if(field == null)
            throw new IllegalArgumentException("Field can't be NULL!");

        this.reflection = reflection;
        this.field = field;

        if(!this.field.isAccessible()) {
            try {
                this.field.setAccessible(true);
            } catch (SecurityException e) {
                // Ignore
            }
        }
    }

    @Override
    public Field member() {
        return this.field;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FieldAccessor<T> getAccessor() {
        return new FieldAccessor<T>() {
            @Override
            public T get(Object instance) {
                try {
                    if(SafeFieldImpl.this.field == null)
                        throw new RuntimeException("Field is NULL!");

                    if(instance == null && !Modifier.isStatic(SafeFieldImpl.this.field.getModifiers()))
                        throw new IllegalArgumentException("Non-static fields require a valid instance passed in!");

                    if (needConversion()) {
                        return SafeFieldImpl.this.converter.getWrapped(field.get(instance));
                    } else {
                        return (T) field.get(instance);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public T getStatic() {
                return get(null);
            }

            @Override
            public void set(Object instance, T value) {
                try {
                    if(SafeFieldImpl.this.field == null)
                        throw new RuntimeException("Field is NULL!");

                    if(!Modifier.isStatic(getModifiers()) && instance == null)
                        throw new IllegalArgumentException("Non-static fields require a valid instance passed-in!");

                    if (needConversion()) {
                        field.set(instance, SafeFieldImpl.this.converter.getUnwrapped(SafeFieldImpl.this.member().getType(), value));
                    } else {
                        field.set(instance, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void setStatic(T value) {
                set(null, value);
            }

            @Override
            public void transfer(Object from, Object to) {
                if(field == null)
                    throw new IllegalStateException("Field is NULL!");

                set(to, get(from));
            }

            @Override
            public SafeField<T> getField() {
                return SafeFieldImpl.this;
            }
        };
    }

    @Override
    public SafeField<T> withConverter(Converter<T> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public Converter<T> getConverter() {
        return this.converter;
    }

    private boolean needConversion() {
        return this.converter != null;
    }

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ClassTemplate<?>> getArguments() {
        return new ArrayList<ClassTemplate<?>>();
    }

    @Override
    public ClassTemplate getType() {
        return this.reflection.reflect(this.field.getType());
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    @Override
    public String getName() {
        return this.field.getName();
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }

    @Override
    public void setModifiers(int mods) {
        this.reflection.reflect(Field.class).getSafeFieldByName("modifiers").getAccessor().set(this.field, mods);
    }

    @Override
    public boolean isSynthetic() {
        return this.field.isSynthetic();
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    @Override
    public boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    @Override
    public boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }
}
