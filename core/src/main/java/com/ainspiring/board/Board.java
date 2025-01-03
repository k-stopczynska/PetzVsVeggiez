package com.ainspiring.board;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Entity;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Board {

    private final int ROWS = 5;
    private final int COLS = 8;
    private final int CELL_WIDTH = 64;
    private final int CELL_HEIGHT = 64;
    private Cell[][] boardGrid;
    private ShapeRenderer shapeRenderer;
    private List<Entity> petsOnBoard;

    private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    public Board() {
        boardGrid = new Cell[ROWS][COLS];
        initializeGrid();
        shapeRenderer = new ShapeRenderer();
        petsOnBoard = new ArrayList<Entity>();
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                shapeRenderer.rect( getOffsetX() + col * CELL_WIDTH, 
                getOffsetY() + row * CELL_HEIGHT, 
                CELL_WIDTH, 
                CELL_HEIGHT);
            }
        }
        shapeRenderer.end();
    }

    public void initializeGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardGrid[row][col] = new Cell();
            }
        }
    }

    public void placePet(Entity pet, Vector2 position) {
        Vector2 newPosition = getCellForPet(pet);
        int col = (int) newPosition.x;
        int row = (int) newPosition.y;
        if (!isPositionValid(col, row)) {
            pet.resetPosition();
            return;
        }
       
        Cell cell = boardGrid[row][col];
        cell.isOccupied = true;
        pet.setPlaced(true);
        petsOnBoard.add(pet);
        float petX = centerPet(col, row, pet).x;
        float petY = centerPet(col, row, pet).y;
        pet.setPosition(petX, petY);
    }

    public boolean isPositionValid(int col, int row) {
        Cell cell = null;
        boolean isWithinBounds = col >= 0 && col < COLS && row >= 0 && row < ROWS;
        if (isWithinBounds) {
            cell = boardGrid[row][col];
        }
        return isWithinBounds && !cell.isOccupied;
    }

    protected Vector2 centerPet(int col, int row, Entity pet) {
        float centerX = getOffsetX() + (col * CELL_WIDTH) + (CELL_WIDTH / 2);
        float centerY = getOffsetY() + (row * CELL_HEIGHT) + (CELL_HEIGHT / 2);

        float petX = centerX - (pet.getWidth() / 2);
        float petY = centerY - (pet.getHeight() / 2);

        return new Vector2(petX, petY);
    }

    public Vector2 getCellForPet(Entity pet) {
        float petX = pet.getPosition().x + (pet.getWidth() / 2);
        float petY = pet.getPosition().y + (pet.getHeight() / 2);

        int col = (int) ((petX - getOffsetX()) / CELL_WIDTH);
        int row = (int) ((petY - getOffsetY()) / CELL_HEIGHT);

        return new Vector2(col, row);
    }

    public List<Entity> getPetsOnBoard() {
    return petsOnBoard;
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
