package com.xlipstudio.cleanthescreen.server.server.room;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.handler.Pool;
import com.xlipstudio.cleanthescreen.server.server.handler.ResponderHelper;

import java.net.Socket;

public abstract class Room implements Pool.PoolCallBacks {
    private Pool pool;
    protected ResponderHelper responderHelper;

    public Room() {
        this.pool = new Pool(this);
        this.responderHelper = ResponderHelper.getInstance();
    }

    public ClientHandler addToPool(Socket socket){
       return pool.addToPool(socket);
    }

    public void dispatchToPool(Wrap wrap) {
       this.pool.dispatchToPool(wrap);
    }



}
