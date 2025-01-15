package com.ainspiring.entities;

import org.apache.logging.log4j.Logger;

import com.ainspiring.board.Cell;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

private static final Logger LOGGER = LoggerFactory.getLogger(Entity.class);

    protected String name;
    protected int health;
    protected int cost;
    protected Sprite sprite;
    protected Vector2 position;
    protected Texture image;
    private Rectangle boundingBox;
    private Vector2 originalPosition;
    private Cell occupiedCell;
    private boolean isPlaced;


    public Entity(String name, Texture image, Vector2 position, int health, int cost) {
        this.name = name;
        this.image = image;
        this.health = health;
        this.cost = cost;
        this.position = position;
        sprite = new Sprite(image);
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        
        // TODO: this is not working properly until we change the method of drawing veggiez onto board in render method in PetzVeggiezGame
        // sprite.setScale(4f);
    }
    
    public abstract void update(float delta);

    public abstract Entity clone();

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.draw(batch);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            if (occupiedCell != null) {
                occupiedCell.setIsOccupied(false);
            }
            this.setPosition(position.x, 2000); 
        }
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.sprite.setPosition(x, y);
        boundingBox.set(x, y, sprite.getWidth(), sprite.getHeight());
    }
    
    public Vector2 getPosition() {
        return position;
    }

    public void setOccupiedCell(Cell cell) {
        this.occupiedCell = cell;
    }

    public Cell getOccupiedCell() {
        return this.occupiedCell;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public Texture getTexture() {
        return this.image;
    }

    public void resetPosition() {
        this.setPosition(originalPosition.x, originalPosition.y);
    }

    public void setOriginalPosition() {
        originalPosition = new Vector2(position.x, position.y);
    }

    public Vector2 getOriginalPosition() {
        return this.originalPosition;
    }

    public boolean isPlaced() {
        return this.isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public int getCost() {
        return this.cost;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public void dispose() {
        image.dispose();
    }
}
