package com.look.str;

/**
 * user: xingjun.zhang
 * Date: 2016/12/3 0003
 */
public class InternTest {
    public static void main(String[] args) {

        String other = "abc";

        String str = new String("abc");

        String intern  = "abc".intern();

        System.out.println(other==intern);

        System.out.println(intern==str);
    }
}
