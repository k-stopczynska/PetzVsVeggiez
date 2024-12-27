package com.ainspiring.utils.prototypes;

public class ManaPetPrototype extends Prototype {
    public ManaPetPrototype() {
    }
        public int mana;
        public float interval;


    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }
}
