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

package com.captainbern.jbel.commons.member.field;

import com.captainbern.jbel.ConstantPool;
import com.captainbern.jbel.commons.member.Member;
import com.captainbern.jbel.Opcode;
import com.captainbern.jbel.commons.attribute.Attribute;
import com.captainbern.jbel.commons.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FieldInfo extends Member implements Opcode {

    public FieldInfo(FieldInfo fieldInfo) {
        super(fieldInfo);
    }

    public FieldInfo(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        super(codeStream, constantPool);
    }

    public FieldInfo(int accessFlags, int index, int signatureIndex, ArrayList<Attribute> attributes, ConstantPool constantPool) {
        super(accessFlags, index, signatureIndex, attributes, constantPool);
    }
}
