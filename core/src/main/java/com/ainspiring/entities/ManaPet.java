package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ManaPet extends Entity {

    protected final int mana;
    protected final float interval;
    private float elapsedTime = 0f;
    
    public ManaPet(Texture image, Vector2 position, int health, int cost, int mana, float interval) {
        super(image, position, health, cost);
        this.mana = mana;
        this.interval = interval;
    }

    @Override
    public void update(float delta) {
        generateMana(delta);
    }

    public float generateMana(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (elapsedTime >= this.interval) {
                elapsedTime = 0f;
                return this.mana;
            }
        }
        return 0f;
    }
    
        public ManaPet clone() {
        return new ManaPet(
            new Texture(this.image.getTextureData()),
            new Vector2(this.position.x, this.position.y),
            this.health,
            this.cost,
            this.mana,
            this.interval
        );
    }

}
