package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_String;

public class StringConstant extends Constant {

    private int cstring;

    public StringConstant(StringConstant constant) {
        this(constant.getStringIndex());
    }

    public StringConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public StringConstant(int cstring) {
        super(CONSTANT_String);
        this.cstring = cstring;
    }

    public int getStringIndex() {
        return this.cstring;
    }
}
