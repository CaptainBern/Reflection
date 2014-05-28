package com.captainbern.jbel;

import org.junit.Test;

public class ByteVectorTest {

    @Test
    public void testByteVector() {
        ByteVector vector = new ByteVector(1);
        vector.putShort((short) -15);

        for (byte b : vector.getBytes()) {
            System.out.println(b);
        }

        System.out.println("---------------------");
    }
}