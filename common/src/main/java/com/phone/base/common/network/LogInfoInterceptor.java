package com.phone.base.common.network;

import com.phone.base.common.utils.LogToCloudUtils;
import com.phone.base.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/10/13
 */
public class LogInfoInterceptor implements Interceptor {
    private static final String TAG = "InfoInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originRequest = chain.request();
        RequestBody originRequestBody = originRequest.body();

        Request newRequest = null;
        if (originRequestBody != null) {
            if (!originRequest.url().toString().contains("/ota") && !originRequest.url().toString().contains("/updateImage")) {
                String originParams = getParams(originRequestBody);
                LogUtil.d(TAG, "intercept: request=" + originRequest.toString() + ", body=" + originParams);
                String requestLog = "request=" + originRequest.toString() + ", body=" + originParams;
                if (!(originRequest.url().toString().contains("/network") && originParams.equals("{\"action\":3}"))) {
                    LogToCloudUtils.INSTANCE.saveLogToFile(requestLog);
                }
                RequestBody newRequestBody = RequestBody.create(originRequestBody.contentType(), originParams);
                newRequest = originRequest.newBuilder()
                        .post(newRequestBody)
                        .build();
            }
        } else {
            LogUtil.d(TAG, "intercept: request=" + originRequest.toString());
        }

        Response response = newRequest == null ? chain.proceed(originRequest) : chain.proceed(newRequest);
        ResponseBody responseBody = response.body();
        if (responseBody != null && !originRequest.url().toString().contains("strut-firmware.s3.us-east-2.amazonaws.com")
                && !originRequest.url().toString().contains("/ota") && !originRequest.url().toString().contains("/updateImage")) {
            String responseData = responseBody.string();
            LogUtil.d(TAG, "intercept: response code=" + response.code() + ", body=" + responseData);
            String responseLog = "response code=" + response.code() + ", body=" + responseData;

            if (originRequestBody != null) {
                String originParams = getParams(originRequestBody);
                if (!(originRequest.url().toString().contains("/network") && originParams.equals("{\"action\":3}"))) {
                    LogToCloudUtils.INSTANCE.saveLogToFile(responseLog);
                }
            }
            //create new response
            ResponseBody newResponseBody = ResponseBody.create(responseBody.contentType(), responseData);
            response = response.newBuilder().body(newResponseBody).build();
            newResponseBody.close();
        } else {
            LogUtil.d(TAG, "intercept: response code=" + response.code());
        }
        return response;
    }

    private String getParams(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String params = "";
        try {
            requestBody.writeTo(buffer);
            params = buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer.close();
        }
        return params;
    }
}
