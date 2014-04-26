package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumberTable extends Attribute implements Opcode {

    private int lineNumberTableLength;
    private LineNumber[] lineNumbers;

    public LineNumberTable(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (LineNumber[]) null, constantPool);
        this.lineNumberTableLength = codeStream.readUnsignedShort();
        this.lineNumbers = new LineNumber[this.lineNumberTableLength];
        for(int i = 0; i < lineNumberTableLength; i++) {
            this.lineNumbers[i] = new LineNumber(codeStream);
        }
    }

    public LineNumberTable(int index, int length, LineNumber[] lineNumbers, ConstantPool constantPool) {
        super(ATTR_LINE_NUMBER_TABLE, index, length, constantPool);
        setLineNumbers(lineNumbers);
    }

    public final int getLineNumberTableLength() {
        return this.lineNumberTableLength;
    }

    public final LineNumber[] getLineNumbers() {
        return this.lineNumbers;
    }

    public final void setLineNumbers(LineNumber[] lineNumbers) {
        this.lineNumberTableLength = lineNumbers == null ? 0 : lineNumbers.length;
        this.lineNumbers = lineNumbers;
    }
}
