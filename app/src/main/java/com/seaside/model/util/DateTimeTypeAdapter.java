package com.seaside.model.util;

import com.google.gson.*;
import com.seaside.utils.DUtils;
import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        DUtils.dLog("天眼实勘===>>>>:ResponseBody:" + src.toString());
        return new JsonPrimitive(src.toString());
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new DateTime(json.getAsString());
    }

}
