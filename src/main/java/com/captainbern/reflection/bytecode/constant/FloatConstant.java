package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public class FloatConstant extends Constant {

    private float cfloat;

    public FloatConstant(FloatConstant constant) {
        this(constant.getFloat());
    }

    public FloatConstant(DataInput stream) throws IOException {
        this(stream.readFloat());
    }

    public FloatConstant(float cfloat) {
        super(CONSTANT_Float);
        this.cfloat = cfloat;
    }

    public float getFloat() {
        return this.cfloat;
    }
}
