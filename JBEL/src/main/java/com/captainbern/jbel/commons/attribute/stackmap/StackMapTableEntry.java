/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.jbel.commons.attribute.stackmap;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * To see how the parsing works read: <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.4">StackMapFrame</a>
 */
public class StackMapTableEntry implements Opcode {

    private int frameType;
    private int offset;    /*delta offset*/
    private int numberOfLocals;
    private TypeInfo[] localFrames;
    private int numberOfStackItems;
    private TypeInfo[] stack;

    private ConstantPool constantPool;

    public StackMapTableEntry(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        this(codeStream.read(), -1, -1, null, -1, null, constantPool);

        if(isWithinBounds(this.frameType, SAME_FRAME, SAME_FRAME_MAX)) {
            this.offset = this.frameType - SAME_FRAME;
        } else if(isWithinBounds(this.frameType, SAME_LOCALS_1_STACK_ITEM, SAME_LOCALS_1_STACK_ITEM_MAX)) {
            this.offset = this.frameType - SAME_LOCALS_1_STACK_ITEM;
            this.numberOfStackItems = 1;
            this.stack = new TypeInfo[1];
            this.stack[0] = new TypeInfo(codeStream, constantPool);
        } else if(this.frameType == SAME_LOCALS_1_STACK_ITEM_EXTENDED) {
            this.offset = codeStream.readShort();
            this.numberOfStackItems = 1;
            this.stack = new TypeInfo[1];
            this.stack[0] = new TypeInfo(codeStream, constantPool);
        } else if(isWithinBounds(this.frameType, CHOP_FRAME, CHOP_FRAME_MAX)) {
            this.offset = codeStream.readShort();
        } else if(this.frameType == SAME_FRAME_EXTENDED) {
            this.offset = codeStream.readShort();
        } else if(isWithinBounds(this.frameType, APPEND_FRAME, APPEND_FRAME_MAX)) {
            this.offset = codeStream.readShort();
            this.numberOfLocals = this.frameType - 251;
            this.localFrames = new TypeInfo[this.numberOfLocals];
            for(int i = 0; i < this.numberOfLocals; i++) {
                this.localFrames[i] = new TypeInfo(codeStream, constantPool);
            }
        } else if(this.frameType == FULL_FRAME) {
            this.offset = codeStream.readShort();

            this.numberOfLocals = codeStream.readShort();
            this.localFrames = new TypeInfo[this.numberOfLocals];
            for(int i = 0; i < this.numberOfLocals; i++) {
                this.localFrames[i] = new TypeInfo(codeStream, constantPool);
            }

            this.numberOfStackItems = codeStream.readShort();
            this.stack = new TypeInfo[this.numberOfStackItems];
            for(int i = 0; i < this.numberOfStackItems; i++) {
                this.stack[i] = new TypeInfo(codeStream, constantPool);
            }
        } else {
            throw new ClassFormatException("Invalid Class-format! Failed to parse frame-type: " + this.frameType);
        }
    }

    public StackMapTableEntry(int frameType, int offset, int numberOfLocals, TypeInfo[] localFrames, int numberOfStackItems, TypeInfo[] stack, ConstantPool constantPool) {
        this.frameType = frameType;
        this.offset = offset;
        this.numberOfLocals = numberOfLocals;
        this.localFrames = localFrames;
        this.numberOfStackItems = numberOfStackItems;
        this.stack = stack;
        this.constantPool = constantPool;
    }

    public int getFrameType() {
        return this.frameType;
    }

    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public int getDeltaOffset() {
        return this.offset;
    }

    public void setDeltaOffset(int deltaOffset) {
        this.offset = deltaOffset;
    }

    public int getNumberOfLocals() {
        return this.numberOfLocals;
    }

    public void setNumberOfLocals(int numberOfLocals) {
        this.numberOfLocals = numberOfLocals;
    }

    public TypeInfo[] getLocalFrames() {
        return this.localFrames;
    }

    public void setLocalFrames(TypeInfo[] localFrames) {
        this.localFrames = localFrames;
    }

    public int getNumberOfStackItems() {
        return this.numberOfStackItems;
    }

    public void setNumberOfStackItems(int numberOfStackItems) {
        this.numberOfStackItems = numberOfStackItems;
    }

    public TypeInfo[] getStack() {
        return this.stack;
    }

    public void setStack(TypeInfo[] stack) {
        this.stack = stack;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void write(DataOutputStream codeStream) throws IOException, ClassFormatException {
        codeStream.write(this.frameType);

        if(isWithinBounds(this.frameType, SAME_FRAME, SAME_FRAME_MAX)) {
            // Do nothing
        } else if(isWithinBounds(this.frameType, SAME_LOCALS_1_STACK_ITEM, SAME_LOCALS_1_STACK_ITEM_MAX)) {
            this.stack[0].write(codeStream);
        } else if(this.frameType == SAME_LOCALS_1_STACK_ITEM_EXTENDED) {
            codeStream.writeShort(this.offset);
            this.stack[0].write(codeStream);
        } else if(isWithinBounds(this.frameType, CHOP_FRAME, CHOP_FRAME_MAX)) {
            codeStream.writeShort(this.offset);
        } else if(this.frameType == SAME_FRAME_EXTENDED) {
            codeStream.writeShort(this.offset);
        } else if(isWithinBounds(this.frameType, APPEND_FRAME, APPEND_FRAME_MAX)) {
            codeStream.writeShort(this.offset);
            for(int i = 0; i < this.numberOfLocals; i++) {
                this.localFrames[i].write(codeStream);
            }
        } else if(this.frameType == FULL_FRAME) {
            codeStream.writeShort(this.offset);

            codeStream.writeShort(this.numberOfLocals);
            for(int i = 0; i < this.numberOfLocals; i++) {
                this.localFrames[i].write(codeStream);
            }

            codeStream.writeShort(this.numberOfStackItems);
            for(int i = 0; i < this.numberOfStackItems; i++) {
                this.stack[i].write(codeStream);
            }
        } else {
            throw new ClassFormatException("Invalid Class-format! Failed to parse frame-type: " + this.frameType);
        }
    }

    /**
     * Utility method
     */
    private static boolean isWithinBounds(int number, int min, int max) {
        return number >= min && number <= max;
    }
}
