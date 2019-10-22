package com.innovandoapps.library.kernel.httpUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class GenericVolley {

    protected RequestQueue mRequestQueue;

    public void setRequestQueue(Request request,int TIME_OUT){
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }
}
