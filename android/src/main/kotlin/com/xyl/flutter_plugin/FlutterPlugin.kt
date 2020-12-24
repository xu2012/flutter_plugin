package com.xyl.flutter_plugin

import android.content.Context
import androidx.annotation.NonNull
import com.xyl.flutter_plugin.code.AutoVerifyCode
import com.xyl.flutter_plugin.code.AutoVerifyCodeConfig
import com.xyl.flutter_plugin.code.callback.SmsCallBack

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** FlutterPlugin */
class FlutterPlugin: FlutterPlugin, MethodCallHandler , ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_plugin")
    channel.setMethodCallHandler(this)
    context  = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if(call.method =="startCodeRead"){
        startCodeRead()
    }else if(call.method == "stopCodeRead"){
      stopCodeRead()
    } else {
      result.notImplemented()
    }
  }
  private fun startCodeRead(){
    val config = AutoVerifyCodeConfig.Builder()
            .smsCodeType(AutoVerifyCodeConfig.CODE_TYPE_NUMBER)
            .smsBodyContains("Cashot")
//                .smsBodyStartWith("")
            .build()
    AutoVerifyCode.getInstance()
            .with(context)
            .config(config)
            .smsCallback(object: SmsCallBack(){
              override fun onGetMessage(mess: String?) {
                super.onGetMessage(mess)
//                        val map = HashMap<String,String>()
//                        map.put("message","$mess")
//                        platform.invokeMethod("receiveMessage",map,null)
              }

              override fun onGetCode(code: String?) {
                super.onGetCode(code)
                val map = HashMap<String,String>()
                map.put("code","$code")
                channel.invokeMethod("receiveCode",map,null)
              }

              override fun onGetSender(phoneNumber: String?) {
                super.onGetSender(phoneNumber)
              }
            })
            .start()
  }
  private fun stopCodeRead(){

  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onDetachedFromActivity() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
  }

  override fun onDetachedFromActivityForConfigChanges() {
  }
}
