package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;

import java.util.ArrayList;
import java.util.List;

public class RuleHolder {
    private static RuleHolder instance = new RuleHolder();

    public static RuleHolder getInstance() {
        return instance;
    }


    private List<BaseWrapHandler> wrapHandlers;

    public RuleHolder() {
        wrapHandlers = new ArrayList<>();
        wrapHandlers.add(new BaseWrapHandler() {
            @Override
            public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
                return null;
            }
        });
        wrapHandlers.add(new RegistrationWrapHandler());
        wrapHandlers.add(new WaitingRoomWrapHandler());
        wrapHandlers.add(new GameRoomWrapHandler());

    }

    public BaseWrapHandler getByClass(Class type) {

        for (BaseWrapHandler handler : wrapHandlers) {
            if (handler.getClass().equals(type)) {
                return handler;
            }

        }
        return getByClass(BaseWrapHandler.class);
    }


}
