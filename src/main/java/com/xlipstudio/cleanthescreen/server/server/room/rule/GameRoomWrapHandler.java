package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.GameRoom;
import com.xlipstudio.cleanthescreen.server.server.room.ProfileRoom;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;
import com.xlipstudio.cleanthescreen.server.server.room.WaitingRoom;

@AllowedReqTypes(types = {RequestType.EXIT, RequestType.DELETE_CELL, RequestType.GO})
public class GameRoomWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        return null;
    }

    @HandleRequest(type = RequestType.DELETE_CELL)
    public void deleteCell(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        long requestedCellId = ((Long) wrap.getRequest().getPayload());
        ((GameRoom) getOriginRoom()).removeCell(requestedCellId, clientHandler);
    }

    @HandleRequest(type = RequestType.GO)
    public void goBack(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        Object payload = wrap.getRequest().getPayload();

        if(payload != null) {
            switch (((String) payload)) {
                case "PLAY":
                    ((GameRoom) getOriginRoom()).moveToRoom(clientHandler, RegistrationRoom.getInstance());
                    clientHandler.dispatch(new Wrap(WrapType.RESPONSE, new Response(true, "Moved to registration room", "101")));
                case "PROFILE":
                    ((GameRoom) getOriginRoom()).moveToRoom(clientHandler, ProfileRoom.getInstance());
                    clientHandler.dispatch(new Wrap(WrapType.RESPONSE, new Response(true, "Moved to profile room", "102")));


            }

        }


    }


}
