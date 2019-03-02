package com.xlipstudio.cleanthescreen.server.server.room;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.helper.ResponderHelper;
import com.xlipstudio.cleanthescreen.server.server.room.rule.BaseWrapHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.RuleHolder;

import java.net.Socket;

public abstract class Room implements Pool.PoolCallBacks {
    protected Pool pool;
    protected ResponderHelper responderHelper;

    public Room() {
        this.pool = new Pool(this);
        this.responderHelper = ResponderHelper.getInstance();
    }

    public ClientHandler addToPool(Socket socket) {
        ClientHandler clientHandler = pool.addToPool(socket);
        welcomeClientHander(clientHandler);
        return clientHandler;
    }

    public ClientHandler moveToRoom(ClientHandler clientHandler, Room target) {
        ClientHandler created = pool.moveToPool(clientHandler, target.getPool());
        target.welcomeClientHander(created);
        return created;
    }

    public void moveAllToRoom(Room target) {
        pool.moveAllToPool(target.getPool());
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

    public Wrap welcomeResponse() {
        return null;
    }


    @Override
    public void wrapReceived(Wrap wrap, ClientHandler from) {
        try {
            BaseWrapHandler baseWrapHandler = RuleHolder.getInstance().getByClass(this.getClass().getAnnotation(WrapHandlerRule.class).ruleClass());
            baseWrapHandler.handleWrap(wrap, from, pool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
