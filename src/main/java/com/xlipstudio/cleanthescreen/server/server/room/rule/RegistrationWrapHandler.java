package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.hibernate.model.User;
import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.Role;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
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
        clientHandler.dispatch(responderHelper.basicSuccess);
        return responderHelper.basicSuccess;
    }

    @HandleRequest(type = RequestType.GO)
    public Wrap handleGoReq(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        if (wrap.getRequest().getPayload() != null && wrap.getRequest().getPayload().equals("PLAY")) {
            getOriginRoom().moveToRoom(clientHandler, WaitingRoom.getInstance());
            return responderHelper.basicSuccess;
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
