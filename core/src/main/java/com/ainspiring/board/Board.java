package com.ainspiring.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Board {

    private final int ROWS = 5;
    private final int COLS = 8;
    private final int CELL_WIDTH = 128;
    private final int CELL_HEIGHT = 128;
    private ShapeRenderer shapeRenderer;

    public Board() {
        initializeGrid();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int row = 0; row <= ROWS; row++) {
            for (int col = 0; col <= COLS; col++) {
                shapeRenderer.rect( getOffsetX() + col * CELL_WIDTH, 
                getOffsetY() + row * CELL_HEIGHT, 
                CELL_WIDTH, 
                CELL_HEIGHT);
            }
        }
        shapeRenderer.end();
    }

    public void initializeGrid() {

        Cell[][] boardGrid = new Cell[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardGrid[row][col] = new Cell();
            }
        }
    }

    public int getRowCount() {
        return ROWS;
    }

    public int getColumnCount() {
        return COLS;
    }

    public int getCellWidth() {
        return CELL_WIDTH;
    }

    public int getCellHeight() {
        return CELL_HEIGHT;
    }

    public int getBoardWidth() {
        return COLS * CELL_WIDTH;
    }

    public int getBoardHeight() {
        return ROWS * CELL_HEIGHT;
    }

    public int getScreenWidth() {
        return Gdx.graphics.getWidth();
    }

    public int getScreenHeight() {
        return Gdx.graphics.getHeight();
    }

    public int getOffsetX() {
        return (getScreenWidth() - getBoardWidth()) / 2;
    }

    public int getOffsetY() {
        return (getScreenHeight() - getBoardHeight()) / 2;
    }

    public void dispose() {
        shapeRenderer.dispose();
    } 

}
