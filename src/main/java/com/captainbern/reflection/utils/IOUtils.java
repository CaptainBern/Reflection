package com.captainbern.reflection.utils;

import java.io.*;

public class IOUtils {

    public static final char DIR_SEPARATOR_CHAR = '/';

    public static final String DIR_SEPARATOR = File.pathSeparator;

    public static final int DEFAULT_BUFFER_SIZE = 0x1060;

    public IOUtils() {
        super();
    }

    /**
     * Close methods
     */

    public static void closeQuietly(final OutputStream outputStream) {
        if(outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final InputStream inputStream) {
        if(inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Writer writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Reader reader) {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if(closeables == null)
            return;
        for(Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    /**
     * Copy methods
     */

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long count = copyReturnLong(inputStream, outputStream);
        if(count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long count = copyReturnLong(reader, writer);
        if(count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static int copy(InputStream inputStream, Writer writer) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return copy(inputStreamReader, writer);
    }

    public static int copy(Reader reader, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        return copy(reader, writer);
    }

    public static int copy(InputStream inputStream, Writer writer, String encoding) throws IOException {
        if(encoding == null) {
            return copy(inputStream, writer);
        } else {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
            return copy(inputStreamReader, writer);
        }
    }

    public static int copy(Reader reader, OutputStream outputStream, String encoding) throws IOException {
        if(encoding == null) {
            return copy(reader, outputStream);
        } else {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, encoding);
            return copy(reader, outputStreamWriter);
        }
    }

    public static long copyReturnLong(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copyReturnLong(Reader reader, Writer writer) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while(-1 != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Convert utils
     */

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(reader, outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader, String encoding) throws IOException {
        if(encoding == null) {
            return toByteArray(reader);
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            copy(reader, outputStream, encoding);
            return outputStream.toByteArray();
        }
    }

    /**
     * Converts a byte to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final byte data) {
        return new byte[]{data};
    }

    /**
     * Converts a byte-array to a byte-array.
     * @param data
     * @return
     */
    public static byte[] toByteArray(final byte[] data) {
        if(data == null || data.length == 0) {
            return null;
        }

        return data;
    }

    /**
     * Converts a short to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final short data) {
        return new byte[]{
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    /**
     * Converts a short-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final short[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 2, 2);
        }

        return bytes;
    }

    /**
     * Converts a char to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final char data) {
        return new byte[]{
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    /**
     * Converts a char-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final char[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 2, 2);
        }

        return bytes;
    }

    /**
     * Converts an int to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xFF),
                (byte) ((data >> 16) & 0xFF),
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    /**
     * Converts an int-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final int[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 4];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 4, 4);
        }

        return bytes;
    }

    /**
     * Converts a long to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xFF),
                (byte) ((data >> 48) & 0xFF),
                (byte) ((data >> 40) & 0xFF),
                (byte) ((data >> 32) & 0xFF),
                (byte) ((data >> 24) & 0xFF),
                (byte) ((data >> 16) & 0xFF),
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    /**
     * Converts a long-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final long[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 8];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 8, 8);
        }

        return bytes;
    }

    /**
     * Converts a float to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final float data) {
        return toByteArray(Float.floatToRawIntBits(data));
    }

    /**
     * Converts a float-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final float[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 4];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 4, 4);
        }

        return bytes;
    }

    /**
     * Converts a double to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final double data) {
        return toByteArray(Double.doubleToRawLongBits(data));
    }

    /**
     * Converts a double-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final double[] data) {
        if(data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length * 8];
        for(int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, bytes, i * 8, 8);
        }

        return bytes;
    }

    /**
     * Converts a string to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final String data) {
        if(data == null) {
            return null;
        }

        return data.getBytes();
    }

    /**
     * Converts a string-array to a byte-array.
     * @param data
     * @return
     */
    public static byte[] toByteArray(final String[] data) {
        if (data == null) {
            return null;
        }

        int totalLength = 0;
        int bytesPos = 0;

        byte[] dLen = toByteArray(data.length);
        totalLength += dLen.length;
        int[] sLens = new int[data.length];
        totalLength += (sLens.length * 4);
        byte[][] strings = new byte[data.length][];

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                strings[i] = toByteArray(data[i]);
                sLens[i] = strings[i].length;
                totalLength += strings[i].length;
            } else {
                sLens[i] = 0;
                strings[i] = new byte[0];
            }
        }

        byte[] bytes = new byte[totalLength];
        System.arraycopy(dLen, 0, bytes, 0, 4);
        byte[] bsLens = toByteArray(sLens);
        System.arraycopy(bsLens, 0, bytes, 4, bsLens.length);

        bytesPos += 4 + bsLens.length;

        for (byte[] sba : strings) {
            System.arraycopy(sba, 0, bytes, bytesPos, sba.length);
            bytesPos += sba.length;
        }

        return bytes;
    }

    /**
     * Converts a boolean to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final boolean data) {
        return new byte[]{(byte) (data? 0x01 : 0x00)};
    }

    /**
     * Converts a boolean-array to a byte-array
     * @param data
     * @return
     */
    public static byte[] toByteArray(final boolean[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        byte[] lena = toByteArray(len);
        byte[] bytes = new byte[lena.length + (len / 8) + (len % 8 != 0 ? 1 : 0)];

        System.arraycopy(lena, 0, bytes, 0, lena.length);

        for (int i = 0, j = lena.length, k = 7; i < data.length; i++) {
            bytes[j] |= (data[i] ? 1 : 0) << k--;
            if (k < 0) { j++; k = 7; }
        }

        return bytes;
    }

    /**
     * -------------------------------------------------------------------------
     * Read methods
     * -------------------------------------------------------------------------
     */

    /**
     * Converts a byte-array to a byte
     * @param bytes
     * @param index
     * @return
     */
    public static byte readByte(final byte[] bytes, final int index) {
        if(bytes == null || bytes.length == 0) {
            return 0x0;
        }
        return bytes[index];
    }

    /**
     * Converts a byte-array to an unsigned-short
     * @param bytes
     * @param index
     * @return
     */
    public static int readUnsignedShort(final byte[] bytes, final int index) {
        if (bytes == null || (bytes.length - index) < 2) {
            return 0x0;
        }

        return (
                (bytes[index] & 0xFF) << 8) |
                (bytes[index + 1] & 0xFF
                );
    }

    /**
     * Converts a byte-array to a short
     * @param bytes
     * @param index
     * @return
     */
    public static short readShort(final byte[] bytes, final int index) {
        if(bytes == null || (bytes.length - index) < 2) {
            return 0x0;
        }

        return (short) ((
                (bytes[index] & 0xFF) << 8) |
                (bytes[index + 1] & 0xFF) << 0
        );
    }

    /**
     * Converts a byte-array to a short-array
     * @param bytes
     * @return
     */
    public static short[] toShortArray(final byte[] bytes) {
        if(bytes == null || bytes.length % 2 != 0) {
            return null;
        }

        short[] shorts = new short[bytes.length / 2];
        for(int i = 0; i < shorts.length; i++) {
            shorts[i] = readShort(new byte[]{
                    bytes[(i * 2)],
                    bytes[(i * 2) + 1]
            }, 0);
        }

        return shorts;
    }

    /**
     * Converts a byte-array to a char
     * @param bytes
     * @param index
     * @return
     */
    public static char readChar(final byte[] bytes, final int index) {
        if(bytes == null || (bytes.length - index) < 2) {
            return 0x0;
        }

        return (char) ((
                (bytes[index] & 0xFF) << 8) |
                (bytes[index + 1] & 0xFF) << 0
        );
    }

    /**
     * Converts a byte-array to a char-array
     * @param bytes
     * @return
     */
    public static char[] toCharArray(final byte[] bytes) {
        if(bytes == null || bytes.length % 2 != 0) {
            return null;
        }

        char[] chars = new char[bytes.length / 2];
        for(int i = 0; i < chars.length; i++) {
            chars[i] = readChar(new byte[]{
                    bytes[(i * 2)],
                    bytes[(i * 2) + 1]
            }, 0);
        }

        return chars;
    }

    /**
     * Converts a byte-array to an int
     * @param bytes
     * @param index
     * @return
     */
    public static int readInt(final byte[] bytes, final int index) {
        if(bytes == null || (bytes.length - index) < 4) {
            return 0x0;
        }

        return (
                (bytes[index] & 0xFF) << 24) |
                ((bytes[index + 1] & 0xFF) << 16) |
                ((bytes[index + 2] & 0xFF) << 8) |
                (bytes[index + 3] & 0xFF
                );
    }

    /**
     * Converts a byte-array to an int-array
     * @param bytes
     * @return
     */
    public static int[] toIntArray(final byte[] bytes) {
        if(bytes == null || bytes.length % 4 != 0) {
            return null;
        }

        int[] ints = new int[bytes.length / 4];
        for(int i = 0; i < ints.length; i++) {
            ints[i] = readInt(new byte[]{
                    bytes[(i * 4)],
                    bytes[(i * 4) + 1],
                    bytes[(i * 4) + 2],
                    bytes[(i * 4) + 3]
            }, 0);
        }

        return ints;
    }

    /**
     * Converts a byte-array to a long
     * @param bytes
     * @param index
     * @return
     */
    public static long readLong(final byte[] bytes, final int index) {
        if(bytes == null || (bytes.length - index) < 8) {
            return 0x0;
        }

        long l1 = readInt(bytes, index);
        long l0 = readInt(bytes, index + 4) & 0xFFFFFFFFL;
        return (l1 << 32) | l0;
    }

    /**
     * Converts a byte-array to a long-array
     * @param bytes
     * @return
     */
    public static long[] toLongArray(final byte[] bytes) {
        if(bytes == null || bytes.length % 8 != 0) {
            return null;
        }

        long[] longs = new long[bytes.length / 8];
        for(int i = 0; i < longs.length; i++) {
            longs[i] = readLong(new byte[]{
                    bytes[(i * 8)],
                    bytes[(i * 8) + 1],
                    bytes[(i * 8) + 2],
                    bytes[(i * 8) + 3],
                    bytes[(i * 8) + 4],
                    bytes[(i * 8) + 5],
                    bytes[(i * 8) + 6],
                    bytes[(i * 8) + 7],
            }, 0);
        }

        return longs;
    }

    public static float readFloat(byte[] data, int index) {
        if (data == null || (data.length - index) < 4) {
            return 0x0;
        }

        return Float.intBitsToFloat(readInt(data, index));
    }
    public static float[] toFloatArray(byte[] data) {
        if (data == null || data.length % 4 != 0) {
            return null;
        }

        float[] floats = new float[data.length / 4];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = readFloat( new byte[] {
                    data[(i*4)],
                    data[(i*4)+1],
                    data[(i*4)+2],
                    data[(i*4)+3],
            }, 0);
        }
        return floats;
    }

    public static double readDouble(byte[] data, int index) {
        if (data == null || (data.length - index) < 8) {
            return 0x0;
        }

        return Double.longBitsToDouble(readLong(data, index));
    }
    public static double[] toDoubleArray(byte[] data) {
        if (data == null) {
            return null;
        }

        if (data.length % 8 != 0) return null;
        double[] doubles = new double[data.length / 8];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = readDouble( new byte[] {
                    data[(i*8)],
                    data[(i*8)+1],
                    data[(i*8)+2],
                    data[(i*8)+3],
                    data[(i*8)+4],
                    data[(i*8)+5],
                    data[(i*8)+6],
                    data[(i*8)+7],
            }, 0);
        }
        return doubles;
    }

    /**
     * Converts a byte-array to a string-array
     * @param bytes
     * @return
     */
    public static String readString(final byte[] bytes) {
        if(bytes == null) {
            return null;
        }

        return new String(bytes);
    }

    /**
     * Converts a byte-array to a string-array
     * @param bytes
     * @return
     */
    public static String[] toStringArray(final byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return null;
        }

        byte[] bBuff = new byte[4]; // Buffer

        System.arraycopy(bytes, 0, bBuff, 0, 4);
        int saLen = readInt(bBuff, 0);
        if (bytes.length < (4 + (saLen * 4))) return null;

        bBuff = new byte[saLen * 4];
        System.arraycopy(bytes, 4, bBuff, 0, bBuff.length);
        int[] sLens = toIntArray(bBuff);
        if (sLens == null) return null;

        String[] strings = new String[saLen];
        for (int i = 0, dataPos = 4 + (saLen * 4); i < saLen; i++) {
            if (sLens[i] > 0) {
                if (bytes.length >= (dataPos + sLens[i])) {
                    bBuff = new byte[sLens[i]];
                    System.arraycopy(bytes, dataPos, bBuff, 0, sLens[i]);
                    dataPos += sLens[i];
                    strings[i] = readString(bBuff);
                } else return null;
            }
        }

        return strings;
    }

    /**
     * Converts a byte-array to a boolean
     * @param bytes
     * @return
     */
    public static boolean readBoolean(final byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? false : bytes[0] != 0x00;
    }

    /**
     * Converts a byte-array to a boolean array
     * @param bytes
     * @return
     */
    public static boolean[] toBooleanArray(final byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return null;
        }

        int len = readInt(new byte[]{
                bytes[0],
                bytes[1],
                bytes[2],
                bytes[3]
        }, 0);

        boolean[] booleans = new boolean[len];
        for (int i = 0, j = 4, k = 7; i < booleans.length; i++) {
            booleans[i] = ((bytes[j] >> k--) & 0x01) == 1;
            if (k < 0) { j++; k = 7; }
        }

        return booleans;
    }
}
