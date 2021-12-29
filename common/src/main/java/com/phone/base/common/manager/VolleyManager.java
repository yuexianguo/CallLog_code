package com.phone.base.common.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyManager {
    private static VolleyManager sVolleyManager;
    private RequestQueue requestQueue;

    public static VolleyManager getInstance(){
        if (null == sVolleyManager){
            synchronized (VolleyManager.class){
                if (null == sVolleyManager){
                    sVolleyManager = new VolleyManager();
                }
            }
        }
        return sVolleyManager;
    }
    public void init(Context context){
        if (null == requestQueue){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public void sendRequest(Request request){
        requestQueue.add(request);
    }
}
