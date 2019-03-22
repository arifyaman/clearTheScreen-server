package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;

@AllowedReqTypes(types = {RequestType.EXIT})
public class ProfileRoomWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        return null;
    }

    @HandleRequest(type = RequestType.EXIT)
    public Wrap handeExitRequest(Wrap wrap, ClientHandler clientHandler, Pool pool) {

        getOriginRoom().moveToRoom(clientHandler, RegistrationRoom.getInstance());
        clientHandler.dispatch(responderHelper.basicSuccess);
        return responderHelper.basicSuccess;
    }


}
