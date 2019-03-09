package com.xlipstudio.cleanthescreen.server.hibernate.model;

import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Player extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5733840493659566553L;

    private long removedCells;


    @OneToOne
    private User user;

    public long getRemovedCells() {
        return removedCells;
    }

    public void setRemovedCells(long removedCells) {
        this.removedCells = removedCells;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
