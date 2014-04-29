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

package com.captainbern.jbel;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.attribute.Signature;
import com.captainbern.jbel.commons.attribute.SourceFile;
import com.captainbern.jbel.commons.exception.ClassFormatException;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;
import com.captainbern.jbel.visitor.ClassVisitor;

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

    /**
     * Constructs a new ClassReader with the given bytes.
     * @param bytes
     * @throws IOException
     * @throws ClassFormatException
     */
    public ClassReader(final byte[] bytes) throws IOException, ClassFormatException {
        this(bytes, 0, bytes.length);
    }

    public ClassReader(final byte[] bytes, final int offset, final int length) throws IOException, ClassFormatException {

        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, offset, newBytes, 0, length);

        DataInputStream codeStream = null;

        try {
            codeStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(newBytes), 8192));
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
                if (codeStream != null) {
                    codeStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Constructs a new ClassReader with the given source location.
     * eg: ClassReader reader = new ClassReader(ClassReader.class.getCanonicalName());
     *
     * @param source
     * @throws IOException
     * @throws ClassFormatException
     */
    public ClassReader(final String source) throws IOException, ClassFormatException {
        this(toBytes(ClassLoader.getSystemResourceAsStream(source.replace(".", "/") + ".class"), true));
    }

    /**
     * Constructs a new ClassReader from the given InputStream.
     * @param inputStream
     * @throws IOException
     * @throws ClassFormatException
     */
    public ClassReader(final InputStream inputStream) throws IOException, ClassFormatException {
        this(toBytes(inputStream, false));
    }

    /**
     * Converts a given InputStream to a byte-array. (or pure bytecode)
     * @param inputStream
     * @param close
     * @return
     */
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
                if (close) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns the bytecode of this ClassReader. This allows one to easily edit a specific value and create a
     * new class with the given bytes.
     * @return
     */
    public byte[] getByteCode() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream, 8192));

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

    /**
     * Writes this class to a file.
     * @param file
     * @throws IOException
     */
    public void write(File file) throws IOException {
        if (file.getParent() != null) {
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
            if (dataStream == null)
                return;
            dataStream.close();
        }
    }

    /**
     * Writes this class to the given DataOutputStream
     * @param codeStream
     * @throws IOException
     */
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

        /**
         * Attributes
         */
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

    /**
     * Defines a new class. Editing classes has never been this easy (and unsafe you moron)!
     * @return
     */
    public Class defineClass() {
        return new ClassLoader() {
            public Class defineClass(byte[] bytes) {
                return super.defineClass(ClassReader.this.getClassName().replace('/', '.'), bytes, 0, bytes.length);
            }
        }.defineClass(getByteCode());
    }

    /**
     * Returns the magic of this class. This is always 0xCAFEBABE.
     * @return
     */
    public int getMagic() {
        return this.magic;
    }

    /**
     * Returns the minor version of this class.
     * @return
     */
    public int getMinor() {
        return this.minor;
    }

    /**
     * Returns the major version of this class.
     * @return
     */
    public int getMajor() {
        return this.major;
    }

    /**
     * Returns a ConstantPool object of this class.
     * @return
     */
    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    /**
     * Returns the access-flags of this class.
     * @return
     */
    public int getAccessFlags() {
        return this.accessFlags;
    }

    /**
     * Returns the name of this class.
     * @return
     */
    public String getClassName() {
        try {
            return this.constantPool.getUtf8(this.constantPool.getClass(this.thisClass).getNameIndex()).getString();
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }

    /**
     * Returns the location of the name in the ConstantPool.
     * @return
     */
    public int getClassNameIndex() {
        return this.thisClass;
    }

    /**
     * returns the name of the SuperClass of this class.
     * @return
     */
    public String getSuperClassName() {
        try {
            return this.constantPool.getUtf8(this.constantPool.getClass(this.superClass).getNameIndex()).getString();
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }

    /**
     * Returns the location of the SuperClassName in the ConstantPool.
     * @return
     */
    public int getSuperClassNameIndex() {
        return this.superClass;
    }

    /**
     * Returns an array of interfaces of this class.
     * @return
     */
    public Interface[] getInterfaces() {
        return this.interfaces;
    }

    /**
     * Returns an array of fields of this class.
     * @return
     */
    public FieldInfo[] getFields() {
        return this.fields;
    }

    /**
     * Returns an array of methods of this class.
     * @return
     */
    public MethodInfo[] getMethods() {
        return this.methods;
    }

    /**
     * Returns an array of attributes of this class.
     * @return
     */
    public Attribute[] getAttributes() {
        return this.attributes;
    }

    /**
     * Visitor system. You can easily create your own visitors and make them visit a class and edit values etc.
     */
    public void accept(ClassVisitor visitor) throws ClassFormatException {

        int version = this.getMajor();
        int accessFlags = this.getAccessFlags();
        String className = this.getClassName();
        String superClassName = this.getSuperClassName();

        String signature = null;
        String sourceFile = null;

        for (Attribute attribute : this.attributes) {

            if (attribute instanceof SourceFile) {
                sourceFile = ((SourceFile) attribute).getSourceFile();
            } else if (attribute instanceof Signature) {
                signature = ((Signature) attribute).getSignature();
            }

            visitor.visit(version, accessFlags, className, superClassName, signature);

           visitor.visitSource(sourceFile);
        }

        for(Interface iface : this.interfaces) {
            visitor.visitInterface(iface);
        }

        for(FieldInfo field : this.fields) {
            visitField(visitor, field);
        }
    }

    /**
     * Visits a specific field.
     * @param visitor
     * @param field
     * @throws ClassFormatException
     */
    protected void visitField(ClassVisitor visitor, FieldInfo field) throws ClassFormatException {
        int access = field.getAccessFlags();
        String name = field.getName();
        String descriptor = field.getDescriptor();
        String signature = null;

        for(Attribute attribute : field.getAttributes()) {
            if(attribute instanceof Signature) {
                signature = ((Signature) attribute).getSignature();
            }
        }

        visitor.visitField(access, name, descriptor, signature);
    }
}
