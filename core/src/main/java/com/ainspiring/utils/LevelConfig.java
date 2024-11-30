package com.ainspiring.utils;

import com.ainspiring.entities.Obstacle;
import com.ainspiring.entities.Pet;
import com.ainspiring.entities.Veggie;

import java.util.List;

public class LevelConfig {
    private List<Prototype> veggiez;
    private List<Prototype> petz;
    private List<Prototype> obstacles;

    public List<Prototype> getVeggiez() {
        return veggiez;
    }

    public List<Prototype> getPetz() {
        return petz;
    }

    public List<Prototype> getObstacles() {
        return obstacles;
    }
}
