package com.zxj;

import java.io.IOException;

/**
 * user: xingjun.zhang
 * Date: 2016/7/10 0010
 */
public class TestBigData {

    public static void main(String[] args) throws IOException {
//        int f[] = new int[13];
//        f[0] = 1;
//        for (int i = 1; i < 13; i++)
//            f[i] = f[i - 1] * 5;
//        int tc = 21;
//        try {
//            tc = System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int n = 5;
//        while (tc-- > 0) {
//            n = n + 1;
//            int tmp, sum = 0;
//            for (int i = 1; i < 13; i++) {
//
//                if ((tmp = n / f[i]) == 0)
//                    break;
//                System.out.println("f i : " + i + " si :" + f[i]);
//                System.out.println("temp" + tmp);
//                sum += tmp;//直接加上存在的5的幂指数就能算出答案
//            }
//            System.out.println("sum : " + (sum));
//        }
        buf[0] = '1';
        buf[1] = '2';
        for (int l = 1; l < 3; l++) {
            buf[l] = '0';
        }

        System.out.println(lastdigit1());
    }

    static int MAXN = 10000;
    static char buf[] = new char[1000];
    static int mod[] = {1, 1, 2, 6, 4, 2, 2, 4, 2, 8, 4,
            4, 8, 4, 6, 8, 8, 6, 8, 2};//最后末尾只可能出现这几个数

    public static int lastdigit() {

        int len = 1000, i, c, ret = 1;
        int a[] = new int[MAXN];
        if (len == 1)
            return mod[buf[0] - '0'];
        for (i = 0; i < len; i++)
            a[i] = buf[len - 1 - i] - '0';
        for (; len - a[len - 1] != 0; ) {
            len = len - a[len - 1];
            ret = ret * mod[a[1] % 2 * 10 + a[0]] % 5;

            for (c = 0, i = len - 1; i >= 0; i--) {
                c = c * 10 + a[i];
                a[i] = c / 5;
                c %= 5;
            }
        }//对2和5进行模除
        return ret + ret % 2 * 5;//算出结果
    }

    public static int lastdigit1() {
        int len = 3,  c=0;
        int ret = 1;
        int a[] = new int[MAXN];
        if (len == 1) return mod[buf[0] - '0'];
        for (int i = 0; i < len; i++) {
            a[i] = buf[i] - '0';
        }
        for (int j = 0; j< 21;j++){
            System.out.print(a[j]);
        }
        for (int p =0; p<len ;p++) {
            ret = ret * mod[a[1] % 2 * 10 + a[0]] % 5;
            for (int i = len - 1; i >= 0; i--) {
                c = c * 10 + a[i];
                a[i] = c / 5;
                c %= 5;
            }
        }
        System.out.println();
        return ret + ret % 2 * 5;
    }
}
