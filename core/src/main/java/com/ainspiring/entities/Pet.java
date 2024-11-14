package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Pet extends FightingEntity {

    public Pet(Texture image, Vector2 position, int health, int cost, int damage, int speed) {
        super(image, position, health, cost, damage, speed);
    }
}
