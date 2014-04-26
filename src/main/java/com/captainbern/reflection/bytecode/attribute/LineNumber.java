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

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumber {

    private int startPC;
    private int lineNumber;

    public LineNumber(LineNumber lineNumber) {
        this(lineNumber.getStartPC(), lineNumber.getLineNumber());
    }

    public LineNumber(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public LineNumber(int startPC, int lineNumber) {
        this.startPC = startPC;
        this.lineNumber = lineNumber;
    }

    public final int getStartPC() {
        return this.startPC;
    }

    public final void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public final int getLineNumber() {
        return this.lineNumber;
    }

    public final void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
