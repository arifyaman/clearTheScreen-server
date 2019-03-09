package com.xlipstudio.cleanthescreen.server;


import com.xlipstudio.cleanthescreen.server.conf.ServerConfigurations;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;
import com.xlipstudio.cleanthescreen.server.server.room.RegistrationRoom;
import com.xlipstudio.cleanthescreen.server.server.room.WaitingRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {
        String initVector = ServerConfigurations.getIntance().security.vector;
        String key = ServerConfigurations.getIntance().security.encriptorKey;
        HibernateUtil.init();
       /*
        User user = new User();
        user.setUserKey("asdas");

        hibernateUtil.saveOrUpdateUser(user);

        User existing = hibernateUtil.getModel(1, User.class);
        System.out.println(existing.getUserKey());*/


        /*try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal("".getBytes());
            System.out.println("encrypted string: "
                    + Base64.getEncoder().encodeToString(encrypted));

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

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
