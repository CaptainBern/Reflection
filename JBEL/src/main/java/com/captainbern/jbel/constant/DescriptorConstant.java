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

package com.captainbern.jbel.constant;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public class DescriptorConstant extends Constant {

    private int memberName;
    private int typeDescriptor;

    public DescriptorConstant(DescriptorConstant constant) {
        this(constant.getMemberName(), constant.getTypeDescriptor());
    }

    public DescriptorConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort(), stream.readUnsignedShort());
    }

    public DescriptorConstant(int memberName, int typeDescriptor) {
        super(CONSTANT_NameAndType);
        this.memberName = memberName;
        this.typeDescriptor = typeDescriptor;
    }

    public int getMemberName() {
        return this.memberName;
    }

    public int getTypeDescriptor() {
        return this.typeDescriptor;
    }

    @Override
    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeByte(this.tag);
        codeStream.writeShort(this.memberName);
        codeStream.writeShort(this.typeDescriptor);
    }
}
