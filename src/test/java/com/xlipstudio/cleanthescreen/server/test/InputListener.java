package com.xlipstudio.cleanthescreen.server.test;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.request.Request;
import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputListener extends Thread {
    Scanner scanner;

    public InputListener() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        List<String> list = new ArrayList<>();

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


            if(req.equals("1")) {
                Wrap wrap = new Wrap();
                wrap.setWrapType(WrapType.REQUEST);
                Request request = new Request();
                request.setPayload("PLAY");
                request.setRequestType(RequestType.GO);
                wrap.setRequest(request);
                GameClient.getInstance().dispatchWrap(wrap);
            }
            if(req.equals("2")) {
                Wrap wrap = new Wrap();
                wrap.setWrapType(WrapType.REQUEST);
                Request request = new Request();
                request.setPayload("PLAY");
                request.setRequestType(RequestType.EXIT);
                wrap.setRequest(request);
                GameClient.getInstance().dispatchWrap(wrap);
            }
        }

    }
}
