package com.look.str;

/**
 * user: xingjun.zhang
 * Date: 2016/12/24 0024
 */
public class AlgorithmTest {

    public static void main(String[] args) {
        int a = 256;

        // 10000100  >>>

        System.out.println(a >> 1);

        System.out.println(a << 2);

        System.out.println(a >>> 4);

        System.out.println(8 << 2);

        System.out.println(8 >>> 2);

        System.out.println(2 << 4 >>> 2);


        System.out.println(2 << 8 - 4 >>> 2);

        int c = 2 | 2 << 8 - 4 >>> 2;

        System.out.println(c);

        int b = 2 | 8 >>>2 << 8 - 4 >>> 2;

        byte d = 'A';

        System.out.println(d);
    }
}
