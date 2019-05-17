package com.example.mezilajanm;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppVolleySingleton {

    private static AppVolleySingleton mInstance;
    private static RequestQueue mRequestQueue;
    private Context mContext;

    public AppVolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized AppVolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppVolleySingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}
