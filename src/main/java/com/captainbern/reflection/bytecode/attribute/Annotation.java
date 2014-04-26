package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;

public class Annotation extends Attribute {

    private Annotation annotation;

    public Annotation(String tag, int index, int length, ConstantPool constantPool) {
        super(tag, index, length, constantPool);
    }
}
