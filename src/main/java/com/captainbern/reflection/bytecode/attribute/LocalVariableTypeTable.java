package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

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
}
