package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.conf.ServerConfigurations;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.GameRoomWrapHandler;

@WrapHandlerRule(ruleClass = GameRoomWrapHandler.class)
public class GameRoom extends Room {
    private boolean waitingPlayer;

    private boolean gameFinished;

    public GameRoom() {
        super();
        this.waitingPlayer = true;
    }

    @Override
    public void newJoin(ClientHandler clientHandler) {
        if(pool.getClientHandlers().size() == 2) {
            this.waitingPlayer = false;
        }
    }

    @Override
    public Wrap welcomeResponse() {
        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.RESPONSE);
        Response response = new Response(true,"Welcome to game room","1");
        response.setPayload(ServerConfigurations.getIntance().gameConf);
        wrap.setResponse(response);
        return wrap;
    }

    public boolean isWaitingPlayer() {
        return waitingPlayer;
    }
}
