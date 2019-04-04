package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.room.rule.AdminRoomWrapHandler;

@WrapHandlerRule(ruleClass = AdminRoomWrapHandler.class)
public class AdminRoom extends Room {

    private static AdminRoom instance = new AdminRoom();

    public AdminRoom() {
        super();
    }

    public static AdminRoom getInstance() {
        return instance;
    }

}
