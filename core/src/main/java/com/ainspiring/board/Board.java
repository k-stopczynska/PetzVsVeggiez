package com.ainspiring.board;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Pet;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.PetHub;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


public class Board {

    private final int ROWS = 5;
    private final int COLS = 8;
    private final int CELL_WIDTH = 64;
    private final int CELL_HEIGHT = 64;
    private ShapeRenderer shapeRenderer;
    private Array<Pet> petsOnBoard;

    private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    public Board() {
        initializeGrid();
        shapeRenderer = new ShapeRenderer();
        petsOnBoard = new Array<>();
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

    public void placePet(Pet pet, Vector2 position) {
    if (!isPositionValid(position)) return;
    
    pet.setPlaced(true);
    petsOnBoard.add(pet);

    //TODO: center a pet in a cell and mark cell occupied to check before setting another one
    float newX = position.x;
    float newY = position.y;

    pet.setPosition(newX, newY);
    }
    
    public boolean isPositionValid(Vector2 position) {
        return position.x >= getOffsetX() && position.x <= getOffsetX() + getBoardWidth()
                && position.y >= getOffsetY() && position.y <= getOffsetY() + getBoardHeight();
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
