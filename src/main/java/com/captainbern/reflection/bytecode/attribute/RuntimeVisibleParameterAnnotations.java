package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.attribute.annotation.ParameterAnnotation;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeVisibleParameterAnnotations extends ParameterAnnotation {

    public RuntimeVisibleParameterAnnotations(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        super(ATTR_RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS, index, length, codeStream, constantPool);
    }
}
