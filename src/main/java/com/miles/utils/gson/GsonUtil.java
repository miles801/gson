package com.miles.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * gson的工具类
 *
 * @author miles
 */
public class GsonUtil {

    /**
     * 将一个对象转成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return toJsonExclude(obj);
    }

    /**
     * 将一个对象转成json字符串并指定需要排除的属性名称列表
     * 如果没有指定属性名称集合，则将会全部转换
     * 默认时间会以yyyy-MM-dd HH:mm:ss的格式进行转换
     *
     * @param obj
     * @return
     */
    public static String toJsonExclude(Object obj, String... exclusionFields) {
        validateJsonObject(obj);
        //创建GsonBuilder
        GsonBuilder builder = new GsonBuilder();

        //设置时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        //设置需要被排除的属性列表
        if (exclusionFields != null && exclusionFields.length > 0) {
            GsonExclusion gsonFilter = new GsonExclusion();
            gsonFilter.addExclusionField(exclusionFields);
            builder.setExclusionStrategies(gsonFilter);
        }

        //创建Gson并进行转换
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    /**
     * 将一个对象转成json字符串并指定需要需要转换的属性名称列表
     * 如果没有指定属性名称集合，则将会全部转换
     * 默认时间会以yyyy-MM-dd HH:mm:ss的格式进行转换
     *
     * @param obj
     * @return
     */
    public static String toJsonInclude(Object obj, String... includeFields) {
        validateJsonObject(obj);
        //创建GsonBuilder
        GsonBuilder builder = new GsonBuilder();

        //设置时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        //设置需要转换的属性名称
        if (includeFields != null && includeFields.length > 0) {
            GsonInclusion gsonFilter = new GsonInclusion();
            gsonFilter.addInclusionFields(includeFields);
            builder.setExclusionStrategies(gsonFilter);
        }

        //创建Gson并进行转换
        Gson gson = builder.create();
        return gson.toJson(obj);
    }


    /**
     * 通过response输出json数据
     *
     * @param response HttpServletResponse对象
     * @param json     json字符串
     */
    public static void printJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json");
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static Map<String, Object> convertJson2Map(String json) {
        if (json == null) return null;
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, HashMap.class);
    }

    public static void printJson(HttpServletResponse response, String key, String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        printJson(response, jsonObject.toString());
    }

    public static void printJson(HttpServletResponse response, String key, Integer value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        printJson(response, jsonObject.toString());
    }

    public static void printJson(HttpServletResponse response, String key, Float value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        printJson(response, jsonObject.toString());
    }

    public static void printJson(HttpServletResponse response, String key, Double value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        printJson(response, jsonObject.toString());
    }

    public static void printJson(HttpServletResponse response, String key, Boolean value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        printJson(response, jsonObject.toString());
    }

    /**
     * 通过response输出json数据
     *
     * @param response HttpServletResponse对象
     * @param obj      object
     */
    public static void printJsonObject(HttpServletResponse response, Object obj) {
        if (obj == null) return;
        if (obj instanceof String) {
            printJson(response, (String) obj);
            return;
        }
        String json = toJson(obj);
        printJson(response, json);

    }

    public static void printError(HttpServletResponse response, String reason) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("error", reason);
        printJson(response, jsonObject.toString());
    }

    private static void validateJsonObject(Object obj) {
        if (obj == null) {
            throw new NullPointerException("要转成json的对象不能为空！");
        }
        if (obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
            throw new RuntimeException("要转成json字符串的必须是复杂(引用)类型的对象！");
        }
    }
}
