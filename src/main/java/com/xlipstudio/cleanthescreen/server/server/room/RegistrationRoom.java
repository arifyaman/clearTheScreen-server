package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.hibernate.model.User;
import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.Role;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;

import java.net.Socket;
import java.util.Date;

public class RegistrationRoom extends Room {

    private static RegistrationRoom instance = new RegistrationRoom();

    public RegistrationRoom() {
        super();
    }

    public static RegistrationRoom getInstance() {
        return instance;
    }


    @Override
    public ClientHandler addToPool(Socket socket) {
        ClientHandler handler = super.addToPool(socket);
        return handler;
    }

    @Override
    public void wrapReceived(Wrap wrap, ClientHandler from) {
        if (wrap.getWrapType() == WrapType.REQUEST) {
            if (wrap.getRequest().getRequestType().equals(RequestType.REGISTER)) {
               User registered = register(((String) wrap.getRequest().getPayload()));
               from.setUser(registered);
               from.dispatch(responderHelper.basicSuccess);
               return;
            }

        }
        from.dispatch(responderHelper.notAllowed);
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
        }else {
            user.setLastLogin(new Date());
            return hibernateUtil.saveOrUpdateUser(user);

        }


    }
}
