# WebSocket

## 模块说明
WebSocket是一个WebSocket监听类

## 使用说明

* 调用方法
```
val uri = URI.create(NetworkModeConstant.Service.SERVICE_WS + BaseModeApplication.getPreferencesUtils().getString(BaseFileModeConstant.SP.USER_ID))
        client = object : JWebSocketClient(uri) {
            override fun onMessage(message: String) {
                //message就是接收到的消息
                LogUtils.e("JWebSClientService $message")
                if (!StringUtils.isEmpty(message)) {
                    try {
                        val webSocketOutput = Gson().fromJson<QrCodeWebSocketOutput>(message, QrCodeWebSocketOutput::class.java)
                        if (webSocketOutput.msgType == "02" && webSocketOutput.message != null) {
                            runOnUiThread {
                                showDriverReceivePopWindow(webSocketOutput.message.name, webSocketOutput.message.number)
                            }
                        }
                    } catch (e: Exception) {
                        LogUtils.e("JWebSClientService $e")
                    }
                }
            }
        }

        Thread(Runnable {
            try {
                client?.connectBlocking()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()



        private fun closeConnect() {
                try {
                    if (client != null) {
                        client!!.close()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    client = null
                }
            }
```

 
