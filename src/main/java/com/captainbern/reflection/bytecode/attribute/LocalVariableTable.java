package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

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

    public final int getLocalVariableTableLength() {
        return this.localVariableTableLength;
    }

    public final LocalVariable[] getVariables() {
        return this.variables;
    }

    public final void setVariables(LocalVariable[] variables) {
        this.localVariableTableLength = variables == null ? 0: variables.length;
        this.variables = variables;
    }
}
