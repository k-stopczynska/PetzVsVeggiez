package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Pet extends FightingEntity {
    protected float elapsedTime = 0;

    public Pet(Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(image, position, health, cost, damage, speed);
    }

    @Override
    public void update(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
        }
    }


    public Pet clone() {
        return new Pet(
            new Texture(this.image.getTextureData()),
            new Vector2(this.position.x, this.position.y),
            this.health,
            this.cost,
            this.damage,
            this.speed
        );
    }
}
