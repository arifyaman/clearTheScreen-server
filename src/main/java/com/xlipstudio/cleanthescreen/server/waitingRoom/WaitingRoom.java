package com.xlipstudio.cleanthescreen.server.waitingRoom;


import java.net.Socket;

public class WaitingRoom {
    private Pool pool;

    private static WaitingRoom intance = new WaitingRoom();

    public WaitingRoom() {
        this.pool = new Pool();
    }

    public static WaitingRoom getIntance() {
        return intance;
    }

    public void addToPool(Socket socket){
        pool.addToPool(socket);
    }



}
