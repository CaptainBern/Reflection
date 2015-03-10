package com.captainbern.reflection.asm.accessor;

import com.captainbern.reflection.asm.Boxer;
import com.captainbern.reflection.asm.CompilerService;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class FieldAccessorBuilder extends AccessorBuilder<FieldAccessor> {

    protected static final String NO_SUCH_FIELD_EXCEPTION = getInternalName(NoSuchFieldException.class.getName());
    protected static final String ILLEGAL_ACCESS_EXCEPTION = getInternalName(IllegalAccessException.class.getName());

    protected FieldAccessorBuilder(Class<?> target) {
        super(target, AccessorType.FIELD);
    }

    @Override
    public FieldAccessor build(ClassLoader classLoader) {
        String targetClassType = getInternalName(this.getTarget().getName());

        injectConstructor(this.getClassWriter());
        injectGetTargetClass(this.getClassWriter(), this.getTarget());

        List<Field> fields = Arrays.asList(this.getTarget().getDeclaredFields());
        injectGet(this.getClassWriter(), targetClassType, fields);
        injectSet(this.getClassWriter(), targetClassType, fields);

        this.getClassWriter().visitEnd();

        Class<?> result = CompilerService.create(classLoader).defineClass(
                getExternalName(getAccessorNameInternal(this.getTarget(), this.getAccessorType())), // this is somewhat redundant but maybe in the future the class-name-format changes
                this.getClassWriter().toByteArray());

        return (FieldAccessor) instantiate(result);
    }

    protected boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    protected boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    protected void injectGet(ClassWriter classWriter, String targetClassName, List<Field> fields) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, new String[] { NO_SUCH_FIELD_EXCEPTION });

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

        injectInvalidFieldIndexException(methodVisitor);
        methodVisitor.visitMaxs(maxStack, 3);
        methodVisitor.visitEnd();
    }

    protected void injectReflectiveGetter(MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "getTargetClass", "()Ljava/lang/Class;", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredFields", "()[Ljava/lang/reflect/Field;", false);
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
        methodVisitor.visitInsn(ARETURN);
    }

    protected void injectSet(ClassWriter classWriter, String targetClassName, List<Field> fields) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", null, new String[] { ILLEGAL_ACCESS_EXCEPTION });

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
                String inputPath = inputType.getName().replace('.', '/');

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

        injectInvalidFieldIndexException(methodVisitor);
        methodVisitor.visitMaxs(maxStack, 4);
        methodVisitor.visitEnd();
    }

    protected void injectReflectiveSetter(MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, getAccessorNameInternal(this.getTarget(), this.getAccessorType()), "getTargetClass", "()Ljava/lang/Class;", false);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredFields", "()[Ljava/lang/reflect/Field;", false);
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitVarInsn(ASTORE, 5);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
        methodVisitor.visitInsn(RETURN);
    }

    protected void injectInvalidFieldIndexException(MethodVisitor methodVisitor) {
        methodVisitor.visitTypeInsn(NEW, ILLEGAL_ACCESS_EXCEPTION);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn("Invalid index: ");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, ILLEGAL_ACCESS_EXCEPTION, "<init>", "(Ljava/lang/String;)V");
        methodVisitor.visitInsn(ATHROW);
    }
}
