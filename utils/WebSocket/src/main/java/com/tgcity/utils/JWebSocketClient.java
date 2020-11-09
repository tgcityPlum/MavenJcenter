package com.tgcity.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author TGCity
 * @date 2020/8/3
 * @description
 */
public class JWebSocketClient extends WebSocketClient {

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogUtils.e("JWebSocketClient onOpen()");
    }

    @Override
    public void onMessage(String message) {
        LogUtils.e("JWebSocketClient message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtils.e("JWebSocketClient onClose reason:" + reason);
    }

    @Override
    public void onError(Exception ex) {
        LogUtils.e("JWebSocketClient onError " + ex.toString());
    }
}
