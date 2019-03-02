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
        this.notAllowed = new Wrap(new Response(false, "Not allowed", "-100"), WrapType.RESPONSE);
        this.basicError = new Wrap(new Response(false, "Failed", "-1"), WrapType.RESPONSE);
        this.basicSuccess = new Wrap(new Response(true, "Success", "1"), WrapType.RESPONSE);
    }
}
