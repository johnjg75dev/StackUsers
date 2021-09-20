package com.jgallegos.stackusers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkSingleton {
    private static NetworkSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private NetworkSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Gets or creates an instance of our NetworkSingleton
     */
    public static synchronized NetworkSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkSingleton(context);
        }
        return instance;
    }

    /**
     * Returns our RequestQueue object
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds a request to our RequestQueue
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
