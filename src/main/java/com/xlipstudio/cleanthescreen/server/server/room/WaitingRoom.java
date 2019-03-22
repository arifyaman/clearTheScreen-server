package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.game.GameSaloon;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.WaitingRoomWrapHandler;

import java.util.ArrayList;
import java.util.List;

@WrapHandlerRule(ruleClass = WaitingRoomWrapHandler.class)
public class WaitingRoom extends Room {
    private static WaitingRoom instance = new WaitingRoom();

    private List<ClientHandler> waitingHandlers;

    public WaitingRoom() {
        super();
        waitingHandlers = new ArrayList<>();
    }

    public static WaitingRoom getInstance() {
        return instance;
    }


    @Override
    public Wrap welcomeResponse(ClientHandler clientHandler) {
        Response response = new Response(true, "Waiting room accepted", "1");
        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.RESPONSE);
        wrap.setResponse(response);
        return wrap;
    }

    @Override
    public void newJoin(ClientHandler clientHandler) {
        if (pool.getClientHandlers().size() == 2) {
            moveHandlersToGame();
            pool.getClientHandlers().clear();
        }

    }


    private void moveHandlersToGame() {
        moveAllToRoom(GameSaloon.getInstance().getAvailableRoom());
    }
}
