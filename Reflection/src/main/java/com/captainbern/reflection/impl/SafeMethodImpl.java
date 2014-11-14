package com.captainbern.reflection.impl;

import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.captainbern.reflection.conversion.Converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class SafeMethodImpl<T> implements SafeMethod<T> {

    protected final Reflection reflection;
    protected Method method;

    protected Converter<T> converter;

    public SafeMethodImpl(final Reflection reflection, final Method method) {
        if(method == null)
            throw new IllegalArgumentException("Method can't be NULL!");

        this.reflection = reflection;
        this.method = method;

        if(!this.method.isAccessible()) {
            try {
                this.method.setAccessible(true);
            } catch (SecurityException e) {
                // Ignore
            }
        }
    }

    @Override
    public Method member() {
        return this.method;
    }

    @Override
    public int getArgumentCount() {
        return this.method.getParameterTypes().length;
    }

    @Override
    public List<ClassTemplate<?>> getArguments() {
        return this.reflection.reflectClasses(Arrays.asList(this.method.getParameterTypes()));
    }

    @Override
    public ClassTemplate<?> getType() {
        return this.reflection.reflect(this.method.getReturnType());
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    @Override
    public String getName() {
        return this.method.getName();
    }

    @Override
    public int getModifiers() {
        return this.method.getModifiers();
    }

    @Override
    public void setModifiers(int mods) {
        this.reflection.reflect(Method.class).getSafeFieldByName("modifiers").getAccessor().set(this.method, mods);
    }

    @Override
    public boolean isSynthetic() {
        return this.method.isSynthetic();
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

    @SuppressWarnings("unchecked")
    @Override
    public MethodAccessor<T> getAccessor() {
        return new MethodAccessor<T>() {
            @Override
            public T invoke(Object instance, Object... args) {

                if(SafeMethodImpl.this.method == null)
                    throw new RuntimeException("Method is NULL!");

                try {
                    if (needConversion()) {
                         return SafeMethodImpl.this.converter.getWrapped(SafeMethodImpl.this.method.invoke(instance, args));
                    } else {
                        return (T) SafeMethodImpl.this.method.invoke(instance, args);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public T invokeStatic(Object... args) {
                return invoke(null, args);
            }

            @Override
            public SafeMethod<T> getMethod() {
                return SafeMethodImpl.this;
            }
        };
    }

    @Override
    public SafeMethod<T> withConverter(Converter<T> converter) {
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
    public boolean isPackagePrivate() {
        return !(Modifier.isPrivate(this.method.getModifiers()) || Modifier.isPublic(this.method.getModifiers()));
    }

    @Override
    public boolean isOverridable() {
        return !(Modifier.isFinal(this.method.getModifiers())
                || Modifier.isPrivate(this.method.getModifiers())
                || Modifier.isStatic(this.method.getModifiers())
                || Modifier.isFinal(this.method.getDeclaringClass().getModifiers()));
    }
}
