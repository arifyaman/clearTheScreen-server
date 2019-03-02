package com.xlipstudio.cleanthescreen.server.server.handler;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.hibernate.model.User;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler extends Thread {
    private long id;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean dead;
    private ClientHandlerBacks clientHandlerBacks;
    private User user;


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
                if(wrap.getRequest() != null) {
                    BaseLogger.LOGGER.info("Client: " + getId() + " TYPE: " + wrap.getRequest().getRequestType());
                }
                clientHandlerBacks.wrapReceived(wrap, this);
            } catch (IOException e) {
                if (e.getClass().getName().equals("java.io.EOFException")) {
                    dead = true;
                    destroy();
                    clientHandlerBacks.removeFromHandles(this);
                    BaseLogger.LOGGER.info("Removed from Handlers " + getId());
                    break;
                }
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void dispatch(Wrap wrap) {
        try {
            outputStream.writeObject(wrap);
        } catch (IOException e) {
            BaseLogger.LOGGER.log(Level.SEVERE, " Error Dispatch: " , e);
            e.printStackTrace();
        }
    }

    public ClientHandler changeClientHandlerBacks(ClientHandlerBacks clientHandlerBacks) {
        this.clientHandlerBacks = clientHandlerBacks;
        return this;
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

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public interface ClientHandlerBacks {
        void removeFromHandles(ClientHandler clientHandler);
        void wrapReceived(Wrap wrap, ClientHandler from);
    }
}
