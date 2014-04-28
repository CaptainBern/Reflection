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

package com.captainbern.reflection.bytecode;

import com.captainbern.reflection.bytecode.attribute.Attribute;
import com.captainbern.reflection.bytecode.exception.ClassFormatException;
import com.captainbern.reflection.bytecode.member.Interface;
import com.captainbern.reflection.bytecode.member.field.FieldInfo;
import com.captainbern.reflection.bytecode.member.method.MethodInfo;

import java.io.*;

public class ClassReader implements Opcode {

    private int magic;
    private int minor;
    private int major;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private Interface[] interfaces;
    private FieldInfo[] fields;
    private MethodInfo[] methods;
    private Attribute[] attributes;

    public ClassReader(final byte[] bytes) throws IOException, ClassFormatException {
        this(bytes, 0, bytes.length);
    }

    public ClassReader(final byte[] bytes, final int offset, final int length) throws IOException, ClassFormatException {

        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, offset, newBytes, 0, length);

        DataInputStream codeStream = null;

        try {
            codeStream = new DataInputStream(new ByteArrayInputStream(newBytes));
            this.magic = codeStream.readInt();

            if (magic != 0xCAFEBABE) {
                throw new IOException("Invalid ClassFile! Magic returned: \'" + Integer.toHexString(magic) + "\'");
            }

            this.minor = codeStream.readUnsignedShort();
            this.major = codeStream.readUnsignedShort();

            if (major > JDK_8) {
                throw new IllegalArgumentException("Unsupported ClassFile!");
            }

            this.constantPool = new ConstantPool(codeStream);

            this.accessFlags = codeStream.readUnsignedShort();
            this.thisClass = codeStream.readUnsignedShort();
            this.superClass = codeStream.readUnsignedShort();

            // Interfaces
            int interfacesCount = codeStream.readUnsignedShort();
            this.interfaces = new Interface[interfacesCount];
            for (int i = 0; i < interfacesCount; i++) {
                this.interfaces[i] = new Interface(codeStream.readUnsignedShort(), this.constantPool);
            }

            // Fields
            int fieldCount = codeStream.readUnsignedShort();
            this.fields = new FieldInfo[fieldCount];
            for (int i = 0; i < fieldCount; i++) {
                this.fields[i] = new FieldInfo(codeStream, this.constantPool);
            }

            // Methods
            int methodCount = codeStream.readUnsignedShort();
            this.methods = new MethodInfo[methodCount];
            for (int i = 0; i < methodCount; i++) {
                this.methods[i] = new MethodInfo(codeStream, this.constantPool);
            }

            // Attributes
            int attributeCount = codeStream.readUnsignedShort();
            this.attributes = new Attribute[attributeCount];
            for (int i = 0; i < attributeCount; i++) {
                this.attributes[i] = Attribute.readAttribute(codeStream, this.constantPool);
            }

        } finally {
            try {
                if(codeStream != null) {
                    codeStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public ClassReader(final String source) throws IOException, ClassFormatException {
        this(toBytes(ClassLoader.getSystemResourceAsStream(source.replace(".", "/") + ".class"), true));
    }

    public ClassReader(final InputStream inputStream) throws IOException, ClassFormatException {
        this(toBytes(inputStream, false));
    }

    private static byte[] toBytes(final InputStream inputStream, boolean close) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[inputStream.available()];

            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }

            return buffer;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(close) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public byte[] getByteCode() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            write(dataOutputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            try {
                dataOutputStream.close();
            } catch (IOException ioe1) {
                ioe1.printStackTrace();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    public void write(File file) throws IOException {
        if(file.getParent() != null) {
            File parent = new File(file.getParent());
            parent.mkdirs();
        }

        DataOutputStream dataStream = null;

        try {
            dataStream = new DataOutputStream(new FileOutputStream(file));
            write(dataStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if(dataStream == null)
                return;
            dataStream.close();
        }
    }

    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeInt(0xCAFEBABE);

        codeStream.writeShort(this.minor);
        codeStream.writeShort(this.major);

        this.constantPool.write(codeStream);

        /**
         * Class info
         */
        codeStream.writeShort(this.accessFlags);
        codeStream.writeShort(this.thisClass);
        codeStream.writeShort(this.superClass);

        /**
         * Interfaces
         */
        codeStream.writeShort(this.interfaces.length);
        for (Interface iface : this.interfaces) {
            codeStream.writeShort(iface.getIndex());
        }

        /**
         * Fields
         */
        codeStream.writeShort(this.fields.length);
        for (FieldInfo fieldInfo : this.fields) {
            fieldInfo.write(codeStream);
        }

        /**
         * Methods
         */
        codeStream.writeShort(this.methods.length);
        for (MethodInfo methodInfo : this.methods) {
            methodInfo.write(codeStream);
        }

        if (this.attributes != null) {
            codeStream.writeShort(this.attributes.length);
            for (Attribute attribute : this.attributes) {
                attribute.write(codeStream);
            }
        } else {
            codeStream.writeShort(0);
        }

        codeStream.flush();
    }

    public Class defineClass() {
        return new ClassLoader() {
            public Class defineClass(byte[] bytes) {
                return super.defineClass(ClassReader.this.getClassName(), bytes, 0, bytes.length);
            }
        }.defineClass(getByteCode());
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

    public Interface[] getInterfaces() {
        return this.interfaces;
    }

    public FieldInfo[] getFields() {
        return this.fields;
    }

    public MethodInfo[] getMethods() {
        return this.methods;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }
}
