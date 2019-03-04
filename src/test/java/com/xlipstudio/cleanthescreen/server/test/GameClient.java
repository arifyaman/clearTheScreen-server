package com.xlipstudio.cleanthescreen.server.test;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.Request;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameClient extends Thread{
    private Socket socket = null;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final String clientId = "76a8a79duo12ı31n2m";

    private static GameClient instance = new GameClient();

    public static GameClient getInstance() {
        return instance;
    }

    public GameClient() {

        try {
            socket = new Socket("localhost", 36813);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            register();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Wrap wrap = ((Wrap) inputStream.readObject());
                String log = wrap.getResponse().getMessage() + "  "+wrap.getResponse().getCode();
                if(wrap.getResponse().getPayload() != null) {
                    log += "  " + wrap.getResponse().getPayload().getClass().getName();
                }

                System.out.println( log);
            } catch (IOException e) {

            }catch (Exception e2) {

            }

        }
    }

    public void register() {
        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.REQUEST);

        Request request = new Request();
        request.setRequestType(RequestType.REGISTER);
        request.setPayload(clientId);
        wrap.setRequest(request);
        dispatchWrap(wrap);
    }

    public void dispatchWrap(Wrap wrap) {
        try {
            outputStream.writeObject(wrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
