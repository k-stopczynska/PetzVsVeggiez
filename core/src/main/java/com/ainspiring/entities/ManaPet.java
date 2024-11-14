package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ManaPet extends Entity {

    protected final int mana;
    private float elapsedTime = 0f;
    
    public ManaPet(Texture image, Vector2 position, int health, int cost, int mana) {
        super(image, position, health, cost);
        this.mana = mana;}

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public int generateMana(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (elapsedTime >= 30.0f) {
                elapsedTime = 0f;
                return this.mana;
            }
        }
    return 0;
    }

}
