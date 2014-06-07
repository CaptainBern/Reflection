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

import com.captainbern.reflection.matcher.Matcher;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author CaptainBern
 */
public interface Access<T> extends Serializable {

    /**
     * Returns the reflected class. This is the class we're currently working with.
     * @return
     */
    public Class<T> getReflectedClass();

    /**
     * Returns a List of all the super-classes of the underlying class.
     * @return
     */
    public List<Class<?>> getAllSuperClasses();

    /**
     * Returns a List of all the super-classes of the underlying class that match the matchers.
     * @param matchers
     * @return
     */
    public List<Class<?>> getAllSuperClasses(final Matcher<? super Class<?>>... matchers);

    /**
     * Returns a List of all the public of the reflected class.
     * @return
     */
    public List<Field> getFields();

    /**
     * Returns a List of all the fields of the reflected class, as a SafeField
     * @return
     */
    public List<SafeField<?>> getSafeFields();

    /**
     * Returns a list of all the fields that match the given matchers.
     * @return
     */
    public List<Field> getFields(final Matcher<? super Field>... matchers);

    /**
     * Returns a list of all the fields that match the given matchers as SafeFields
     * @param matchers
     * @return
     */
    public List<SafeField<?>> getSafeFields(final Matcher<? super Field>... matchers);

    /**
     * Returns a field which matches the given name
     * @param name
     * @return
     */
    public Field getFieldByName(final String name);

    /**
     * Returns a field which matches the given name as a SafeField.
     * @param name
     * @param <T>
     * @return
     */
    public <T> SafeField<T> getSafeFieldByName(final String name);

    /**
     * Returns the first field found that matches the given type
     * @param type
     * @return
     */
    public Field getFieldByType(final Class<?> type);

    /**
     * Returns the first field found that matches the given type as a SafeField
     * @param type
     * @param <T>
     * @return
     */
    public <T> SafeField<T> getSafeFieldByType(final Class<T> type);

    /**
     * Returns a field which matches the name and type
     * @param name
     * @param type
     * @return
     */
    public Field getFieldByNameAndType(final String name, final Class<?> type);

    /**
     * Returns a field which matches the given name and type as a SafeField
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    public <T> SafeField<T> getSafeFieldByNameAndType(final String name, final Class<T> type);

    /**
     * Returns a List of all the methods of the underlying class, keeping in mind the access-level
     * @return
     */
    public List<Method> getMethods();

    /**
     * Returns a List of all the methods of the underlying class as SafeMethods
     * @return
     */
    public List<SafeMethod<?>> getSafeMethods();

    /**
     * Returns a List of all the methods that match the matchers.
     * @param matchers
     * @return
     */
    public List<Method> getMethods(final Matcher<? super Method>... matchers);

    /**
     * Returns a List of all the methods that match the matchers as SafeMethods
     * @param matchers
     * @return
     */
    public List<SafeMethod<?>> getSafeMethods(final Matcher<? super Method>... matchers);

    /**
     * Returns a method with the given name
     * @param name
     * @return
     */
    public Method getMethod(final String name);

    /**
     * Returns a SafeMethod with the given name
     * @param name
     * @return
     */
    public SafeMethod getSafeMethod(final String name);

    /**
     * Returns a method with the given name and parameters
     * @param name
     * @param params
     * @return
     */
    public Method getMethod(final String name, final Class... params);

    /**
     * Returns a SafeMethod with the given name and parameters
     * @param name
     * @param params
     * @return
     */
    public SafeMethod getSafeMethod(final String name, final Class... params);

    /**
     * Returns a List of all the constructors of the underlying class, keeping in mind the access-level
     * @return
     */
    public List<Constructor> getConstructors();

    /**
     * Returns a List of all the constructors of the underlying class as SafeConstructors
     * @param <T>
     * @return
     */
    public <T> List<SafeConstructor<T>> getSafeConstructors();

    /**
     * Returns a constructor which has the given params.
     * @param params
     * @param <T>
     * @return
     */
    public <T> Constructor<T> getConstructor(Class... params);

    /**
     * Returns a constructor which has the given params as a SafeConstructor
     * @param params
     * @param <T>
     * @return
     */
    public <T> Constructor<T> getSafeConstructor(Class... params);

    /**
     * Returns a List of all the constructors of the underlying class that match the matchers.
     * @param matchers
     * @return
     */
    public List<Constructor> getConstructors(final Matcher<? super Constructor>... matchers);

    /**
     * Returns a List of all the constructors of the underlying class that match the matchers as SafeConstructors
     * @param matchers
     * @param <T>
     * @return
     */
    public <T> List<SafeConstructor<T>> getSafeConstructors(final Matcher<? super Constructor>... matchers);

    /**
     * Whether or not this reflected class is assignable from {@param clazz}
     * @param clazz
     * @return
     */
    public boolean isAssignableFrom(final Class<?> clazz);

    /**
     * Whether or not this class is assignable from {@param object}
     * @param object
     * @return
     */
    public boolean isAssignableFromObject(final Object object);

    /**
     * Returns whether or not the reflected class is an instance of the given object.
     * @param object
     * @return
     */
    public boolean isInstanceOf(final Object object);

    /**
     * Returns the type of this class. (GenericSuperClass)
     * @return
     */
    public Type getType();
}
