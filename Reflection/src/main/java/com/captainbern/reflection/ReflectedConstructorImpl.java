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

import com.captainbern.reflection.accessor.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ReflectedConstructorImpl<T> implements ReflectedConstructor<T> {

    protected Constructor<T> constructor;
    public ReflectedConstructorImpl(final Constructor<T> constructor) {
        if(constructor == null)
            throw new IllegalArgumentException("Constructor can't be NULL!");

        this.constructor = constructor;

        if(!constructor.isAccessible()) {
            try {
                constructor.setAccessible(true);
            } catch (SecurityException e) {
                // Ignore
            }
        }
    }

    @Override
    public Constructor member() {
        return this.constructor;
    }

    @Override
    public ConstructorAccessor getAccessor() {
        return new ConstructorAccessor<T>() {
            @Override
            public T invoke(Object... args) {
                if(ReflectedConstructorImpl.this.constructor == null)
                    throw new RuntimeException("Constructor is NULL!");

                try {
                    return ReflectedConstructorImpl.this.constructor.newInstance(args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public ReflectedConstructor getConstructor() {
                return ReflectedConstructorImpl.this;
            }
        };
    }

    @Override
    public int getArgumentCount() {
        return ReflectedConstructorImpl.this.getArgumentCount();
    }

    @Override
    public List<ReflectedClass<?>> getArguments() {
        return Reflection.reflect(Arrays.asList(this.constructor.getParameterTypes()));
    }

    @Override
    public ReflectedClass getType() {
        return Reflection.reflect(this.constructor.getDeclaringClass());
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.constructor.getDeclaringClass();
    }

    @Override
    public String getName() {
        return this.constructor.getName();
    }

    @Override
    public int getModifiers() {
        return this.constructor.getModifiers();
    }

    @Override
    public boolean isSynthetic() {
        return this.constructor.isSynthetic();
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
