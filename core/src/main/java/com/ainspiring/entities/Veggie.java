package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Veggie extends FightingEntity {
    protected float elapsedTime = 0;


    public Veggie(Texture image, Vector2 position, int health, int cost, int damage, int speed) {
        super(image, position, health, cost, damage, speed);
    }

    
    public void move(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (position.x > 0) {
                position.x -= this.speed;
            }
        }
    }
   
}