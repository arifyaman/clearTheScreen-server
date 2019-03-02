package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.room.rule.WaitingRoomWrapHandler;

@WrapHandlerRule(ruleClass = WaitingRoomWrapHandler.class)
public class WaitingRoom extends Room {
    private static WaitingRoom instance = new WaitingRoom();

    public WaitingRoom() {
        super();
    }

    public static WaitingRoom getInstance() {
        return instance;
    }


    @Override
    public Wrap welcomeResponse() {
        Response response = new Response(true, "Waiting room accepted", "1");


        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.RESPONSE);
        wrap.setResponse(response);
        return wrap;
    }
}
