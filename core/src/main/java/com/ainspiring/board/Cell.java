package com.ainspiring.board;

import org.apache.logging.log4j.Logger;

import com.ainspiring.utils.LoggerFactory;


public class Cell {

    private static final Logger LOGGER = LoggerFactory.getLogger(Cell.class);

    protected boolean isOccupied;

    public Cell() {
        this.isOccupied = false;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
