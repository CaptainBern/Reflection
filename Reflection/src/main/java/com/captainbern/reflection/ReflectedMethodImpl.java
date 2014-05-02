package com.captainbern.reflection;

import com.captainbern.reflection.accessor.MethodAccessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ReflectedMethodImpl<T> implements ReflectedMethod<T> {

    protected Method method;

    public ReflectedMethodImpl(final Method method) {
        if(method == null)
            throw new IllegalArgumentException("Method can't be NULL!");

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
        return this.method.getParameterCount();
    }

    @Override
    public List<ReflectedClass<?>> getArguments() {
        return Reflection.reflect(Arrays.asList(this.method.getParameterTypes()));
    }

    @Override
    public ReflectedClass<?> getType() {
        return Reflection.reflect(this.method.getReturnType());
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

    @Override
    public MethodAccessor<T> getAccessor() {
        return new MethodAccessor<T>() {
            @Override
            public T invoke(Object instance, Object... args) {
                if(instance == null)
                    throw new IllegalArgumentException("Given instance can't be NULL!");

                if(ReflectedMethodImpl.this.method == null)
                    throw new RuntimeException("Method is NULL!");

                try {
                    return (T) ReflectedMethodImpl.this.method.invoke(instance, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public ReflectedMethod getMethod() {
                return ReflectedMethodImpl.this;
            }
        };
    }
}
