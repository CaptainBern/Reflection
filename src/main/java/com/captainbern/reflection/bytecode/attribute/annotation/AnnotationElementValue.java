package com.captainbern.reflection.bytecode.attribute.annotation;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class AnnotationElementValue extends ElementValue {

    private int index;
    private ConstantPool constantPool;
    private LinkedList<ElementValuePair> members = new LinkedList<>();

    private boolean visible;

    public AnnotationElementValue(AnnotationElementValue elementValue, ConstantPool constantPool) {
        super(TYPE_ANNOTATION, constantPool);
    }

    public AnnotationElementValue(int index, ConstantPool constantPool, boolean isVisible) {
        super(TYPE_ANNOTATION, constantPool);
        this.index = index;
        this.constantPool = constantPool;
        this.visible = isVisible;
    }

    public final int getIndex() {
        return this.index;
    }

    public final boolean isRuntimeVisible() {
        return this.visible;
    }

    public final int getElementValuePairsSize() {
        return this.members.size();
    }

    public final ElementValuePair[] getElementValuePairs() {
        return this.members.toArray(new ElementValuePair[this.members.size()]);
    }

    public final void addElementValue(ElementValuePair elementValuePair) {
        this.members.add(elementValuePair);
    }

    public static AnnotationElementValue read(DataInputStream codeStream, ConstantPool constantPool, boolean visible) throws IOException {
        final AnnotationElementValue annotationElementValue = new AnnotationElementValue(codeStream.readUnsignedShort(), constantPool, visible);
        final int valuePairs = codeStream.readUnsignedShort();

        for(int i = 0; i < valuePairs; i++) {
            annotationElementValue.addElementValue(new ElementValuePair(codeStream.readUnsignedShort(), ElementValue.read(codeStream, constantPool), constantPool));
        }

        return annotationElementValue;
    }
}
