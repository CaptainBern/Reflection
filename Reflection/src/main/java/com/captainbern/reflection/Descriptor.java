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
import java.lang.reflect.Method;
import java.util.HashMap;

public class Descriptor {

    private static final HashMap<Class, Character> typeToDescriptor = new HashMap<Class, Character>();
    static {
        typeToDescriptor.put(Integer.TYPE, 'I');
        typeToDescriptor.put(Void.TYPE, 'V');
        typeToDescriptor.put(Boolean.TYPE, 'Z');
        typeToDescriptor.put(Byte.TYPE, 'B');
        typeToDescriptor.put(Character.TYPE, 'C');
        typeToDescriptor.put(Short.TYPE, 'S');
        typeToDescriptor.put(Double.TYPE, 'D');
        typeToDescriptor.put(Float.TYPE, 'F');
        typeToDescriptor.put(Long.TYPE, 'J');
    }

    /**
     * Sealed constructor.
     */
    private Descriptor() {
        super();
    }

    /**
     * Returns the descriptor of a given class.
     * @param clazz
     * @return
     */
    public static String getClassDescriptor(final Class<?> clazz) {
        StringBuffer buffer = new StringBuffer();
        getDescriptor(buffer, clazz);
        return buffer.toString();
    }

    /**
     * Returns a constructor descriptor.
     * @param constructor
     * @return
     */
    public static String getConstructorDescriptor(final Constructor constructor) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('(');

        Class[] parameters = constructor.getParameterTypes();
        for(Class<?> param : parameters) {
            getDescriptor(buffer, param);
        }

        return buffer.append(")V").toString();
    }

    /**
     * Returns the method-descriptor of a method.
     * @param method
     * @return
     */
    public static String getMethodDescriptor(final Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }
        buf.append(')');
        getDescriptor(buf, method.getReturnType());
        return buf.toString();
    }

    /**
     * Returns the descriptor of a given class.
     * @param buffer
     * @param clazz
     */
    public static void getDescriptor(final StringBuffer buffer, final Class<?> clazz) {
        Class<?> d = clazz;
        while (true) {
            if (d.isPrimitive()) {
                char car = typeToDescriptor.get(clazz);
                buffer.append(car);
                return;
            } else if (d.isArray()) {
                buffer.append('[');
                d = d.getComponentType();
            } else {
                buffer.append('L');
                String name = d.getName();
                int len = name.length();
                for (int i = 0; i < len; ++i) {
                    char car = name.charAt(i);
                    buffer.append(car == '.' ? '/' : car);
                }
                buffer.append(';');
                return;
            }
        }
    }
}
