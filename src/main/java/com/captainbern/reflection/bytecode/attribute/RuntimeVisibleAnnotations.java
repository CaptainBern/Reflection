package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.attribute.annotation.AnnotationElementValue;

public class RuntimeVisibleAnnotations extends AnnotationElementValue {

    public RuntimeVisibleAnnotations(int index, ConstantPool constantPool, boolean isVisible) {
        super(index, constantPool, true);
    }
}
