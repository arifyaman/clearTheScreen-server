package com.xlipstudio.cleanthescreen.server.handler;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private long id;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean dead;
    private ClientHandlerBacks clientHandlerBacks;


    public ClientHandler(long id, Socket clientSocket, ClientHandlerBacks clientHandlerBacks) {
        this.clientSocket = clientSocket;
        this.clientHandlerBacks = clientHandlerBacks;
        this.id = id;
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
          //  e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {

            try {
                Wrap wrap = ((Wrap) inputStream.readObject());
                BaseLogger.LOGGER.info("Client: "+ getId() + " TYPE: "+ wrap.getWrapType());
                System.out.println();
            } catch (IOException e) {
                if(e.getClass().getName().equals("java.io.EOFException")) {
                    dead = true;
                    destroy();
                    clientHandlerBacks.removeFromHandles(this);
                    break;
                }
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        try {
            this.outputStream.close();
            this.inputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public long getId() {
        return id;
    }

    public interface ClientHandlerBacks{
        void removeFromHandles(ClientHandler clientHandler);

    }
}
