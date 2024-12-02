package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pet extends FightingEntity {
    private Rectangle bounds;
    protected float elapsedTime = 0;

    public Pet(Texture image, Vector2 position, int health, int cost, int damage, int speed) {
        super(image, position, health, cost, damage, speed);
        bounds = new Rectangle();
    }

    @Override
    public void update(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
        }
    }

    public Texture getTexture() {
        return this.image;
    }

    public void setPosition(float x, float y) {
        bounds.set(x, y, sprite.getWidth(), sprite.getHeight());
        this.position.set(x, y);
        this.sprite.setPosition(x, y);
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }
}
