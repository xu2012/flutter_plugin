package com.xyl.flutter_plugin.code.callback;

/**
 * 权限回调
 * Created by xyl 2020-12-24 17:57:37.
 */

public interface PermissionCallBack {
    
    void onSuccess();

    /**
     * 回调获取短信权限失败
     * @return 为真，则重试。假则返回。
     */
    boolean onFail();
    
}
