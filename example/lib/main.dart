import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/flutter_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  TextEditingController _controller = TextEditingController();
  @override
  void initState() {
    super.initState();
    initPlatformState();
    //开启短信监听并设置筛选的短信关键词
    AutoVerfyCodePlugin.startCodeRead("Cashot");
    //自动填充到TextFormField
    AutoVerfyCodePlugin.setVerifyCode(_controller);
  }
  @override
  void dispose() {
    super.dispose();
    //取消短信监听
    AutoVerfyCodePlugin.stopCodeRead();
  }
  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await AutoVerfyCodePlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child:TextFormField(
            controller: _controller,
          ),
        ),
      ),
    );
  }
}
