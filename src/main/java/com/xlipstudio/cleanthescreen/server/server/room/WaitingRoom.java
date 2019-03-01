package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;

import java.net.Socket;

public class WaitingRoom extends Room {

    private static WaitingRoom instance = new WaitingRoom();

    public WaitingRoom() {
        super();
    }

    public static WaitingRoom getInstance() {
        return instance;
    }


    @Override
    public ClientHandler addToPool(Socket socket) {
      ClientHandler handler = super.addToPool(socket);
      handler.dispatch(response());
      return handler;
    }

    private Wrap response() {
        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.RESPONSE);
        return wrap;
    }


    @Override
    public void wrapReceived(Wrap wrap, ClientHandler from) {

    }
}
