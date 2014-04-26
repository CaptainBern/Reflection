package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

public class ElementValuePair {

    private ElementValue elementValue;
    private ConstantPool constantPool;
    private int nameIndex;

    public ElementValuePair(int nameIndex, ElementValue elementValue, ConstantPool constantPool) {
        this.nameIndex = nameIndex;
        this.elementValue = elementValue;
        this.constantPool = constantPool;
    }

    public final ElementValue getElementValue() {
        return this.elementValue;
    }

    public final void setElementValue(ElementValue elementValue) {
        this.elementValue = elementValue;
    }

    public final int getNameIndex() {
        return this.nameIndex;
    }

    public final void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public final String getName() throws ClassFormatException {
        return this.constantPool.getUtf8StringConstant(this.nameIndex);
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}

