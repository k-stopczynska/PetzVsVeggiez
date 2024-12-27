package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Obstacle extends Entity {
    public Obstacle(Texture image, Vector2 position, int health, int cost) {
        super(image, position, health, cost);
 }

    @Override
    public void update(float delta) {
    }

        public Obstacle clone() {
        return new Obstacle(
            new Texture(this.image.getTextureData()),
            new Vector2(this.position.x, this.position.y),
            this.health,
            this.cost
        );
    }
}
