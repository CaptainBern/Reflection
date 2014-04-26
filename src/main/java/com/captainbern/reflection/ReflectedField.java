package com.captainbern.reflection;

import com.captainbern.reflection.accessor.FieldAccessor;

import java.lang.reflect.Field;

/**
 * @author CaptainBern
 */
public interface ReflectedField<T> extends ReflectedMember {

    public Field member();

    public FieldAccessor<T> getAccessor();
}
