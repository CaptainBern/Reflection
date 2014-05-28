package com.captainbern.jbel;

import org.junit.Test;

public class ByteVectorTest {

    @Test
    public void testByteVector() {
         ByteVector test1 = new ByteVector();
         test1.putLong(2014L);

        for (byte b : test1.getBytes()) {
            System.out.println(b);
        }

        System.out.println("TESTING SECOND METHOD");

        ByteVector test2 = new ByteVector();
        test2.putLong2(2014L);

        for (byte b : test2.getBytes()) {
            System.out.println(b);
        }
    }
}