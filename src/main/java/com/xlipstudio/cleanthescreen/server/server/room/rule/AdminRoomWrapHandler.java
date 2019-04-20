package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;

@AllowedReqTypes(types = {RequestType.GO,RequestType.EXIT,RequestType.HEARTH_BEAT})
public class AdminRoomWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {

        return null;
    }

    @HandleRequest(type = RequestType.GO)
    public Wrap handleGoReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        clientHandler.dispatch(responderHelper.basicSuccess);
        return responderHelper.basicSuccess;

    }

    @HandleRequest(type = RequestType.HEARTH_BEAT)
    public Wrap handleHearthBeatReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        clientHandler.dispatch(responderHelper.hearthBeat);
        return responderHelper.hearthBeat;

    }



}
