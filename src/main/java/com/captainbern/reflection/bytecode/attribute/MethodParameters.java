package com.captainbern.reflection.bytecode.attribute;

import com.captainbern.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MethodParameters extends Attribute {

    private int parameterCount;
    private MethodParameter[] parameters;

    public MethodParameters(int index, int length, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(index, length, (MethodParameter[]) null, constantPool);
        this.parameterCount = codeStream.readUnsignedShort();
        this.parameters = new MethodParameter[this.parameterCount];
        for(int i = 0; i < this.parameterCount; i++) {
            this.parameters[i] = new MethodParameter(codeStream);
        }
    }

    public MethodParameters(int index, int length, MethodParameter[] parameters, ConstantPool constantPool) {
        super(ATTR_METHOD_PARAMETERS, index, length, constantPool);
        setParameters(parameters);
    }

    public final int getParameterCount() {
        return this.parameterCount;
    }

    public final MethodParameter[] getParameters() {
        return this.parameters;
    }

    public final void setParameters(MethodParameter[] parameters) {
        this.parameters = parameters;
        this.parameterCount = parameters == null ? 0 : parameters.length;
    }

    @Override
    public void write(DataOutputStream codeStream) throws IOException {
        super.write(codeStream);
        codeStream.writeShort(this.parameterCount);
        for(int i = 0; i < this.parameterCount; i++) {
            this.parameters[i].write(codeStream);
        }
    }
}
