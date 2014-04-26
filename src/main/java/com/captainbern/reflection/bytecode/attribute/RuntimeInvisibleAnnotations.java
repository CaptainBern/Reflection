package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.attribute.annotation.AnnotationElementValue;

public class RuntimeInvisibleAnnotations extends AnnotationElementValue {

    public RuntimeInvisibleAnnotations(int index, ConstantPool constantPool) {
        super(index, constantPool, false);
    }
}
