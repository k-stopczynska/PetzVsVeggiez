package com.ainspiring.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected int health;
    protected int cost;
    protected Sprite sprite;
    protected Vector2 position;
    protected Texture image;
    private Rectangle boundingBox;

    public Entity(Texture image, Vector2 position, int health, int cost) {
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

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.draw(batch);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

        public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.sprite.setPosition(x, y);
        boundingBox.set(x, y, sprite.getWidth(), sprite.getHeight());
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
}
