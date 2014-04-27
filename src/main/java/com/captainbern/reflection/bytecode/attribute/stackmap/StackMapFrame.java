package com.captainbern.reflection.bytecode.attribute.stackmap;

import com.captainbern.reflection.bytecode.ConstantPool;
import com.captainbern.reflection.bytecode.Opcode;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a Frame (while in the JVM-Specs the "stack_map_frame" a representation is of all the
 * possible StackMap types, I called this a StackMapFrame since I feel this name fits better then any other)
 *
 * To see how the parsing works read: <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.4">StackMapFrame</a>
 */
public final class StackMapFrame implements Opcode {

    private int frameType;
    private int offset;    /*delta offset*/
    private int numberOfLocals;
    private TypeInfo[] localFrames;
    private int numberOfStackItems;
    private TypeInfo[] stack;

    private ConstantPool constantPool;

    public StackMapFrame(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
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
            this.numberOfLocals = this.frameType - 251;
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

    public StackMapFrame(int frameType, int offset, int numberOfLocals, TypeInfo[] localFrames, int numberOfStackItems, TypeInfo[] stack, ConstantPool constantPool) {
        this.frameType = frameType;
        this.offset = offset;
        this.numberOfLocals = numberOfLocals;
        this.localFrames = localFrames;
        this.numberOfStackItems = numberOfStackItems;
        this.stack = stack;
        this.constantPool = constantPool;
    }

    public final int getFrameType() {
        return this.frameType;
    }

    public final void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public final int getDeltaOffset() {
        return this.offset;
    }

    public final void setDeltaOffset(int deltaOffset) {
        this.offset = deltaOffset;
    }

    public final int getNumberOfLocals() {
        return this.numberOfLocals;
    }

    public final void setNumberOfLocals(int numberOfLocals) {
        this.numberOfLocals = numberOfLocals;
    }

    public final TypeInfo[] getLocalFrames() {
        return this.localFrames;
    }

    public final void setLocalFrames(TypeInfo[] localFrames) {
        this.localFrames = localFrames;
    }

    public final int getNumberOfStackItems() {
        return this.numberOfStackItems;
    }

    public final void setNumberOfStackItems(int numberOfStackItems) {
        this.numberOfStackItems = numberOfStackItems;
    }

    public final TypeInfo[] getStack() {
        return this.stack;
    }

    public final void setStack(TypeInfo[] stack) {
        this.stack = stack;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    /**
     * Utility method
     */
    private static boolean isWithinBounds(int number, int min, int max) {
        return number >= min && number <= max;
    }
}
