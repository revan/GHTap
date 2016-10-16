package io.revan;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

public class GHServer {

    public static void main(String[] args) throws InterruptedException,
            UnsupportedEncodingException,UnknownHostException,AWTException {

        final Map<KeyPressEvent.Button, Integer> keyMap = new HashMap<>();
        keyMap.put(KeyPressEvent.Button.GREEN, KeyEvent.VK_1);
        keyMap.put(KeyPressEvent.Button.RED, KeyEvent.VK_2);
        keyMap.put(KeyPressEvent.Button.YELLOW, KeyEvent.VK_3);
        keyMap.put(KeyPressEvent.Button.BLUE, KeyEvent.VK_4);
        keyMap.put(KeyPressEvent.Button.ORANGE, KeyEvent.VK_5);
        keyMap.put(KeyPressEvent.Button.STRUM_UP, KeyEvent.VK_6);
        keyMap.put(KeyPressEvent.Button.STRUM_DOWN, KeyEvent.VK_7);


        final Robot robot = new Robot();

        Configuration config = new Configuration();
        config.setHostname("192.168.0.108");
        config.setPort(9003);
//        config.setMaxFramePayloadLength(1024*1024);
//        config.setMaxHttpContentLength(1024*1024);

        final SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("press", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender)
                    throws Exception {
                System.out.print("received: ");
                System.out.println(data);

                KeyPressEvent event = KeyPressEvent.fromString(data);
                System.out.println(event);
                int key = keyMap.get(event.button);
                System.out.println(key);
                if (event.keyState == KeyPressEvent.KeyState.DOWN) {
                    robot.keyPress(key);
                } else {
                    robot.keyRelease(key);
                }
            }
        });

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("client connected! " + client.getSessionId());
            }
        });

        server.start();
        System.out.println("ready!");
        System.out.println(InetAddress.getLocalHost().toString());

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
