package com.xyl.flutter_plugin.code.callback;

/**
 * 收到短信接口回调
 * Created by xyl 2020-12-24 17:57:37.
 */

public interface GetMessageListener {
    
    boolean onGetMessageInfo(String sender, String message);
    
}
