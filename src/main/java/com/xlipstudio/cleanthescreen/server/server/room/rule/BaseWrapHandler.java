package com.xlipstudio.cleanthescreen.server.server.room.rule;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.AllowedReqTypes;
import com.xlipstudio.cleanthescreen.server.annotations.HandleRequest;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.helper.ResponderHelper;
import com.xlipstudio.cleanthescreen.server.server.room.Room;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseWrapHandler {
    ResponderHelper responderHelper = new ResponderHelper();
    private Room originRoom;


    public Wrap handleWrap(Wrap wrap, ClientHandler clientHandler, Pool pool) {
        if (wrap.getWrapType().equals(WrapType.REQUEST)) {
            AllowedReqTypes rule = this.getClass().getAnnotation(AllowedReqTypes.class);
            for (RequestType type : rule.types()) {
                if (type.equals(wrap.getRequest().getRequestType())) {
                    final List<Method> allMethods = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredMethods()));
                    for (final Method method : allMethods) {
                        if (method.isAnnotationPresent(HandleRequest.class)) {
                            HandleRequest requestAnnotation = method.getAnnotation(HandleRequest.class);
                            if (requestAnnotation.type().equals(wrap.getRequest().getRequestType())) {
                                try {
                                    return ((Wrap) method.invoke(this, wrap, clientHandler, pool));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    return processReceivedWrap(wrap, clientHandler, pool);
                }
            }
        }
        clientHandler.dispatch(responderHelper.notAllowed);
        return null;
    }


    /**
     * @param wrap request wrap
     * @return response Wrap
     */
    public abstract Wrap processReceivedWrap(Wrap wrap, ClientHandler clientHandler, Pool pool);


    public Room getOriginRoom() {
        return originRoom;
    }

    public void setOriginRoom(Room originRoom) {
        this.originRoom = originRoom;
    }
}
