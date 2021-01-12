import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class AutoVerfyCodePlugin {
  static const MethodChannel _channel = const MethodChannel('flutter_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void startCodeRead(String keyword) {
    _channel.invokeMethod("startCodeRead", keyword);
  }

  static void stopCodeRead() {
    _channel.invokeMethod("stopCodeRead");
  }

  static void setVerifyCode(TextEditingController controller){
    _channel.setMethodCallHandler((call) async {
      if (call.method == "receiveCode" ) {
        String content = await call.arguments['code'];
        if(content!=null){
          controller.text = content;
        }
      }
    });
  }
  static void onReceiveCode(Function(String data) onReceive){
    _channel.setMethodCallHandler((call) async {
      if (call.method == "receiveCode" ) {
        String content = await call.arguments['code'];
        if(content!=null){
          onReceive(content);
        }
      }
    });
  }
}
