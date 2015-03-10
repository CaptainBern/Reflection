package com.captainbern.reflection.asm.accessor;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public abstract class AccessorBuilder<T> {

    private ClassWriter classWriter;

    private final Class<?> target;
    private final AccessorType accessorType;

    protected AccessorBuilder(Class<?> target, AccessorType accessorType) {
        this.target = target;
        this.accessorType = accessorType;
        this.classWriter = createClassWriter(getAccessorNameInternal(this.getTarget(), this.getAccessorType()), this.accessorType);
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }

    public Class<?> getTarget() {
        return this.target;
    }

    public AccessorType getAccessorType() {
        return this.accessorType;
    }

    public T build() {
        return this.build(ClassLoader.getSystemClassLoader());
    }

    public abstract T build(ClassLoader classLoader);

    public static ConstructorAccessorBuilder newConstructorAccessorBuilder(Class<?> clazz) {
        return new ConstructorAccessorBuilder(clazz);
    }

    public static FieldAccessorBuilder newFieldAccessorBuilder(Class<?> clazz) {
        return new FieldAccessorBuilder(clazz);
    }

    public static MethodAccessorBuilder newMethodAccessorBuilder(Class<?> clazz) {
        return new MethodAccessorBuilder(clazz);
    }

    /*
     * Utility methods
     */

    protected static Object instantiate(Class<?> type) {
        Object returnValue;

        try {
            returnValue = type.newInstance();
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to instantiate a new instance of: \'" + type.getName() + "\'", throwable);
        }

        return returnValue;
    }

    protected static ClassWriter createClassWriter(String internalName, AccessorType accessorType) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(V1_6, ACC_PUBLIC + ACC_SUPER, internalName, null, "java/lang/Object", new String[]{ getInternalName(accessorType.getAccessorType().getName()) });
        classWriter.visitSource(getExternalName(internalName), null);
        return classWriter;
    }

    protected static void injectConstructor(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    protected static void injectGetTargetClass(ClassWriter classWriter, Class<?> targetClass) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "getTargetClass", "()Ljava/lang/Class;", "()Ljava/lang/Class<*>;", null);
        methodVisitor.visitCode();
        methodVisitor.visitLdcInsn(Type.getType(targetClass));
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    protected static String toSafeName(Class<?> clazz) {
        return clazz.getCanonicalName().replace("[]", "Array").replace(".", "_");
    }

    protected static String getAccessorNameInternal(Class<?> clazz, AccessorType accessorType) {
        String typeName = toSafeName(clazz);

        return getExternalName(typeName + "$Accessor$" + accessorType.name().toLowerCase());
    }

    protected static String getInternalName(String externalName) {
        return externalName.replace(".", "/");
    }

    protected static String getExternalName(String internalName) {
        return internalName.replace("/", ".");
    }
}
