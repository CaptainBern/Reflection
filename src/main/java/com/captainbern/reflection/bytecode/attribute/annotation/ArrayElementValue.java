package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;

public class ArrayElementValue extends ElementValue {

    private ElementValue[] values;

    public ArrayElementValue(ArrayElementValue arrayElementValue) {
        this(arrayElementValue.getValues(), arrayElementValue.getConstantPool());
    }

    public ArrayElementValue(ElementValue[] elementValues, ConstantPool constantPool) {
        super(TYPE_ARRAY, constantPool);
        this.values = elementValues;
    }

    public final int size() {
        return this.values.length;
    }

    public final ElementValue[] getValues() {
        return this.values;
    }

    public static ArrayElementValue read(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        final int size = codeStream.readUnsignedShort();
        ElementValue[] array = new ElementValue[size];
        for(int i = 0; i < size; i++) {
            array[i] = ElementValue.read(codeStream, constantPool);
        }
        return new ArrayElementValue(array, constantPool);
    }
}
