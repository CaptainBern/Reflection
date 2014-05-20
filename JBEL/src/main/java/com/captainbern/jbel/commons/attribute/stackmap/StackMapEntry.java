package com.captainbern.jbel.commons.attribute.stackmap;

import com.captainbern.jbel.ConstantPool;

public class StackMapEntry {

    private int offset;
    private int numberOfLocals;
    private TypeInfo[] locals;
    private int numberOfStackItems;
    private TypeInfo[] stack;
    private ConstantPool constantPool;


}
