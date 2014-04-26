package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.attribute.annotation.ElementValue;

import java.io.DataInputStream;
import java.io.IOException;

public class AnnotationDefault extends Attribute implements Opcode {

    private ElementValue defaultValue;

    public AnnotationDefault(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (ElementValue) null, constantPool);
        this.defaultValue = ElementValue.read(codeStream, constantPool);
    }

    public AnnotationDefault(int index, int length, ElementValue defaultValue, ConstantPool constantPool) {
        super(ATTR_ANNOTATION_DEFAULT, index, length, constantPool);
        this.defaultValue = defaultValue;
    }

    public final ElementValue getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(ElementValue defaultValue) {
        this.defaultValue = defaultValue;
    }
}
