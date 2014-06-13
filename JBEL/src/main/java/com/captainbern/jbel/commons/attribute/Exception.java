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

package com.captainbern.jbel.commons.attribute;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Exception {

    private int startPc;
    private int endPc;
    private int handlerPc;
    private int catchType;

    public Exception(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort(), codeStream.readUnsignedShort());
    }

    public Exception(int startPc, int endPc, int handlerPc, int catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }

    public int getStartPC() {
        return this.startPc;
    }

    public void setStartPC(int startPC) {
        this.startPc = startPC;
    }

    public int getEndCP() {
        return this.endPc;
    }

    public void setEndPC(int endPC) {
        this.endPc = endPC;
    }

    public int getHandlerPC() {
        return this.handlerPc;
    }

    public void setHandlerPC(int handlerPC) {
        this.handlerPc = handlerPC;
    }

    public int getCatchType() {
        return this.catchType;
    }

    public void setCatchType(int catchType) {
        this.catchType = catchType;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.startPc);
        codeStream.writeShort(this.endPc);
        codeStream.writeShort(this.handlerPc);
        codeStream.writeShort(this.catchType);
    }
}
