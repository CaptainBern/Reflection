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

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a LocalVariableTable attribute.
 *
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.13">LocalVariableTable</a>
 */
public class LocalVariableTable extends Attribute implements Opcode {

    private int localVariableTableLength;
    private LocalVariable[] variables;

    public LocalVariableTable(LocalVariableTable localVariableTable) {
        this(localVariableTable.getNameIndex(), localVariableTable.getLength(), localVariableTable.getVariables(), localVariableTable.getConstantPool());
    }

    public LocalVariableTable(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (LocalVariable[]) null, constantPool);
        this.localVariableTableLength = codeStream.readUnsignedShort();
        this.variables = new LocalVariable[this.localVariableTableLength];
        for(int i = 0; i < this.localVariableTableLength; i++) {
            this.variables[i] = new LocalVariable(codeStream);
        }
    }

    public LocalVariableTable(int index, int length, LocalVariable[] variables, ConstantPool constantPool) {
        super(ATTR_LOCAL_VARIABLE_TABLE, index, length, constantPool);
        setVariables(variables);
    }

    public int getLocalVariableTableLength() {
        return this.localVariableTableLength;
    }

    public LocalVariable[] getVariables() {
        return this.variables;
    }

    public void setVariables(LocalVariable[] variables) {
        this.localVariableTableLength = variables == null ? 0: variables.length;
        this.variables = variables;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.localVariableTableLength);
        for(int i = 0; i < this.localVariableTableLength; i++) {
            this.variables[i].write(codeStream);
        }
    }
}
