package com.xlipstudio.cleanthescreen.server.server.game;

import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.server.logging.BaseLogger;
import com.xlipstudio.cleanthescreen.server.server.room.GameRoom;

import java.util.ArrayList;
import java.util.List;

public class GameSaloon {
    private static GameSaloon instance = new GameSaloon();
    private List<GameRoom> gameRooms;

    public GameSaloon() {
        this.gameRooms = new ArrayList<>();
    }

    public static GameSaloon getInstance() {
        return instance;
    }

    public GameRoom getAvailableRoom() {
        for (GameRoom room : gameRooms) {
            if (room.isWaitingPlayer()) {
                return room;
            }
        }

        GameRoom gameRoom = new GameRoom();
        gameRooms.add(gameRoom);
        return gameRoom;
    }

    public void dispatchToAll(Wrap wrap) {
        for (GameRoom room : gameRooms) {
            room.dispatchToPool(wrap);
        }
        BaseLogger.LOGGER.info("Dispatched to game saloon");

    }


}
