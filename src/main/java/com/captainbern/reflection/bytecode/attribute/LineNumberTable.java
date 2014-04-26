/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
