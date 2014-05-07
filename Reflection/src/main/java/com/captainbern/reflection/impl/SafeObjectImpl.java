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

import com.captainbern.reflection.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class SafeObjectImpl<T> implements SafeObject<T> {

    protected T object;

    public SafeObjectImpl(final T object) {
        if(object == null)
            throw new IllegalArgumentException("Object is NULL!");

        this.object = object;
    }

    @Override
    public ClassTemplate<T> asReflectedClass() {
        return (ClassTemplate<T>) Reflection.reflect(this.object);
    }

    @Override
    public T value() {
        return this.object;
    }

    @Override
    public <C> C cast(Class<C> type) {
        return type.cast(this.object);
    }

    @Override
    public Class<T> getReflectedClass() {
        return asReflectedClass().getReflectedClass();
    }

    @Override
    public Set<SafeField> getFields() {
        return asReflectedClass().getFields();
    }

    @Override
    public Set<SafeField> getDeclaredFields(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredFields(exemptedSuperClass);
    }

    @Override
    public List<SafeField> getFieldsByType(Class<?> type) {
        return asReflectedClass().getFieldsByType(type);
    }

    @Override
    public SafeField getFieldByNameAndType(String name, Class<?> type) {
        return asReflectedClass().getFieldByNameAndType(name, type);
    }

    @Override
    public SafeField getFieldByName(String name) {
        return asReflectedClass().getFieldByName(name);
    }

    @Override
    public Set<SafeMethod> getMethods() {
        return asReflectedClass().getMethods();
    }

    @Override
    public Set<SafeMethod> getDeclaredMethods(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredMethods(exemptedSuperClass);
    }

    @Override
    public SafeMethod getMethod(String name, Class<?> returnType, Class[] arguments) {
        return asReflectedClass().getMethod(name, returnType, arguments);
    }

    @Override
    public Set<SafeConstructor> getConstructors() {
        return asReflectedClass().getConstructors();
    }

    @Override
    public Set<SafeConstructor> getDeclaredConstructors(Class<?> exemptedSuperClass) {
        return asReflectedClass().getDeclaredConstructors(exemptedSuperClass);
    }

    @Override
    public boolean isAssignableFrom(Class<?> clazz) {
        return asReflectedClass().isAssignableFrom(clazz);
    }

    @Override
    public boolean isAssignableFromObject(Object object) {
        return asReflectedClass().isAssignableFrom(object.getClass());
    }

    @Override
    public boolean isInstanceOf(Object object) {
        return asReflectedClass().isInstanceOf(object);
    }

    @Override
    public Type getType() {
        return asReflectedClass().getType();
    }
}
