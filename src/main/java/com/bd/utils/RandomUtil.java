package com.bd.utils;

import java.util.Random;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/24 17:26
 */
public class RandomUtil {
    private static final String RANDOM_STR="qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM0123456789";

    /**
     * @description: 随机生成 字符串
     * @param length: 字符串长度
     * @return java.lang.String
     * @author 夕雾
     * @date 2024/9/24 17:28
     */
    public static String generateStr(int length){
        Random random = new Random();
        StringBuilder sb=new StringBuilder();
        int index=-1;
        for (int i=0;i<length;i++) {
            index = random.nextInt(62);
            sb.append(RANDOM_STR.charAt(index));
        }
        return sb.toString();
    }
}
