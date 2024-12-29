package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Veggie extends FightingEntity {
    protected float elapsedTime = 0;

    public Veggie(String name, Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(name, image, position, health, cost, damage, speed);
    }

    @Override
    public void update(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (position.x > 0) {
                position.x -= this.speed;
            }
        }
    }

    @Override
    public Veggie clone() {
        return new Veggie(name, image, position, health, cost, damage, speed);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }
   
}
