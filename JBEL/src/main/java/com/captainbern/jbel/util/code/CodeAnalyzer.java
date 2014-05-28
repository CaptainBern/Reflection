package com.captainbern.jbel.util.code;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.commons.attribute.Code;

public class CodeAnalyzer {

    private final ConstantPool constantPool;
    private final Code code;

    public CodeAnalyzer(final Code code) {
        this.constantPool = code.getConstantPool();
        this.code = code;
    }
}
