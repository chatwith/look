package com.look.str;

/**
 * user: xingjun.zhang
 * Date: 2017/1/2 0002
 */
public class ForTest {
    public static void main(String[] args) {
       loop: for (int i =0 ; i < 5 ; i++) {
            for (int k = 0; k < 10 ;k ++) {
                if (k == 5) {
                    break loop;
                }else {
                    System.out.println(k);
                }
            }
            System.out.println(i + "----------");
        }
    }
}
