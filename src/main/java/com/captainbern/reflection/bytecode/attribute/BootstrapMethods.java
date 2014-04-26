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

public class BootstrapMethods extends Attribute implements Opcode {

    private int bootstrapMethodCount;
    private BootstrapMethod[] bootstrapMethods;

    public BootstrapMethods(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (BootstrapMethod[]) null, constantPool);
        this.bootstrapMethodCount = codeStream.readUnsignedShort();
        this.bootstrapMethods = new BootstrapMethod[this.bootstrapMethodCount];
        for(int i = 0; i < this.bootstrapMethodCount; i++) {
            this.bootstrapMethods[i] = new BootstrapMethod(codeStream);
        }
    }

    public BootstrapMethods(int index, int length, BootstrapMethod[] bootstrapMethods, ConstantPool constantPool) {
        super(ATTR_BOOTSTRAP_METHODS, index, length, constantPool);
    }

    public final int getBootstrapMethodCount() {
        return this.bootstrapMethodCount;
    }

    public final BootstrapMethod[] getBootstrapMethods() {
        return this.bootstrapMethods;
    }

    public final void setBootstrapMethods(BootstrapMethod[] bootstrapMethods) {
        this.bootstrapMethodCount = bootstrapMethods == null ? 0 : bootstrapMethods.length;
        this.bootstrapMethods = bootstrapMethods;
    }
}
