package com.xlipstudio.cleanthescreen.server.server.game;

import com.xlipstudio.cleanthescreen.server.hibernate.model.User;

public class Cell {
    private long id;
    private boolean destroyed;
    private User destroyer;

    public Cell(long id) {
        this.id = id;
    }

    public Cell() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public User getDestroyer() {
        return destroyer;
    }

    public void setDestroyer(User destroyer) {
        this.destroyer = destroyer;
    }
}
