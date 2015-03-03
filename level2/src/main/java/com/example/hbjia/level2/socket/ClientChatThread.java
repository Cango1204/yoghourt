package com.example.hbjia.level2.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2015/3/3.
 */
public class ClientChatThread implements Runnable{

    private Handler mHandler;
    private String mEditMsg;

    public ClientChatThread(Handler handler, String editMsg) {
        this.mHandler = handler;
        this.mEditMsg = editMsg;
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        bundle.clear();
        Message message = new Message();
        try {
            //与服务器建立连接
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("192.168.123.1", 8889), 5000);
            //读取来自服务器的信息
            OutputStream os = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = null;
            String buffer = "";
            while((line = br.readLine()) != null) {
                buffer = line + buffer;
            }

            //向服务器发送信息
            os.write(mEditMsg.getBytes("gbk"));
            os.flush();

            //更新UI
            message.what = 0x110;
            bundle.putString("serverMessage", buffer);
            message.setData(bundle);
            mHandler.sendMessage(message);

            //关闭各种流
            br.close();
            os.close();
            socket.close();

        } catch (SocketTimeoutException exception) {
            //连接超时
            message.what = 0x119;
            bundle.putString("serverMessage", "服务器连接超时！");
            message.setData(bundle);
            mHandler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
