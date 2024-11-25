package com.ainspiring.board;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cell {
    protected Sprite sprite;
    protected boolean isOccupied;

    public Cell() {
        this.sprite = null;
        this.isOccupied = false;
    }
}
