package com.xlipstudio.cleanthescreen.server.server.handler;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.server.helper.ResponderHelper;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Pool implements ClientHandler.ClientHandlerBacks {
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    private PoolCallBacks poolCallBacks;
    public ResponderHelper responderHelper;


    public Pool(PoolCallBacks poolCallBacks) {
        this.poolCallBacks = poolCallBacks;
        this.responderHelper = ResponderHelper.getInstance();
    }

    public ClientHandler addToPool(Socket socket) {
        ClientHandler handler = new ClientHandler(clientHandlers.size(), socket, this);
        handler.start();
        clientHandlers.add(handler);
        poolCallBacks.newJoin(handler);
        return handler;
    }

    private ClientHandler addToPool(ClientHandler clientHandler) {
        clientHandler.setId(clientHandlers.size());
        clientHandlers.add(clientHandler);
        welcomeClientHander(clientHandler);
        poolCallBacks.newJoin(clientHandler);
        return clientHandler;
    }

    private void welcomeClientHander(ClientHandler handler) {
        Wrap welcomeResponse = poolCallBacks.welcomeResponse();
        if(welcomeResponse != null){
            handler.dispatch(welcomeResponse);
        }
    }

    @Override
    public void removeFromHandles(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }


    public void dispatchToPool(Wrap wrap) {
        for (ClientHandler handler :
                clientHandlers) {
            handler.dispatch(wrap);
        }

    }

    public ClientHandler moveToPool(ClientHandler clientHandler, Pool target) {
        clientHandlers.remove(clientHandler);
        return target.addToPool(clientHandler.changeClientHandlerBacks(target));
    }

    public void moveAllToPool(Pool target) {
        for (ClientHandler handler : clientHandlers) {
            moveToPool(handler, target);
        }
    }

    @Override
    public void wrapReceived(Wrap wrap, ClientHandler from) {
        this.poolCallBacks.wrapReceived(wrap, from);
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public interface PoolCallBacks {
        void wrapReceived(Wrap wrap, ClientHandler from);
        void newJoin(ClientHandler clientHandler);
        Wrap welcomeResponse();
    }
}
