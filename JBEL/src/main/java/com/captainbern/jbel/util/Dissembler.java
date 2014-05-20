package com.captainbern.jbel.util;

import com.captainbern.jbel.ClassReader;
import com.captainbern.jbel.util.instruction.Instruction;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.LinkedList;
import java.util.List;

public class Dissembler {

    public List<Instruction> dissemble(ClassReader classReader, byte[] code) {
        LinkedList<Instruction> instructions = new LinkedList<>();

        int index;

        try {
            DataInputStream codeStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(code)));
            while(codeStream.available() > 0) {
                index = code.length - codeStream.available();
                byte opCode = codeStream.readByte();
            }
        } catch (Exception e) {

        }

        return instructions;
    }
}
