package com.seaside.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public final class EntityUtils {

    private EntityUtils() {}

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
            .create();

}
