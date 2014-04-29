package com.captainbern.jbel.visitor;

import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.member.Member;

public interface MemberVisitor {

    public void visitMember(Member member);

    public int visitAccessFlags(int mask);

    public String visitName(String name);

    public String visitDescriptor(String descriptor);

    public void visitAttributes(Attribute[] attributes);

    public void visitAttribute(Attribute attribute);

    public void visitEnd();
}
