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

package com.captainbern.jbel.attribute;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DebugExtension {

    private int index;

    public DebugExtension(DebugExtension debugExtension) {
        this(debugExtension.getIndex());
    }

    public DebugExtension(DataInputStream codeStream) throws IOException {
        this(codeStream.readUnsignedShort());
    }

    public DebugExtension(int index) {
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeShort(this.index);
    }
}
