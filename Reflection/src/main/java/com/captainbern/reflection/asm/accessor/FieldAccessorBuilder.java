package com.captainbern.reflection.asm.accessor;

import com.captainbern.reflection.asm.Boxer;
import com.captainbern.reflection.asm.CompilerService;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class FieldAccessorBuilder extends AccessorBuilder<FieldAccessor> {

    protected static final Class ILLEGAL_ACCESS_EXCEPTION = IllegalAccessException.class;

    protected FieldAccessorBuilder(Class<?> target) {
        super(target, AccessorType.FIELD);
    }

    @Override
    public FieldAccessor build(ClassLoader classLoader) {
        String targetClassType = getInternalName(this.getTarget().getName());

        ClassWriter classWriter = this.getClassWriter();

        injectFieldTable(classWriter);
        injectConstructor(classWriter);
        injectGetTargetClass(classWriter, this.getTarget());

        List<Field> fields = new ArrayList<>();
        Class<?> current = this.getTarget();
        while (current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                fields.add(field);
            }
            current = current.getSuperclass();
        }

        injectGetByIndex(classWriter, targetClassType, fields);
        injectSetByIndex(classWriter, targetClassType, fields);

        injectGetByName(classWriter);
        injectSetByName(classWriter);

        injectGetFieldTable(classWriter);
        injectGetIndex(classWriter);

        classWriter.visitEnd();

        Class<?> result = CompilerService.create(classLoader).defineClass(
                getExternalName(getAccessorNameInternal(this.getTarget(), this.getAccessorType())), // this is somewhat redundant but maybe in the future the class-name-format changes
                this.getClassWriter().toByteArray());

        return (FieldAccessor) instantiate(result);
    }

    protected void injectFieldTable(ClassWriter classWriter) {
        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PRIVATE + ACC_FINAL, "fieldTable", "[Ljava/lang/reflect/Field;", null, null);
        fieldVisitor.visitEnd();
    }

    protected void injectConstructor(ClassWriter classWriter) {
        String resultName = getAccessorNameInternal(this.getTarget(), this.getAccessorType());

        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);;
        methodVisitor.visitTypeInsn(NEW, "java/util/ArrayList");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, 1);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, resultName, "getTargetClass", "()Ljava/lang/Class;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        Label whileLoop = new Label(); // enter the "while (currentClass != Object.class)" loop
        methodVisitor.visitLabel(whileLoop);
        methodVisitor.visitFrame(Opcodes.F_FULL, 3, new Object[]{resultName, "java/util/List", "java/lang/Class"}, 0, new Object[]{});
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitLdcInsn(Type.getType("Ljava/lang/Object;"));
        Label classNotObjectComparer = new Label(); // compares the current class to Object.class aka currentClass != Object.class
        methodVisitor.visitJumpInsn(IF_ACMPEQ, classNotObjectComparer);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredFields", "()[Ljava/lang/reflect/Field;", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ARRAYLENGTH);
        methodVisitor.visitVarInsn(ISTORE, 4);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 5);
        Label cachingForLoop = new Label(); // this loop handles the storing of the fields (to the list)
        methodVisitor.visitLabel(cachingForLoop);
        methodVisitor.visitFrame(Opcodes.F_APPEND, 3, new Object[]{"[Ljava/lang/reflect/Field;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label cachingForLoopIncrementLabel = new Label(); // handles the looping of the fields
        methodVisitor.visitJumpInsn(IF_ICMPGE, cachingForLoopIncrementLabel);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitVarInsn(ASTORE, 6);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        methodVisitor.visitInsn(POP);
        methodVisitor.visitIincInsn(5, 1);
        methodVisitor.visitJumpInsn(GOTO, cachingForLoop);
        methodVisitor.visitLabel(cachingForLoopIncrementLabel);
        methodVisitor.visitFrame(Opcodes.F_CHOP, 3, null, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSuperclass", "()Ljava/lang/Class;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        methodVisitor.visitJumpInsn(GOTO, whileLoop);
        methodVisitor.visitLabel(classNotObjectComparer);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "size", "()I", true);
        methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/reflect/Field");
        methodVisitor.visitFieldInsn(PUTFIELD, resultName, "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 3);
        Label storingForLoop = new Label(); // this for loop goes about storing the fields in the array defined in the class
        methodVisitor.visitLabel(storingForLoop);
        methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, resultName, "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitInsn(ARRAYLENGTH);
        Label storage = new Label(); // gets the field at position x and stores it in the cache (fieldTable)
        methodVisitor.visitJumpInsn(IF_ICMPGE, storage);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, resultName, "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitVarInsn(ILOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ILOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
        methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/reflect/Field");
        methodVisitor.visitInsn(AASTORE);
        methodVisitor.visitIincInsn(3, 1);
        methodVisitor.visitJumpInsn(GOTO, storingForLoop);
        methodVisitor.visitLabel(storage);
        methodVisitor.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(4, 7);
        methodVisitor.visitEnd();
    }

    protected void injectGetByIndex(ClassWriter classWriter, String targetClassName, List<Field> fields) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, new String[] { getInternalName(ILLEGAL_ACCESS_EXCEPTION.getCanonicalName()) });

        Boxer boxer = new Boxer(methodVisitor);

        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ILOAD, 2);

        int maxStack = 6;

        Label[] labels = new Label[fields.size()];
        Label errorLabel = new Label();

        for (int i = 0; i < fields.size(); i++) {
            labels[i] = new Label();
        }

        methodVisitor.visitTableSwitchInsn(0, labels.length - 1, errorLabel, labels);

        if (!fields.isEmpty()) {
            maxStack--;

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                Class<?> type = field.getType();
                String fieldDescriptor = Type.getDescriptor(type);

                methodVisitor.visitLabel(labels[i]);

                if (i == 0)
                    methodVisitor.visitFrame(F_APPEND, 1, new Object[] { targetClassName }, 0, null);
                else
                    methodVisitor.visitFrame(F_SAME, 0, null, 0, null);

                if (isPublic(field)) {
                    methodVisitor.visitVarInsn(ALOAD, 1);
                    methodVisitor.visitTypeInsn(CHECKCAST, targetClassName);
                    methodVisitor.visitFieldInsn(GETFIELD, targetClassName, field.getName(), fieldDescriptor);

                    boxer.box(Type.getType(type));
                } else {
                    injectReflectiveGetter(methodVisitor);
                }

                methodVisitor.visitInsn(ARETURN);
            }

            methodVisitor.visitLabel(errorLabel);
            methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        }

        injectException(methodVisitor, IllegalAccessException.class);
        methodVisitor.visitMaxs(maxStack, 3);
        methodVisitor.visitEnd();
    }

    protected void injectReflectiveGetter(MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
        methodVisitor.visitInsn(ARETURN);
    }

    protected void injectGetByName(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", null, new String[]{ "java/lang/IllegalAccessException" });
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "getIndex", "(Ljava/lang/String;)I", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(4, 3);
        methodVisitor.visitEnd();
    }

    protected void injectSetByIndex(ClassWriter classWriter, String targetClassName, List<Field> fields) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", null, new String[] { getInternalName(ILLEGAL_ACCESS_EXCEPTION.getCanonicalName()) });

        Boxer boxer = new Boxer(methodVisitor);

        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ILOAD, 2);

        int maxStack = 6;

        Label[] labels = new Label[fields.size()];
        Label errorLabel = new Label();

        for (int i = 0; i < fields.size(); i++) {
            labels[i] = new Label();
        }

        methodVisitor.visitTableSwitchInsn(0, labels  .length - 1, errorLabel, labels);

        if (!fields.isEmpty()) {
            maxStack--;

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                Class<?> type = field.getType();
                Class<?> inputType = Boxer.wrap(type);
                String fieldDescriptor = Type.getDescriptor(type);
                String inputPath = getInternalName(inputType.getName());

                methodVisitor.visitLabel(labels[i]);

                if (i == 0)
                    methodVisitor.visitFrame(F_APPEND, 1, new Object[] { targetClassName }, 0, null);
                else
                    methodVisitor.visitFrame(F_SAME, 0, null, 0, null);

                if (isPublic(field) && !isFinal(field)) {
                    methodVisitor.visitVarInsn(ALOAD, 1);
                    methodVisitor.visitTypeInsn(CHECKCAST, targetClassName);
                    methodVisitor.visitVarInsn(ALOAD, 3);

                    if (!type.isPrimitive()) {
                        methodVisitor.visitTypeInsn(CHECKCAST, inputPath);
                    } else {
                        boxer.unbox(Type.getType(type));
                    }

                    methodVisitor.visitFieldInsn(PUTFIELD, targetClassName, field.getName(), fieldDescriptor);
                } else {
                    injectReflectiveSetter(methodVisitor);
                }

                methodVisitor.visitInsn(RETURN);
            }

            methodVisitor.visitLabel(errorLabel);
            methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        }

        injectException(methodVisitor, IllegalAccessException.class);
        methodVisitor.visitMaxs(maxStack, 4);
        methodVisitor.visitEnd();
    }

    protected void injectReflectiveSetter(MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
        methodVisitor.visitInsn(RETURN);
    }

    protected void injectSetByName(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", null, new String[]{ "java/lang/IllegalAccessException" });
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "getIndex", "(Ljava/lang/String;)I", false);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(4, 4);
        methodVisitor.visitEnd();
    }

    protected void injectException(MethodVisitor methodVisitor, Class<? extends Throwable> throwable) {
        methodVisitor.visitTypeInsn(NEW, getInternalName(throwable.getCanonicalName()));
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn("Invalid index: ");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, getInternalName(throwable.getCanonicalName()), "<init>", "(Ljava/lang/String;)V");
        methodVisitor.visitInsn(ATHROW);
    }

    protected void injectGetFieldTable(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "getFieldTable", "()[Ljava/lang/reflect/Field;", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "fieldTable", "[Ljava/lang/reflect/Field;");
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    protected void injectGetIndex(ClassWriter classWriter) {
        MethodVisitor mv = classWriter.visitMethod(ACC_PRIVATE, "getIndex", "(Ljava/lang/String;)I", null, null);
        mv.visitCode();
        mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ISTORE, 2);
        Label forLoopLabel = new Label();
        mv.visitLabel(forLoopLabel);
        mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(ILOAD, 2);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "fieldTable", "[Ljava/lang/reflect/Field;");
        mv.visitInsn(ARRAYLENGTH);
        Label forLoopIncrementLabel = new Label();
        mv.visitJumpInsn(IF_ICMPGE, forLoopIncrementLabel);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "fieldTable", "[Ljava/lang/reflect/Field;");
        mv.visitVarInsn(ILOAD, 2);
        mv.visitInsn(AALOAD);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "getName", "()Ljava/lang/String;", false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        Label equalsLabel = new Label();
        mv.visitJumpInsn(IFEQ, equalsLabel);
        mv.visitVarInsn(ILOAD, 2);
        mv.visitInsn(IRETURN);
        mv.visitLabel(equalsLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitIincInsn(2, 1);
        mv.visitJumpInsn(GOTO, forLoopLabel);
        mv.visitLabel(forLoopIncrementLabel);
        mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
        mv.visitInsn(ICONST_M1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 3);
        mv.visitEnd();
    }
}
