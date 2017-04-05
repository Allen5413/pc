package com.allen.util;

import java.text.DecimalFormat;

/**
 * Created by Allen on 2016/12/19.
 */
public class StringUtil {
    public static boolean isEmpty(String str){
        if(null == str){
            return true;
        }else{
            str = str.trim();
            if("".equals(str) || 1 > str.length()){
                return true;
            }else{
                return false;
            }
        }
    }
    private static final String STR_FORMAT = "00000000";

    public static String haoAddOne_2(String liuShuiHao){
        Integer intHao = Integer.parseInt(liuShuiHao);
        intHao++;
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(intHao);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.haoAddOne_2("313"));
    }
}
