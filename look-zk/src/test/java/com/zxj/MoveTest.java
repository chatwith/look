package com.zxj;

/**
 * user: xingjun.zhang
 * Date: 2016/7/17 0017
 */
public class MoveTest {
    public static void main(String[] args) {
        int k = 1;
        for (int i = 0; i < 26; i++) {
            k = k << 1;
            System.out.println(k);
        }
        int p = 1048576 & 2097152;
        System.out.println(4194304 | p);
    }
}
