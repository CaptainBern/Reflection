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

public class Access {

    /**
     * Short introduction into bitwise ops:
     * & = bitwise AND, it returns the bits that are turned on in both parts
     *     0x0001 & 0x0101 = 0x0001
     * | = bitwise (inclusive) OR, similar to AND
     *     0x0100 | 0x0010 (int = 4 | 2) = 0x0110 (or 6)
     *
     * ^ = bitwise (exclusive) XOR, think of it as "this or that, but not both!"
     *     (decimal)    (binary)
     *          5     =  101
     *          6     =  110
     *      ------------------ xor
     *          3     =  011
     *
     * ~ = unary bitwise complement, switches the state of the bits
     *     ~0x0011 becomes 0x1100
     */

    /**
     * Access codes
     */
    public static final int ACC_PUBLIC      = 0x0001;
    public static final int ACC_PRIVATE     = 0x0002;
    public static final int ACC_PROTECTED   = 0x0004;
    public static final int ACC_STATIC      = 0x0008;
    public static final int ACC_FINAL       = 0x0010;
    public static final int ACC_SUPER       = 0x0020;
    public static final int ACC_SYNCHRONIZED
                                            = 0x0020;
    public static final int ACC_VOLATILE    = 0x0040;
    public static final int ACC_BRIDGE      = 0x0040;
    public static final int ACC_VARARGS     = 0x0080;
    public static final int ACC_TRANSIENT   = 0x0080;
    public static final int ACC_NATIVE      = 0x0100;
    public static final int ACC_INTERFACE   = 0x0200;
    public static final int ACC_ABSTRACT    = 0x0400;
    public static final int ACC_STRICT      = 0x0800;
    public static final int ACC_SYNTHETIC   = 0x1000;
    public static final int ACC_ANNOTATION  = 0x2000;
    public static final int ACC_ENUM        = 0x4000;

    /**
     * Our flag
     */
    private int flag;

    public Access(int flag) {
        setFlag(flag);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return this.flag;
    }

    public int setPublic(boolean bool) {
        return this.flag = (bool ? (this.flag & ~(ACC_PRIVATE | ACC_PROTECTED)) | ACC_PUBLIC : this.flag & ~(ACC_PUBLIC) | ACC_PRIVATE);
    }

    public boolean isPublic() {
        return (this.flag & ACC_PUBLIC) != 0;
    }

    public int setPrivate(boolean bool) {
        return this.flag = (bool ? (this.flag & ~(ACC_PUBLIC | ACC_PROTECTED)) | ACC_PRIVATE : this.flag & ~(ACC_PRIVATE));
    }

    public boolean isPrivate() {
        return (this.flag & ACC_PUBLIC) != 0;
    }

    public int setProtected(boolean bool) {
        return this.flag = (bool ? (this.flag & ~(ACC_PUBLIC | ACC_PRIVATE)) | ACC_PROTECTED : this.flag & ~(ACC_PROTECTED));
    }

    public boolean isProtected() {
        return (this.flag & ACC_PROTECTED) != 0;
    }

    public int setStatic(boolean bool) {
        return this.flag = (bool ? (this.flag | ACC_STATIC) : this.flag & ~(ACC_STATIC));
    }

    public boolean isStatic() {
        return (this.flag & ACC_STATIC) != 0;
    }

    public int setFinal(boolean bool) {
         return this.flag = (bool ? (this.flag & ~(ACC_ABSTRACT) | ACC_FINAL) : this.flag & ~(ACC_STATIC));
    }

    public boolean isFinal() {
        return (this.flag & ACC_FINAL) != 0;
    }
}
