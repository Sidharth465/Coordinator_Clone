package io.siddharth.myapplication.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
    private String msgToSend = "";
    private boolean isRunning = true;

    @Override
    public void run() {
        Socket socket = null;
        DataOutputStream out = null;
        try {
            // Replace with your server's actual IP and Port
            socket = new Socket("192.168.1.1", 8080);
            out = new DataOutputStream(socket.getOutputStream());

            while (isRunning) {
                if (!msgToSend.isEmpty()) {
                    byte[] bytes = msgToSend.getBytes();
                    out.writeInt(bytes.length);
                    out.write(bytes);
                    out.flush();
                    msgToSend = "";
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (socket != null) socket.close(); } catch (IOException e) {}
        }
    }

    public void sendMsg(String msg) {
        this.msgToSend = msg;
    }

    public void disconnect() {
        this.isRunning = false;
    }
}