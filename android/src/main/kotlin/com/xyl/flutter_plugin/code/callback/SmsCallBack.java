package com.xyl.flutter_plugin.code.callback;

import androidx.annotation.Nullable;

/**
 * 短信消息回调
 * Created by xyl 2020-12-24 17:57:37.
 */

public abstract class SmsCallBack {
    
    public void onGetMessage(String mess){}
    
    public void onGetCode(String code){}
    
    public void onGetSender(@Nullable String phoneNumber){}
    
}
