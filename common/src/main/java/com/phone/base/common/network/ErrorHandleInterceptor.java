package com.phone.base.common.network;

import com.phone.base.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/11/06
 */
public class ErrorHandleInterceptor implements Interceptor {
    private static final String TAG = "ErrorHandleInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originRequest = chain.request();
        Response response = chain.proceed(originRequest);
        ResponseBody body = response.body();
        if (body != null) {
            String json = body.string();
            LogUtil.d(TAG, "response=" + json);
            try {
                JSONObject jsonObject = new JSONObject(json);
                int result = jsonObject.optInt("result", 0);
                switch (result) {
                    case 0:
                        break;
                    case -1:
                        break;
                    case -2:
                        break;
                    case -3:
                        break;
                    case -4:
                        break;
                    case -5:
                        chain.proceed(originRequest);
                        break;
                    default:
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
