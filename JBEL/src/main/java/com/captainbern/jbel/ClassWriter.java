/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.                        by
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
