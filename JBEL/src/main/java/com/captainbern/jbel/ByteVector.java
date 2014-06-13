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

/**
 * Class to easy work with bytes etc.
 */
public class ByteVector {

    /**
     * The default size of a ByteVector
     */
    public static final int DEFAULT_SIZE = 64;

    /**
     * The contents of the ByteVector
     */
    private byte[] data;

    /**
     * The length of the ByteVector, also uses as "index"
     */
    private int length;

    public ByteVector() {
        this(DEFAULT_SIZE);
    }

    public ByteVector(final int length) {
        this.data = new byte[length];
    }

    /**
     * Returns the contents of the ByteVector
     *
     * @return
     */
    public byte[] getBytes() {
        return this.data;
    }

    /**
     * Returns the length of this ByteVector
     *
     * @return
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Puts a byte in the vector
     *
     * @param b
     * @return
     */
    public ByteVector putByte(final int b) {
        ensureCapacity(1);

        this.data[this.length++] = (byte) (b&0xFF);

        return this;
    }

    /**
     * Puts 2 bytes in the vector
     *
     * @param b1
     * @param b2
     * @return
     */
    public ByteVector put11(final int b1, final int b2) {
        ensureCapacity(2);

        this.data[this.length++] = (byte) (b1&0xFF);
        this.data[this.length++] = (byte) (b2&0xFF);

        return this;
    }

    /**
     * Puts a short in the vector
     *
     * @param s
     * @return
     */
    public ByteVector putShort(final short s) {
        ensureCapacity(2);

        this.data[this.length++] = (byte) ((s >>> 8)&0xFF);
        this.data[this.length++] = (byte) (s&0xFF);

        return this;
    }

    /**
     * Puts a byte and a short in the vector
     *
     * @param b
     * @param s
     * @return
     */
    public ByteVector put12(final int b, final short s) {
        ensureCapacity(3);

        this.data[this.length++] = (byte) (b&0xFF);
        this.data[this.length++] = (byte) ((s >>> 8)&0xFF);
        this.data[this.length++] = (byte) (s&0xFF);

        return this;
    }

    /**
     * Puts an int in the vector
     *
     * @param i
     * @return
     */
    public ByteVector putInt(final int i) {
        ensureCapacity(4);

        this.data[this.length++] = (byte) ((i >>> 24)&0xFF);
        this.data[this.length++] = (byte) ((i >>> 16)&0xFF);
        this.data[this.length++] = (byte) ((i >>> 8)&0xFF);
        this.data[this.length++] = (byte) (i&0xFF);

        return this;
    }

    /**
     * Puts a float in the vector
     *
     * @param f
     * @return
     */
    public ByteVector putFloat(final float f) {
        this.putInt(Float.floatToRawIntBits(f));
        return this;
    }

    /**
     * Puts a double in the vector
     *
     * @param d
     * @return
     */
    public ByteVector putDouble(final double d) {
        this.putLong(Double.doubleToRawLongBits(d));
        return this;
    }

    /**
     * Puts a long in the vector
     *
     * @param l
     * @return
     */
    public ByteVector putLong(final long l) {
        ensureCapacity(8);

        this.data[this.length++] = (byte) ((l >>> 56)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 48)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 40)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 32)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 24)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 16)&0xFF);
        this.data[this.length++] = (byte) ((l >>> 8)&0xFF);
        this.data[this.length++] = (byte) (l&0xFF);

        return this;
    }

    /**
     * Puts a byte array in the vector
     *
     * @param bytes
     * @param offset
     * @param length
     * @return
     */
    public ByteVector putByteArray(final byte[] bytes, final int offset, final int length) {
        ensureCapacity(length);

        if (bytes != null) {
            System.arraycopy(bytes, offset, this.data, this.length, length);
        }
        this.length += length;

        return this;
    }

    /**
     * Puts a string in the bytevector
     *
     * @param s
     * @return
     */
    public ByteVector putString(final String s) {
        int stringLength = s.length();

        ensureCapacity(2 + stringLength);

        final int originalLength = this.length;

        this.data[this.length++] = (byte) (stringLength >>> 8);
        this.data[this.length++] = (byte) (stringLength);

        for (int i = 0; i < stringLength; i++) {
            // Apply magic here
            char c = s.charAt(i);
            if (c >= '\001' && c <= '\177') {
                this.data[length++] = (byte) c;
            } else {
                int bytesLength = i;
                for (int j = 0; j < stringLength; j++) {
                    c = s.charAt(j);
                    if (c >= '\001' && c <= '\177') {
                        bytesLength++;
                    } else if (c > '\u07FF') {
                        bytesLength += 3;
                    } else {
                        bytesLength += 2;
                    }
                }

                this.data[originalLength] = (byte) (bytesLength >>> 8);
                this.data[originalLength + 1] = (byte) (bytesLength);

                ensureCapacity(bytesLength + 2);

                for (int j = i; j < stringLength; ++j) {
                    c = s.charAt(j);
                    if (c >= '\001' && c <= '\177') {
                        this.data[this.length++] = (byte) c;
                    } else if (c > '\u07FF') {
                        this.data[this.length++] = (byte) (0xE0|c >> 12&0xF);
                        this.data[this.length++] = (byte) (0x80|c >> 6&0x3F);
                        this.data[this.length++] = (byte) (0x80|c&0x3F);
                    } else {
                        this.data[this.length++] = (byte) (0xC0|c >> 6&0x1F);
                        this.data[this.length++] = (byte) (0x80|c&0x3F);
                    }
                }
                break;
            }
        }

        return this;
    }

    /**
     * Ensures the capacity of the vector, when it's too small for the data that needs to
     * be put in, it will be resized
     *
     * @param size
     */
    protected void ensureCapacity(final int size) {
        if (size + this.length <= this.data.length) {
            return;
        }

        int length1 = 2 * this.data.length;
        int length2 = this.length + size;
        byte[] newData = new byte[length1 > length2 ? length1 : length2];
        System.arraycopy(data, 0, newData, 0, this.length);
        this.data = newData;
    }

    protected void checkIndex(final int index) {
        if (index < 0 || index > this.data.length) {
            throw new IndexOutOfBoundsException();
        }
    }
}
