package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;

@AllowedReqTypes(types = {RequestType.EXIT, RequestType.DELETE_CELL})
public class GameRoomWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        return null;
    }



}
