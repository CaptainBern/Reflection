package com.captainbern.jbel;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.member.Interface;
import com.captainbern.jbel.commons.member.field.FieldInfo;
import com.captainbern.jbel.commons.member.method.MethodInfo;
import com.captainbern.jbel.generator.ConstantPoolGenerator;
import com.captainbern.jbel.visitor.ClassVisitor;

public class ClassWriter extends ClassVisitor {

    private int minor;

    private int major;

    private ConstantPoolGenerator constantPool;

    private int accessFlags;

    private String className;

    private String superClassName;

    private Interface[] interfaces;

    private FieldInfo[] fields;

    private MethodInfo[] methods;

    private Attribute[] attributes;


    public ClassWriter(int api) {
        super(api);
        this.constantPool = new ConstantPoolGenerator();
    }
}
