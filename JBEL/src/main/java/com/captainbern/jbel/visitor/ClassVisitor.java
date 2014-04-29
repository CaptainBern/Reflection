package com.captainbern.jbel.visitor;

import com.captainbern.jbel.ClassReader;
import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.constant.Constant;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;

public interface ClassVisitor {

    public void visitClass(ClassReader classReader);

    public int visitMinor(int minorVersion);

    public int visitMajor(int majorVersion);

    public void visitConstantPool(ConstantPool constantPool);

    public void visitConstant(Constant constant);

    public int visitAccessFags(int flags);

    public String visitName(String name);

    public String visitSuperClassName(String name);

    public void visitInterfaces(Interface[] interfaces);

    public void visitInterface(Interface iface);

    public void visitFields(FieldInfo[] fields);

    public void visitField(FieldInfo field);

    public void visitMethods(MethodInfo[] methods);

    public void visitMethod(MethodInfo method);

    public void visitAttributes(Attribute[] attributes);

    public void visitAttribute(Attribute attribute);

    public void visitEnd();
}
