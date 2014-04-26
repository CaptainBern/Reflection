package com.captainbern.reflection.bytecode;

import com.captainbern.reflection.bytecode.attribute.Attribute;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;
import com.captainbern.reflection.bytecode.field.Field;
import com.captainbern.reflection.bytecode.method.Method;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ClassFile implements Opcode {

    protected byte[] bytes;
    protected DataInputStream codeStream;
    private int magic;
    private int minor;
    private int major;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private int[] interfaces;
    private String[] interfaceNames;
    private Field[] fields;
    private Method[] methods;
    private Attribute[] attributes;

    public ClassFile(final byte[] bytes) throws IOException, ClassFormatException {
        DataInputStream codeStream = new DataInputStream(new ByteArrayInputStream(bytes));
        this.magic = codeStream.readInt();

        if(magic != 0xCAFEBABE) {
            throw new IOException("Invalid ClassFile! Magic returned: \'" + Integer.toHexString(magic) + "\'");
        }

        this.minor = codeStream.readUnsignedShort();
        this.major = codeStream.readUnsignedShort();

        if(major > JDK_8) {
            throw new IllegalArgumentException("Unsupported ClassFile!");
        }

        this.constantPool = new ConstantPool(codeStream);

        this.accessFlags = codeStream.readUnsignedShort();
        this.thisClass = codeStream.readUnsignedShort();
        this.superClass = codeStream.readUnsignedShort();

        // Interfaces
        int interfacesCount = codeStream.readUnsignedShort();
        if(interfacesCount == 0) {
            this.interfaces = null;
        } else {
            this.interfaces = new int[interfacesCount];
            for(int i = 0; i < interfacesCount; i++) {
                this.interfaces[i] = codeStream.readUnsignedShort();
            }
        }

        // And their names
        this.interfaceNames = new String[this.interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaceNames[i] = this.constantPool.getConstantString(interfaces[i], Opcode.CONSTANT_Class);
        }

        // Fields
        int fieldCount = codeStream.readUnsignedShort();
        if(fieldCount == 0) {
            this.fields = null;
        } else {
            this.fields = new Field[interfacesCount];
            for(int i = 0; i < fieldCount; i++) {
                this.fields[i] = new Field(codeStream, this.constantPool);
            }
        }

        // Methods
        int methodCount = codeStream.readUnsignedShort();
        if(methodCount == 0) {
            this.methods = null;
        } else {
            this.methods = new Method[methodCount];
            for(int i = 0; i < methodCount; i++) {
                this.methods[i] = new Method(codeStream, this.constantPool);
            }
        }

        // Attributes
    }

    public byte[] getByteCode() {
        return this.bytes;
    }

    public int getMagic() {
        return this.magic;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getMajor() {
        return this.major;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public String getClassName() {
        try {
            return this.constantPool.getConstantString(this.thisClass, CONSTANT_Class);
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }

    public String getSuperClassName() {
        try {
            return this.constantPool.getConstantString(this.superClass, CONSTANT_Class);
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }

    public int[] getInterfaces() {
        return this.interfaces;
    }

    public String[] getInterfaceNames() {
        return this.interfaceNames;
    }

    public Field[] getFields() {
        return this.fields;
    }

    public Method[] getMethods() {
        return this.methods;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }
}
