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
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a LocalVariableTypeTable attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.14">LocalVariableTypeTable</a>
 */
public class LocalVariableTypeTable extends Attribute implements Opcode {

    private int localVariableTypeTableLength;
    private LocalVariable[] variables;

    public LocalVariableTypeTable(LocalVariableTypeTable localVariableTypeTable) {
        this(localVariableTypeTable.getNameIndex(), localVariableTypeTable.getLength(), localVariableTypeTable.getVariables(), localVariableTypeTable.getConstantPool());
    }

    public LocalVariableTypeTable(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (LocalVariable[]) null, constantPool);
        this.localVariableTypeTableLength = codeStream.readUnsignedShort();
        this.variables = new LocalVariable[this.localVariableTypeTableLength];
        for(int i = 0; i < this.localVariableTypeTableLength; i++) {
            this.variables[i] = new LocalVariable(codeStream);
        }
    }

    public LocalVariableTypeTable(int index, int length, LocalVariable[] variables, ConstantPool constantPool) {
        super(ATTR_LOCAL_VARIABLE_TYPE_TABLE, index, length, constantPool);
        setVariables(variables);
    }

    public final int getLocalVariableTypeTableLength() {
        return this.localVariableTypeTableLength;
    }

    public final LocalVariable[] getVariables() {
        return this.variables;
    }

    public final void setVariables(LocalVariable[] variables) {
        this.localVariableTypeTableLength = variables == null ? 0: variables.length;
        this.variables = variables;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.localVariableTypeTableLength);
        for(int i = 0; i < this.localVariableTypeTableLength; i++) {
            this.variables[i].write(codeStream);
        }
    }
}
