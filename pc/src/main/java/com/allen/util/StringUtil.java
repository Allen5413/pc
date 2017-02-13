package com.allen.util;

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
}
