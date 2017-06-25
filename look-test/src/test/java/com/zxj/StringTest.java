package com.zxj;

/**
 * user: xingjun.zhang
 * Date: 2016/8/14 0014
 */
public class StringTest {
    public static void main(String[] args) {
        String a = "abc";

        String b = "abc";

        String c = "abc".intern();

        String d = new String("abc");

        System.out.println(b == a);

        System.out.println(c == b);

        System.out.println(c == d);
    }
}
