package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.server.room.rule.RegistrationWrapHandler;

@WrapHandlerRule(ruleClass = RegistrationWrapHandler.class)
public class RegistrationRoom extends Room {

    private static RegistrationRoom instance = new RegistrationRoom();

    public RegistrationRoom() {
        super();
    }

    public static RegistrationRoom getInstance() {
        return instance;
    }


}
