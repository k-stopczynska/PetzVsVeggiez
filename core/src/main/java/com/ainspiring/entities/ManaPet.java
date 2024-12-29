package com.ainspiring.entities;

import org.apache.logging.log4j.Logger;

import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ManaPet extends Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManaPet.class);

    protected final int mana;
    protected final float interval;
    private float elapsedTime = 0f;
    private boolean hasGeneratedMana = false;
    private Rectangle starBoundingBox;
    
    public ManaPet(String name, Texture image, Vector2 position, int health, int cost, int mana, float interval) {
        super(name, image, position, health, cost);
        this.mana = mana;
        this.interval = interval;
        starBoundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void update(float delta) {
        if (isPlaced()) generateMana(delta);
    }

    public void generateMana(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (elapsedTime >= this.interval) {
                elapsedTime = 0f;
                LOGGER.info("Generated mana: " + this.mana);
                hasGeneratedMana = true;
            }
        }
    }
    
    public void setStarBoundingBox(Vector2 position, Sprite sprite) {
        starBoundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    public Rectangle getStarBoundingBox() {
        return starBoundingBox;
    }

    public boolean isWithinBounds(float x, float y) {
        return starBoundingBox.contains(x, y);
    }

    public boolean getHasGeneratedMana() {
        return hasGeneratedMana;
    }

    public void setHasGeneratedMana() {
        hasGeneratedMana = false;
    }

    public int getMana() {
        return mana;
    }
    
    public ManaPet clone() {
        return new ManaPet(
            this.name,
            new Texture(this.image.getTextureData()),
            new Vector2(this.position.x, this.position.y),
            this.health,
            this.cost,
            this.mana,
            this.interval
        );
    }

}
