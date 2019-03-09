package com.xlipstudio.cleanthescreen.server.server.helper;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;

public class ResponderHelper {
    private static ResponderHelper instance = new ResponderHelper();
    public Wrap basicSuccess;
    public Wrap basicError;
    public Wrap notAllowed;



    public static ResponderHelper getInstance() {
        return instance;
    }

    public ResponderHelper() {
        this.notAllowed = new Wrap(WrapType.RESPONSE, new Response(false, "Not allowed", "-100"));
        this.basicError = new Wrap(WrapType.RESPONSE,new Response(false, "Failed", "-1"));
        this.basicSuccess = new Wrap(WrapType.RESPONSE,new Response(true, "Success", "1"));
    }
}
