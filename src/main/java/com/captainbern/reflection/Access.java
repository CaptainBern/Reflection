package com.captainbern.reflection;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author CaptainBern
 */
public interface Access<T> {

    /**
     * Returns the reflected class. This is the class we're currently working with.
     * @return
     */
    public Class<T> getReflectedClass();

    /**
     * Returns a Set of all the public fields of the reflected class.
     * @return
     */
    public Set<ReflectedField> getFields();

    /**
     * Returns a list of all the fields of the reflected class and it's super classes until it reaches {@param exemptedSuperClass}
     * @param exemptedSuperClass
     * @return
     */
    public Set<ReflectedField> getDeclaredFields(Class<?> exemptedSuperClass);

    /**
     * Returns a list of all the fields of type {@param type}
     * @param type
     * @return
     */
    public List<ReflectedField> getFieldsByType(Class<?> type);

    /**
     * Returns a field with {@param name} of type: {@param type}. If the field isn't found
     * or the type is incorrect this will throw an IllegalArgumentException.
     * @param name
     * @param type
     * @return
     */
    public ReflectedField getFieldByNameAndType(String name, Class<?> type);

    /**
     * Returns the field with this specific name.
     * @param name
     * @return
     */
    public ReflectedField getFieldByName(String name);

    /**
     * Returns a Set of all the public methods.
     * @return
     */
    public Set<ReflectedMethod> getMethods();

    /**
     * Returns a list of all the methods and the methods of this class's superclasses until it reaches a specific superclass.
     * @param exemptedSuperClass
     * @return
     */
    public Set<ReflectedMethod> getDeclaredMethods(Class<?> exemptedSuperClass);

    /**
     * Whether or not this reflected class is assignable from {@param clazz}
     * @param clazz
     * @return
     */
    public boolean isAssignableFrom(Class<?> clazz);

    /**
     * Whether or not this class is assignable from {@param object}
     * @param object
     * @return
     */
    public boolean isAssignableFromObject(Object object);

    /**
     * Returns whether or not the reflected class is an instance of the given object.
     * @param object
     * @return
     */
    public boolean isInstanceOf(Object object);

    /**
     * Returns the modifiers of this class.
     * @return
     */
    public int modifiers();

    /**
     * Returns the name of this class.
     * @return
     */
    public String name();

    /**
     * Returns the type of this class. (GenericSuperClass)
     * @return
     */
    public Type getType();
}