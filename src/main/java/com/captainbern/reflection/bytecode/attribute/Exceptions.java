package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class Exceptions extends Attribute implements Opcode {

    private int exceptionCount;
    private int[] exceptions;

    public Exceptions(Exceptions exceptions) {
        this(exceptions.getNameIndex(), exceptions.getLength(), exceptions.getExceptions(), exceptions.getConstantPool());
    }

    public Exceptions(int index, int count, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, count, (int[]) null, constantPool);
        this.exceptionCount = codeStream.readUnsignedShort();
        this.exceptions = new int[this.exceptionCount];
        for (int i = 0; i < exceptionCount; i++) {
            this.exceptions[i] = codeStream.readUnsignedShort();
        }
    }

    public Exceptions(int index, int length, int[] exceptions, ConstantPool constantPool) {
        super(ATTR_EXCEPTIONS, index, length, constantPool);
        setExceptions(exceptions);
    }

    public final int getExceptionCount() {
        return this.exceptionCount;
    }

    public final int[] getExceptions() {
        return this.exceptions;
    }

    public final void setExceptions(int[] exceptions) {
        this.exceptions = exceptions;
        this.exceptionCount = exceptions == null ? 0 : exceptions.length;
    }

    public final String[] getExceptionsAsString() throws ClassFormatException {
        String[] names = new String[this.exceptionCount];
        for(int i = 0; i < this.exceptionCount; i++) {
            names[i] = this.constantPool.getConstantString(exceptions[i], CONSTANT_Class);
        }
        return names;
    }
}
