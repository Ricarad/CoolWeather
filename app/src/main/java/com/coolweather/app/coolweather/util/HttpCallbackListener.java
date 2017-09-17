package com.coolweather.app.coolweather.util;

/**
 * Created by dongdong on 2017/9/17.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
