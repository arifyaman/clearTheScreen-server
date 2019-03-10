package com.xlipstudio.cleanthescreen.server.server.room;


import com.xlipstudio.cleanthescreen.communication.Wrap;
import com.xlipstudio.cleanthescreen.communication.response.Response;
import com.xlipstudio.cleanthescreen.communication.sub.WrapType;
import com.xlipstudio.cleanthescreen.server.annotations.WrapHandlerRule;
import com.xlipstudio.cleanthescreen.server.conf.ServerConfigurations;
import com.xlipstudio.cleanthescreen.server.hibernate.HibernateUtil;
import com.xlipstudio.cleanthescreen.server.hibernate.model.GameMatch;
import com.xlipstudio.cleanthescreen.server.hibernate.model.Player;
import com.xlipstudio.cleanthescreen.server.server.game.Cell;
import com.xlipstudio.cleanthescreen.server.server.handler.ClientHandler;
import com.xlipstudio.cleanthescreen.server.server.room.rule.GameRoomWrapHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WrapHandlerRule(ruleClass = GameRoomWrapHandler.class)
public class GameRoom extends Room {

    private boolean waitingPlayer;
    private boolean gameFinished;
    private Map<Long, Cell> cells;
    private int removedCellSize = 0;
    private Player player1;
    private Player player2;
    private Wrap winnerWrap = new Wrap(WrapType.RESPONSE, new Response(true, "You won", "100"));
    private Wrap loserWrap = new Wrap(WrapType.RESPONSE, new Response(true, "You lose", "-100"));
    private GameMatch match;


    public GameRoom() {
        super();
        this.waitingPlayer = true;
        this.cells = new HashMap<>();
    }

    @Override
    public void newJoin(ClientHandler clientHandler) {
        if (pool.getClientHandlers().size() == 2) {
            player2 = getPlayer(clientHandler);
            initGame();
        } else {
            player1 = getPlayer(clientHandler);
        }
    }

    private Player getPlayer(ClientHandler clientHandler) {
        Player player = HibernateUtil.getInstance().searchPlayerByUser(clientHandler.getUser());
        if (player == null) {
            player = new Player();
            player.setUser(clientHandler.getUser());
            return HibernateUtil.getInstance().saveOrUpdate(player, Player.class);
        } else {
            return player;
        }

    }

    public void initGame() {


        for (long i = 0; i < ServerConfigurations.getIntance().gameConf.getCellSize(); i++) {
            cells.put(i, new Cell(i));
        }
        for (int i = 0; i < pool.getClientHandlers().size(); i++) {

        }
        waitingPlayer = false;

        this.match = new GameMatch();
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        HibernateUtil util = HibernateUtil.getInstance();
        this.match = util.saveOrUpdate(match, GameMatch.class);
    }

    public void removeCell(long id, ClientHandler handler) {
        Cell target = cells.get(id);
        if (target != null) {
            if (!target.isDestroyed()) {
                target.setDestroyed(true);
                target.setDestroyer(handler.getUser());

                Response response = new Response(true, "Deleted Id: " + id, "1");
                response.setPayload(id);
                Wrap wrap = new Wrap(WrapType.RESPONSE, response);
                dispatchToPool(wrap);
                removedCellSize++;
                if (removedCellSize == ServerConfigurations.getIntance().gameConf.getCellSize()) {
                    gameFinished();
                }
                return;
            } else {
                Wrap wrap = new Wrap(WrapType.RESPONSE, new Response(false, "Already removed" + id, "0"));
                dispatchToPool(wrap);
                return;
            }
        }

        Wrap wrap = new Wrap(WrapType.RESPONSE, new Response(false, "No cell found: " + id, "-1"));
        dispatchToPool(wrap);

    }

    private void gameFinished() {
        gameFinished = true;
        int player1Score = 0;
        int player2Score = 0;

        for (Cell cell : cells.values()) {
            if(cell.getDestroyer() != null) {
                if (cell.getDestroyer() == player1.getUser()) {
                    player1Score++;
                } else {
                    player2Score++;
                }
            }

        }

        player1.setRemovedCells(player1.getRemovedCells() + player1Score);
        player2.setRemovedCells(player2.getRemovedCells() + player2Score);
        HibernateUtil util = HibernateUtil.getInstance();
        util.saveOrUpdate(player1, Player.class);
        util.saveOrUpdate(player2, Player.class);

        if(pool.getClientHandlers().size() == 1) {
            winnerWrap.getResponse().setPayload(player1Score);
            pool.getClientHandlers().get(0).dispatch(winnerWrap);
        }else {
            if (player1Score > player2Score) {
                winnerWrap.getResponse().setPayload(player1Score);
                pool.getClientHandlers().get(0).dispatch(winnerWrap);
                loserWrap.getResponse().setPayload(player2Score);
                pool.getClientHandlers().get(1).dispatch(loserWrap);

            } else {
                winnerWrap.getResponse().setPayload(player1Score);
                pool.getClientHandlers().get(1).dispatch(winnerWrap);
                loserWrap.getResponse().setPayload(player2Score);
                pool.getClientHandlers().get(0).dispatch(loserWrap);
            }
        }



        match.setPlayer1Score(player1Score);
        match.setPlayer2Score(player2Score);
        match.setEndTime(new Date());
        match = util.saveOrUpdate(match, GameMatch.class);

    }

    @Override
    public Wrap welcomeResponse() {
        Wrap wrap = new Wrap();
        wrap.setWrapType(WrapType.RESPONSE);
        Response response = new Response(true, "Welcome to game room", "1");
        response.setPayload(gson.toJson(ServerConfigurations.getIntance().gameConf));
        wrap.setResponse(response);
        return wrap;
    }

    @Override
    public void disconnected(ClientHandler clientHandler) {
        super.disconnected(clientHandler);
        if(pool.getClientHandlers().size() == 1) {
            gameFinished();
        }else if(pool.getClientHandlers().size() == 0) {
            resetRoom();
        }


    }

    public boolean isWaitingPlayer() {
        return waitingPlayer;
    }

    private void resetRoom() {
        this.waitingPlayer = true;
        this.cells.clear();
        this.pool.getClientHandlers().clear();
        this.match = null;
        this.player1 = null;
        this.player2= null;
        this.removedCellSize = 0;
        this.gameFinished = false;
    }
}
