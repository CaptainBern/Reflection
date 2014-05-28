package com.captainbern.jbel.util.code;

import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Code;

public class CodeIterator implements Opcode {

    protected final Code code;
    protected byte[] byteCode;

    protected int currentPosition;

    protected int size;

    public CodeIterator(final Code code) {
        this.code = code;
        this.byteCode = code.getCode();

        this.currentPosition = 0;
        this.size = this.byteCode.length;
    }

    public Code getCode() {
        return this.code;
    }

    public void move(int index) {
        if (index > this.size)
            throw new IndexOutOfBoundsException();

        this.currentPosition = index;
    }

    public int getByteAt(int index) {
        return this.byteCode[index] & 0xFF;
    }
}
