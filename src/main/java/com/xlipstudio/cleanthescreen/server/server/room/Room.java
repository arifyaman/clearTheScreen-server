package com.xlipstudio.cleanthescreen.server.server.room;

import com.google.gson.Gson;
import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.helper.ResponderHelper;
import com.xlipstudio.cleanthescreen.server.server.room.rule.BaseWrapHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.RuleHolder;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Pool.PoolCallBacks {
    protected Pool pool;
    protected ResponderHelper responderHelper;
    protected Gson gson;

    public Room() {
        this.pool = new Pool(this);
        this.responderHelper = ResponderHelper.getInstance();
        this.gson = new Gson();
    }

    public ClientHandler addToPool(Socket socket) {
        ClientHandler clientHandler = pool.addToPool(socket);
        return clientHandler;
    }

    public ClientHandler moveToRoom(ClientHandler clientHandler, Room target) {
        ClientHandler created = pool.moveToPool(clientHandler, target.getPool());
        return created;
    }

    public void moveAllToRoom(Room target) {
        List<ClientHandler> temp = new ArrayList<>(pool.getClientHandlers());

        for (ClientHandler clientHandler : temp) {
            moveToRoom(clientHandler, target);
        }
        temp.clear();
    }

    public Pool getPool() {
        return pool;
    }


    public void dispatchToPool(Wrap wrap) {
        this.pool.dispatchToPool(wrap);
    }

    private void welcomeClientHander(ClientHandler clientHandler) {
        Wrap response = welcomeResponse();
        if (response != null) {
            clientHandler.dispatch(response);
        }

    }

    @Override
    public void disconnected(ClientHandler clientHandler) {

    }

    public Wrap welcomeResponse() {
        return null;
    }


    @Override
    public void wrapReceived(Wrap wrap, ClientHandler from) {
        try {
            BaseWrapHandler baseWrapHandler = RuleHolder.getInstance().getByClass(this.getClass().getAnnotation(WrapHandlerRule.class).ruleClass());
            baseWrapHandler.setOriginRoom(this);
            baseWrapHandler.handleWrap(wrap, from, pool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newJoin(ClientHandler clientHandler) {

    }
}
