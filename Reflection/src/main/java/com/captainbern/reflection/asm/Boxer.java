package com.captainbern.reflection.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class Boxer {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    static {
        Map<Class<?>, Class<?>> primToWrap = new HashMap<Class<?>, Class<?>>(16);
        Map<Class<?>, Class<?>> wrapToPrim = new HashMap<Class<?>, Class<?>>(16);

        add(primToWrap, wrapToPrim, boolean.class, Boolean.class);
        add(primToWrap, wrapToPrim, byte.class, Byte.class);
        add(primToWrap, wrapToPrim, char.class, Character.class);
        add(primToWrap, wrapToPrim, double.class, Double.class);
        add(primToWrap, wrapToPrim, float.class, Float.class);
        add(primToWrap, wrapToPrim, int.class, Integer.class);
        add(primToWrap, wrapToPrim, long.class, Long.class);
        add(primToWrap, wrapToPrim, short.class, Short.class);
        add(primToWrap, wrapToPrim, void.class, Void.class);

        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(wrapToPrim);
    }

    private static void add(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
        forward.put(key, value);
        backward.put(value, key);
    }

    public static <T> Class<T> wrap(Class<T> type) {
        Class<T> wrapped = (Class<T>) PRIMITIVE_TO_WRAPPER_TYPE.get(type);
        return (wrapped == null) ? type : wrapped;
    }

    public static <T> Class<T> unwrap(Class<T> type) {
        Class<T> unwrapped = (Class<T>) WRAPPER_TO_PRIMITIVE_TYPE.get(type);
        return (unwrapped == null) ? type : unwrapped;
    }

    private final static Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
    private final static Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
    private final static Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
    private final static Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
    private final static Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
    private final static Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
    private final static Type LONG_TYPE = Type.getObjectType("java/lang/Long");
    private final static Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
    private final static Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
    private final static Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");

    private static final String BOOLEAN_VALUE = "booleanValue";
    private static final String CHAR_VALUE = "charValue";
    private static final String INT_VALUE = "intValue";
    private static final String FLOAT_VALUE = "floatValue";
    private static final String LONG_VALUE = "longValue";
    private static final String DOUBLE_VALUE = "doubleValue";

    private static final Map<Type, String> DESCRIPTORS = new HashMap<Type, String>() {
        {
            put(BYTE_TYPE, "B");
            put(BOOLEAN_TYPE, "Z");
            put(SHORT_TYPE, "S");
            put(CHARACTER_TYPE, "C");
            put(INTEGER_TYPE, "I");
            put(FLOAT_TYPE, "F");
            put(LONG_TYPE, "J");
            put(DOUBLE_TYPE, "D");
        }
    };

    private MethodVisitor methodVisitor;

    public Boxer(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void box(Type type) {
        if (type.getSort() == Type.ARRAY || type.getSort() == Type.OBJECT)
            return;

        if (type == Type.VOID_TYPE) {
            this.methodVisitor.visitInsn(ACONST_NULL);
        } else {
            Type asBoxed = type;

            switch (type.getSort()) {
                case Type.BYTE:
                    asBoxed = BYTE_TYPE;
                    break;
                case Type.BOOLEAN:
                    asBoxed = BOOLEAN_TYPE;
                    break;
                case Type.SHORT:
                    asBoxed = SHORT_TYPE;
                    break;
                case Type.CHAR:
                    asBoxed = CHARACTER_TYPE;
                    break;
                case Type.INT:
                    asBoxed = INTEGER_TYPE;
                    break;
                case Type.FLOAT:
                    asBoxed = FLOAT_TYPE;
                    break;
                case Type.LONG:
                    asBoxed = LONG_TYPE;
                    break;
                case Type.DOUBLE:
                    asBoxed = DOUBLE_TYPE;
                    break;
            }

            insertValueOf(asBoxed);
        }
    }

    private void insertValueOf(Type boxedType) {
        this.methodVisitor.visitMethodInsn(INVOKESTATIC, boxedType.getInternalName(), "valueOf", "(" + DESCRIPTORS.get(boxedType) + ")" + boxedType.getDescriptor());
    }

    public void unbox(Type type) {
        Type boxedType = NUMBER_TYPE;
        String methodName = null;

        switch (type.getSort()) {
            case Type.VOID:
                return;
            case Type.BOOLEAN:
                boxedType = BOOLEAN_TYPE;
                methodName = BOOLEAN_VALUE;
                break;
            case Type.CHAR:
                boxedType = CHARACTER_TYPE;
                methodName = CHAR_VALUE;
                break;
            case Type.FLOAT:
                boxedType = FLOAT_TYPE;
                methodName = FLOAT_VALUE;
                break;
            case Type.LONG:
                boxedType = LONG_TYPE;
                methodName = LONG_VALUE;
                break;
            case Type.DOUBLE:
                boxedType = DOUBLE_TYPE;
                methodName = DOUBLE_VALUE;
                break;
            case Type.SHORT:
                boxedType = SHORT_TYPE;
                methodName = INT_VALUE;
                break;
            case Type.INT:
                boxedType = INTEGER_TYPE;
                methodName = INT_VALUE;
                break;
            case Type.BYTE:
                boxedType = BYTE_TYPE;
                methodName = INT_VALUE;
                break;
        }

        if (methodName != null) {
            insertCheckCast(boxedType);
            insertInvokeVirtual(boxedType, methodName);
        } else {
            insertCheckCast(type);
        }
    }

    private void insertCheckCast(Type type) {
        if (type.equals(OBJECT_TYPE))
            return;

        String signature;
        if (type.getSort() == Type.ARRAY) {
            signature = type.getDescriptor();
        } else {
            signature = type.getInternalName();
        }

        this.methodVisitor.visitTypeInsn(CHECKCAST, signature);
    }

    private void insertInvokeVirtual(Type type, String methodName) {
        this.methodVisitor.visitMethodInsn(INVOKEVIRTUAL, type.getSort() == Type.ARRAY ? type.getDescriptor() : type.getInternalName(), methodName, "()" + DESCRIPTORS.get(type), false);
    }
}
