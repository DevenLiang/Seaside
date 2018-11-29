package com.seaside.model.api;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class StringConverterFactory extends Converter.Factory {

    public static StringConverterFactory create(){
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new StringConverter();
        }
        //其它类型我们不处理，返回null就行
        return null;
    }
}
