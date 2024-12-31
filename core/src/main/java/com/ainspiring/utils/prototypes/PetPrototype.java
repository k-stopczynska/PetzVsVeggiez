package com.ainspiring.utils.prototypes;

public class PetPrototype extends Prototype {
    public PetPrototype() {
    }

        public float speed;
        public int damage;


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
