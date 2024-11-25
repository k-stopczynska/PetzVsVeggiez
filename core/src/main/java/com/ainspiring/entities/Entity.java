package com.ainspiring.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected int health;
    protected int cost;
    protected Sprite sprite;
    protected Vector2 position;
    protected Texture image;

    public Entity(Texture image, Vector2 position, int health, int cost) {
        this.image = image;
        this.health = health;
        this.cost = cost;
        this.position = position;
        sprite = new Sprite(image);
    }
    
    public abstract void update(float delta);

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.draw(batch);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }
}
