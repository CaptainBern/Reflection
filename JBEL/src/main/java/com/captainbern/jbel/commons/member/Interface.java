package com.captainbern.jbel.commons.member;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.constant.ClassConstant;
import com.captainbern.jbel.commons.constant.Utf8Constant;
import com.captainbern.jbel.commons.exception.ClassFormatException;

public class Interface implements Opcode {

    private int index;
    private ConstantPool constantPool;

    public Interface(int index, ConstantPool constantPool) {
         this.index = index;
        this.constantPool = constantPool;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() throws ClassFormatException {
        return this.constantPool.getUtf8(this.constantPool.getClass(this.index).getNameIndex()).getString();
    }

    public void setName(String name) throws ClassFormatException {
        this.constantPool.setConstant(((ClassConstant) this.constantPool.getConstant(this.index, CONSTANT_Class)).getNameIndex(), new Utf8Constant(name));
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
}
