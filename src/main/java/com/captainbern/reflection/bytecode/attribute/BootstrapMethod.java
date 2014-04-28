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
import java.io.DataOutputStream;
import java.io.IOException;

public class BootstrapMethod {

    private int methodIndex;
    private int numberOfArguments;
    private int[] arguments;

    public BootstrapMethod(DataInputStream codeStream) throws IOException {
        this.methodIndex = codeStream.readUnsignedShort();
        this.numberOfArguments = codeStream.readUnsignedShort();
        this.arguments = new int[this.numberOfArguments];
        for(int i = 0; i < this.numberOfArguments; i++) {
            this.arguments[i] = codeStream.readUnsignedShort();
        }
    }

    public BootstrapMethod(int index, int numberOfArguments, int[] arguments) {
        this.methodIndex = index;
        this.numberOfArguments = numberOfArguments;
        this.arguments = arguments;
    }

    public final int getMethodIndex() {
        return this.methodIndex;
    }

    public final void setMethodIndex(int methodIndex) {
        this.methodIndex = methodIndex;
    }

    public final int getNumberOfArguments() {
        return this.numberOfArguments;
    }

    public final int[] getArguments() {
        return this.arguments;
    }

    public final void setArguments(int[] arguments) {
        this.numberOfArguments = arguments == null ? 0 : arguments.length;
        this.arguments = arguments;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.methodIndex);
        codeStream.writeShort(this.numberOfArguments);
        for(int i = 0; i < this.numberOfArguments; i++) {
            codeStream.writeShort(this.arguments[i]);
        }
    }
}
