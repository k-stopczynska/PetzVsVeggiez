package com.ainspiring.utils.prototypes;

public class PetPrototype extends Prototype {
    public PetPrototype() {
    }
    
        public int speed;
        public int damage;


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
