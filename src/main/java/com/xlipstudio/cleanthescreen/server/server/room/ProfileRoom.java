package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.hibernate.model.Player;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.ProfileRoomWrapHandler;

import java.util.HashMap;

@WrapHandlerRule(ruleClass = ProfileRoomWrapHandler.class)
public class ProfileRoom extends Room {

    private static ProfileRoom instance = new ProfileRoom();

    public ProfileRoom() {
        super();
    }

    public static ProfileRoom getInstance() {
        return instance;
    }

    @Override
    public Wrap welcomeResponse(ClientHandler clientHandler) {
        Response response = new Response(true, "Player information", "10");
        Player player = HibernateUtil.getInstance().searchPlayerByUser(clientHandler.getUser());
        HashMap props = new HashMap<String, String>();
        props.put("Won Match", Long.toString(player.getWonMatch()));
        props.put("Lost Match", Long.toString(player.getLostMatch()));
        props.put("Cleared Cell", Long.toString(player.getRemovedCells()));
        response.setPayload(props);
        return new Wrap(WrapType.RESPONSE, response);
    }
}
