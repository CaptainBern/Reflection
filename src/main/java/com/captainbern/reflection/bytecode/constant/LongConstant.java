package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.reflection.bytecode.Opcode.CONSTANT_Long;

public class LongConstant extends Constant {

    private long clong;

    public LongConstant(LongConstant constant) {
        this(constant.getLong());
    }

    public LongConstant(DataInput stream) throws IOException {
        this(stream.readLong());
    }

    public LongConstant(long clong) {
        super(CONSTANT_Long);
        this.clong = clong;
    }

    public long getLong() {
        return this.clong;
    }
}
