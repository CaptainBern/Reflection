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
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.accessor.FieldAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.captainbern.reflection.Reflection.reflect;

public class SafeFieldImpl<T> implements SafeField<T> {

    protected Field field;

    public SafeFieldImpl(final Field field) {
        if(field == null)
            throw new IllegalArgumentException("Field can't be NULL!");

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

                    return (T) field.get(instance);
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

                    if(!Modifier.isStatic(getModifiers()) && value == null)
                        throw new IllegalArgumentException("Non-static fields require a valid instance passed-in!");

                    field.set(instance, value);
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
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ClassTemplate<?>> getArguments() {
        return new ArrayList<ClassTemplate<?>>();
    }

    @Override
    public ClassTemplate getType() {
        return reflect(this.field.getType());
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
        // TODO: reflect(Field.class).getFieldByName("modifiers").getAccessor().set(this.field, mods);

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
