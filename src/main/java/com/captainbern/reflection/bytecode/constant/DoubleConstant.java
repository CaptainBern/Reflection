package com.captainbern.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

public class DoubleConstant extends Constant {

    private double cdouble;

    public DoubleConstant(DoubleConstant constant) {
        this(constant.getDouble());
    }

    public DoubleConstant(DataInput stream) throws IOException {
        this(stream.readDouble());
    }

    public DoubleConstant(double cdouble) {
        super(CONSTANT_Double);
        this.cdouble = cdouble;
    }

    public double getDouble() {
        return this.cdouble;
    }
}
