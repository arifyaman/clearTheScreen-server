package com.xlipstudio.cleanthescreen.server.test;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameClient {
    private Socket socket = null;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    public GameClient() {

        try {
            socket = new Socket("localhost", 36813);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            join();
            new Listener().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class Listener extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Wrap wrap = ((Wrap) inputStream.readObject());
                    System.out.println(wrap.getWrapType());
                } catch (IOException e) {

                }catch (Exception e2) {

                }

            }
        }
    }

    public void join() {
       Wrap wrap = new Wrap();
       wrap.setWrapType(WrapType.JOIN);
       dispatchWrap(wrap);
        System.out.println("Sent join req");
    }

    public void dispatchWrap(Wrap wrap) {
        try {
            outputStream.writeObject(wrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
