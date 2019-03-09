package com.xlipstudio.cleanthescreen.server.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class GameConf implements Serializable {


    private static final long serialVersionUID = -2014447644561983067L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long cellSize;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCellSize() {
        return cellSize;
    }

    public void setCellSize(long cellSize) {
        this.cellSize = cellSize;
    }
}
