package com.captainbern.reflection.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ClassUtils {

    public static final String CLASS_FILE_SUFFIX = ".class";

    public static final char INNER_SEPARATOR_CHAR = '$';

    public static final String INNER_SEPARATOR = String.valueOf(INNER_SEPARATOR_CHAR);

    public static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);

    public ClassUtils() {
        super();
    }

    private static final HashMap<Class, Class> wrapperToPrimitive = new HashMap<Class, Class>();
    static {
        wrapperToPrimitive.put(Boolean.class, boolean.class);
        wrapperToPrimitive.put(Byte.class, byte.class);
        wrapperToPrimitive.put(Character.class, char.class);
        wrapperToPrimitive.put(Double.class, double.class);
        wrapperToPrimitive.put(Float.class, float.class);
        wrapperToPrimitive.put(Integer.class, int.class);
        wrapperToPrimitive.put(Long.class, long.class);
        wrapperToPrimitive.put(Short.class, short.class);
        wrapperToPrimitive.put(Void.class, void.class);
    }

    private static final HashMap<Class, Class> primitiveToWrapper = new HashMap<Class, Class>();
    static {
        for(final Class<?> wrapper : wrapperToPrimitive.keySet()) {
            final Class<?> primitive = wrapperToPrimitive.get(wrapper);
            if(primitive != null && !primitive.equals(wrapper)) {
                primitiveToWrapper.put(primitive, wrapper);
            }
        }
    }

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
     * Returns whether or not the given class is a primitive.
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(final Class clazz) {
        return primitiveToWrapper.containsKey(clazz);
    }

    /**
     * Returns whether or not the given class is a wrapper for a primitive type.
     * @param clazz
     * @return
     */
    public static boolean isWrapper(final Class clazz) {
        return wrapperToPrimitive.containsKey(clazz);
    }

    /**
     * Returns whether or not the given class is a primitive or wrapper.
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveOrWrapper(final Class clazz) {
        if(clazz == null)
            return false;
        return isPrimitive(clazz) || isWrapper(clazz);
    }

    /**
     * Converts a primitive to it's wrapper.
     * @param clazz
     * @return
     */
    public static Class primitiveToWrapper(final Class clazz) {
        if(clazz == null) {
            return null;
        }

        Class converted = clazz;
        if(isPrimitive(clazz)) {
            converted = primitiveToWrapper.get(clazz);
        }
        return converted;
    }

    /**
     * Converts a primitive to it's wrapper.
     * @param clazz
     * @return
     */
    public static Class wrapperToPrimitive(final Class clazz) {
        if(clazz == null) {
            return null;
        }

        Class converted = clazz;
        if(isWrapper(clazz)) {
            converted = wrapperToPrimitive.get(clazz);
        }
        return converted;
    }

    /**
     * Returns the current ClassLoader.
     * @return
     */
    public static ClassLoader getCurrentClassLoader() {
        ClassLoader loader = null;

        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {

        }

        if(loader == null) {
            loader = ClassUtils.class.getClassLoader();
        }

        return loader;
    }

    /**
     * Returns a class with the given name.
     * @param name
     * @return
     */
    public static Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Casts an object to the given class.
     * @param source
     * @param cast
     * @return
     */
    public static Object tryCast(Object source, Class cast) {
        return cast.cast(source);
    }

    /**
     * Returns a list of all superClasses of a given class.
     * @param clazz
     * @return
     */
    public static List<Class<?>> getSuperClasses(final Class<?> clazz) {
        if(clazz == null) {
            return null;
        }

        List<Class<?>> classes = new ArrayList<Class<?>>();
        Class<?> superClass = clazz.getSuperclass();

        while (superClass != null) {
            classes.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return classes;
    }

    /**
     * Returns a list of all interfaces the given class implements. (and all it's superClasses)
     * @param clazz
     * @return
     */
    public static List<Class<?>> getInterfaces(final Class<?> clazz) {
        if(clazz == null) {
            return null;
        }

        final LinkedList<Class<?>> interfaces = new LinkedList<Class<?>>();
        getInterfaces(clazz, interfaces);

        return interfaces;
    }

    /**
     * Populates a given list with all the interfaces the given class implements. (and it's superClasses)
     * @param clazz
     * @param interfaces
     */
    public static void getInterfaces(Class<?> clazz, final List<Class<?>> interfaces) {
        while (clazz != null) {
            for(Class<?> interfaceClazz : clazz.getInterfaces()) {
                interfaces.add(interfaceClazz);
                getInterfaces(interfaceClazz, interfaces);
            }

            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Returns the name of a class as a system-resource.
     * @param clazz
     * @return
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getName().replace(PACKAGE_SEPARATOR_CHAR, IOUtils.DIR_SEPARATOR_CHAR) + CLASS_FILE_SUFFIX;
    }

    /**
     * Converts a class to a byte-array or bytecode.
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] classToBytes(String source) throws IOException {
        return IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(source.replace(PACKAGE_SEPARATOR_CHAR, IOUtils.DIR_SEPARATOR_CHAR) + CLASS_FILE_SUFFIX));
    }

    /**
     * Returns the location of the Jar of a given class.
     * @param clazz
     * @return
     */
    public static File getJarLocation(Class clazz) {
        CodeSource source = clazz.getProtectionDomain().getCodeSource();

        try {
            URI location = source.getLocation().toURI();
            File file = new File(location);

            if(file.exists()) {
                return file;
            }
        } catch (URISyntaxException e) {
        }
        return null;
    }
}
