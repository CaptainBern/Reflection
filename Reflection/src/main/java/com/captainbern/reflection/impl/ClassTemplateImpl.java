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

import com.captainbern.reflection.AbstractAccess;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeConstructor;
import com.captainbern.reflection.matcher.AbstractMatcher;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ClassTemplateImpl<T> extends AbstractAccess<T> implements ClassTemplate<T> {

    public ClassTemplateImpl(final Reflection reflection, final Class<T> clazz) {
        this(reflection, clazz, false);
    }

    public ClassTemplateImpl(final Reflection reflection, Class<T> clazz, boolean forceAccess) {
        super(reflection, clazz, forceAccess);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T newInstance(final Object... args) {
        try {
            if (args.length > 0) {
                List<SafeConstructor<T>> constructors = this.getSafeConstructors(new AbstractMatcher<Constructor>() {
                    @Override
                    public boolean matches(Constructor type) {
                        if (type.getParameterTypes().length != args.length)
                            return false;

                        Class[] paramTypes = type.getParameterTypes();
                        for (int i = 0; i < paramTypes.length; i++) {
                            if (!paramTypes[i].isAssignableFrom(args[i] instanceof Class ? (Class<?>) args[i] : args[i].getClass()))
                                return false;
                        }

                        return true;
                    }
                });

                if (constructors.size() > 0)
                    return constructors.get(0).getAccessor().invoke(args);

                String argBuilder = args[0].getClass().getCanonicalName();
                for (int i = 1; i < args.length; i++) {
                    argBuilder += ", " + args[i].getClass().getCanonicalName();
                }

                throw new IllegalStateException("Failed to find a valid constructor for args: " + argBuilder);
            } else {
                return this.clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ClassTemplate> getSuperClasses() {
        List<ClassTemplate> classes = new ArrayList<>();
        for(Class<?> clazz : super.getAllSuperClasses()) {
            classes.add(super.reflection.reflect(clazz));
        }
        return classes;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + this.reflection.hashCode();
        hash = 31 * hash + this.clazz.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ClassTemplate))
            return false;

        if (other == this)
            return true;

        ClassTemplate otherTemplate = ((ClassTemplate) other);

        return otherTemplate.getReflectedClass().equals(this.clazz);
    }
}
