package com.seaside.utils;

import android.text.TextUtils;

/**
 * Createed by Deven
 * on 2018/12/14.
 * Descibe: describe
 */
public class StringUtils {
    /**
     * 为空返回空字符串
     * @param src 字符串
     * @return 为空返回空字符串
     */
    public static String isNull(String src) {
        return TextUtils.isEmpty(src) ? "" : src;
    }


    /**
     * 为空返回自定义字符串
     * @param src 字符串
     * @param defaultStr 为空返回的字符串
     * @return
     */
    public static String isNull(String src, String defaultStr) {
        return TextUtils.isEmpty(src) ? defaultStr : src;
    }
}
