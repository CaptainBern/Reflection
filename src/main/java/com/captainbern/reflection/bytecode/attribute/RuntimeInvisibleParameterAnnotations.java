package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.attribute.annotation.ParameterAnnotation;

import java.io.DataInputStream;
import java.io.IOException;

public class RuntimeInvisibleParameterAnnotations extends ParameterAnnotation implements Opcode {

    public RuntimeInvisibleParameterAnnotations(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        super(ATTR_RUNTIME_IN_VISIBLE_PARAMETER_ANNOTATIONS, index, length, codeStream, constantPool);
    }
}
