package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Pet extends FightingEntity {
    protected float elapsedTime = 0;
    private Vector2 originalPosition;
    private boolean isPlaced;

    public Pet(Texture image, Vector2 position, int health, int cost, int damage, int speed) {
        super(image, position, health, cost, damage, speed);
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
