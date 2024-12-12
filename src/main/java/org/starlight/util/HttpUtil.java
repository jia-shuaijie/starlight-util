package org.starlight.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author 黑色的小火苗
 */

public class HttpUtil {
    private final static OkHttpClient CLIENT = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    /**
     * get请求
     *
     * @param url 请求地址
     * @return String类型的返回结果
     */
    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return execute(request);
    }

    /**
     * get请求 携带请求头参数
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return String类型的返回结果
     */
    public static String get(String url, Map<String, String> headers) {
        Request request = getHeaderBuilder(headers).url(url).build();
        return execute(request);
    }

    /**
     * post请求 body形式传参
     *
     * @param url  请求地址
     * @param json 请求JSON字符串
     * @return String类型返回结果
     */
    public static String post(String url, String json) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON))
                .build();
        return execute(request);
    }

    /**
     * post请求 formData形式传参
     *
     * @param url        请求地址
     * @param formParams 请求参数-表单形式传参
     * @return String类型返回结果
     */
    public static String post(String url, Map<String, String> formParams) {
        Request request = new Request.Builder()
                .url(url)
                .post(mapToBody(formParams))
                .build();
        return execute(request);
    }

    /**
     * post请求 formData形式传参 携带请求头
     *
     * @param url        请求地址
     * @param headers    请求头
     * @param formParams 请求参数-表单形式传参
     * @return String类型返回结果
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> formParams) {
        Request request = getHeaderBuilder(headers)
                .url(url)
                .post(mapToBody(formParams)).build();
        return execute(request);
    }

    /**
     * post请求 body形式传参 携带请求头
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param json    请求JSON字符串
     * @return String类型返回结果
     */
    public static String post(String url, Map<String, String> headers, String json) {
        Request request = getHeaderBuilder(headers)
                .url(url)
                .post(RequestBody.create(json, JSON))
                .build();
        return execute(request);
    }

    /**
     * 将Map转换为FormBody
     *
     * @param formParams 请求参数
     * @return FormBody
     */
    private static FormBody mapToBody(Map<String, String> formParams) {
        FormBody.Builder builder = new FormBody.Builder();
        formParams.forEach(builder::add);
        return builder.build();
    }

    /**
     * 构建请求头
     *
     * @param headers 请求头
     * @return Request.Builder
     */
    private static Request.Builder getHeaderBuilder(Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::addHeader);
        return builder;
    }

    /**
     * 执行request返回String结果
     *
     * @param request 请求
     * @return String结果
     */
    private static String execute(Request request) {
        Response response;
        try {
            response = CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful() || response.body() == null) {
            return "";
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            return "";
        }
    }

}
