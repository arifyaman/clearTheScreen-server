package com.xlipstudio.cleanthescreen.server.hibernate.model;

import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
public class GameMatch extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 2928565157518657208L;

    @ManyToOne
    private Player player1;

    private long player1Score;

    private long player2Score;

    @ManyToOne
    private Player player2;

    private Date endTime;


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(long player1Score) {
        this.player1Score = player1Score;
    }

    public long getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(long player2Score) {
        this.player2Score = player2Score;
    }
}
