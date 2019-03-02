package com.xlipstudio.cleanthescreen.server.test;

public class TestClient {

    public static void main(String[] args) {

        GameClient.getInstance().start();
        new InputListener().start();

    }


}
