package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.hibernate.model.User;
import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.Role;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.room.AdminRoom;
import com.xlipstudio.cleanthescreen.server.server.room.ProfileRoom;
import com.xlipstudio.cleanthescreen.server.server.room.WaitingRoom;

import java.util.Date;

@AllowedReqTypes(types = {RequestType.REGISTER, RequestType.GO})
public class RegistrationWrapHandler extends BaseWrapHandler {

    @Override
    public Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        return null;
    }

    @HandleRequest(type = RequestType.REGISTER)
    public Wrap handleRegisterReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        User registered = register(((String) wrap.getRequest().getPayload()));
        clientHandler.setUser(registered);

        if (registered.getRole() == Role.ADMIN) {
            BaseLogger.LOGGER.info("ADMIN LOGGED IN");
            getOriginRoom().moveToRoom(clientHandler, AdminRoom.getInstance());
            return null;
        }

        AdminRoom.getInstance().dispatchToPool(new Wrap(WrapType.RESPONSE, new Response(true, "SOMEONE CONNECTED TO THE GAME", "1")));

        clientHandler.dispatch(responderHelper.basicSuccess);
        return responderHelper.basicSuccess;
    }

    @HandleRequest(type = RequestType.GO)
    public Wrap handleGoReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        Object payload = wrap.getRequest().getPayload();

        if (payload != null) {
            switch (((String) payload)) {
                case "PLAY":

                    clientHandler.dispatch(new Wrap(WrapType.RESPONSE, new Response(true, "Moved to waiting room", "101")));
                    getOriginRoom().moveToRoom(clientHandler, WaitingRoom.getInstance());
                    return responderHelper.basicSuccess;
                case "PROFILE":
                    clientHandler.dispatch(new Wrap(WrapType.RESPONSE, new Response(true, "Moved to profile room", "102")));
                    getOriginRoom().moveToRoom(clientHandler, ProfileRoom.getInstance());
                    return responderHelper.basicSuccess;
            }

        }

        return responderHelper.notAllowed;
    }

    private User register(String userKey) {
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
        User user = hibernateUtil.searchByUserKey(userKey);
        if (user == null) {
            User newOne = new User();
            newOne.setUserKey(userKey);
            newOne.setRole(Role.USER);
            newOne.setLastLogin(new Date());
            newOne.setName("Guest");
            return hibernateUtil.saveOrUpdateUser(newOne);
        } else {
            user.setLastLogin(new Date());
            return hibernateUtil.saveOrUpdateUser(user);

        }
    }


}
