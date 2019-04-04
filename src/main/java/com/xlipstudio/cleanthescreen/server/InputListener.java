package com.xlipstudio.cleanthescreen.server;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.server.game.GameSaloon;
import com.xlipstudio.cleanthescreen.server.server.room.AdminRoom;

import java.util.Scanner;

public class InputListener extends Thread {
    Scanner scanner;

    public InputListener() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            String req = scanner.nextLine();

           /* if(req.equals("1")) {
                list.add("asd");
            }
            if(req.equals("2")) {
                list.remove("asd");
            }if(req.equals("3")) {
                System.out.println(list.size());
            }*/


            if (req.equals("1")) {
                Wrap wrap = new Wrap(WrapType.RESPONSE, new Response(true, "TEST", "0"));
                AdminRoom.getInstance().dispatchToPool(wrap);


            }
        }

    }
}
