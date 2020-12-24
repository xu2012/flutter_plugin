import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPlugin {
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

  static void setCodeCallback(
      Future<dynamic> Function(MethodCall call) handler) {
    _channel.setMethodCallHandler((call) async {
      if (call.method == "receiveCode") {
        handler.call(call);
      }
    });
  }
}
