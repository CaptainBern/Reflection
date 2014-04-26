package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_Utf8;

public class Utf8Constant extends Constant {

    private String cstring;

    public Utf8Constant(Utf8Constant constant) {
        this(constant.getString());
    }

    public Utf8Constant(DataInput inputStream) throws IOException {
        this(inputStream.readUTF());
    }

    public Utf8Constant(String cstring) {
        super(CONSTANT_Utf8);
        this.cstring = cstring;
    }

    public String getString() {
        return this.cstring;
    }
}
