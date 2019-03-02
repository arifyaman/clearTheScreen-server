package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;
import com.xlipstudio.cleanthescreen.server.server.room.WaitingRoom;

@AllowedReqTypes(types = {RequestType.EXIT})
public class WaitingRoomWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        return null;
    }

    @HandleRequest(type = RequestType.EXIT)
    public Wrap handleRegisterReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {

        RegistrationRoom.getInstance().moveToRoom(clientHandler, RegistrationRoom.getInstance());
        return responderHelper.basicSuccess;

    }


}
