package com.captainbern.jbel;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.attribute.Code;
import com.captainbern.jbel.commons.member.method.MethodInfo;

import java.io.PrintStream;

public class InstructionPrinter implements Opcode, Mnemonics {

    private PrintStream printStream;

    public InstructionPrinter() {
        this(System.out);
    }

    public InstructionPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printMethod(MethodInfo method) {
        ConstantPool constantPool = method.getConstantPool();
        Code code = (Code) Attribute.getAttribute(method.getAttributes(), "Code");
    }
}
