package com.seaside.model.api;

import com.seaside.utils.DUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class StringConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        String string = value.string();
        DUtils.dLog("天眼实勘===>>>>:ResponseBody:" + string);
        return string;
    }
}
