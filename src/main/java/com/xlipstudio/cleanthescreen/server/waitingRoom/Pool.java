package com.xlipstudio.cleanthescreen.server.waitingRoom;

import com.xlipstudio.cleanthescreen.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Pool implements ClientHandler.ClientHandlerBacks {
    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public int addToPool(Socket socket) {
        ClientHandler handler = new ClientHandler(clientHandlers.size(), socket, this);
        handler.start();
        clientHandlers.add(handler);
        return clientHandlers.size();
    }

    @Override
    public void removeFromHandles(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        BaseLogger.LOGGER.info("Removed from Handlers " + clientHandler.getId());
    }

}
