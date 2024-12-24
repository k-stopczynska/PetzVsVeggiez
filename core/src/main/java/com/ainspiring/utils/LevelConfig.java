package com.ainspiring.utils;


import java.util.List;

public class LevelConfig {
    private List<Prototype> veggiez;
    private List<Prototype> petz;
    private List<Prototype> mana_petz;
    private List<Prototype> obstacles;

    public List<Prototype> getVeggiez() {
        return veggiez;
    }

    public List<Prototype> getPetz() {
        return petz;
    }

    public List<Prototype> getManaPetz() {
        return mana_petz;
    }

    public List<Prototype> getObstacles() {
        return obstacles;
    }

    public void setVeggiez(List<Prototype> veggiez) {
        this.veggiez = veggiez;
    }

    public void setPetz(List<Prototype> petz) {
        this.petz = petz;
    }

    public void setManaPetz(List<Prototype> mana_petz) {
        this.mana_petz = mana_petz;
    }
    
    public void setObstacles(List<Prototype> obstacles) {
        this.obstacles = obstacles;
    }
}
