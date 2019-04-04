package com.xlipstudio.cleanthescreen.server;


import com.xlipstudio.cleanthescreen.server.conf.ServerConfigurations;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {
        String initVector = ServerConfigurations.getIntance().security.vector;
        String key = ServerConfigurations.getIntance().security.encriptorKey;
        HibernateUtil.init();

        try {
            ServerSocket listener = new ServerSocket(ServerConfigurations.getIntance().serverPort);
            BaseLogger.LOGGER.info("SERVER STARTED");
            new InputListener().start();
            while (true) {
                Socket socket = listener.accept();

                RegistrationRoom.getInstance().addToPool(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
