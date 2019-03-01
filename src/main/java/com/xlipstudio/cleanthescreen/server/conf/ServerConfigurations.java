package com.xlipstudio.cleanthescreen.server.conf;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;


public class ServerConfigurations {
    public int serverPort;
    public String serverLogFile;
    public Security security;
    public WaitingRoom waitingRoom;



    private static ServerConfigurations intance = new ServerConfigurations();

    public static ServerConfigurations getIntance() {
        return intance;
    }


    static {

        Yaml yaml = new Yaml();
        try {
            File initialFile = null;

            try {
                initialFile = new File(ServerConfigurations.class.getResource("config.yml").toURI());
            } catch (Exception e) {
                initialFile = new File("config.yml");
            }

            InputStream targetStream = new FileInputStream(initialFile);
            intance = yaml.loadAs(targetStream, ServerConfigurations.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static class Security{
        public String encriptorKey;
        public String vector;
    }

    public static class WaitingRoom {
        public int maxPoolSize;
    }

}
