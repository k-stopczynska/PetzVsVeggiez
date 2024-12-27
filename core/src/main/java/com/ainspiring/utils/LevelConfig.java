package com.ainspiring.utils;


import java.util.List;

import com.ainspiring.utils.prototypes.ManaPetPrototype;
import com.ainspiring.utils.prototypes.PetPrototype;
import com.ainspiring.utils.prototypes.Prototype;
import com.ainspiring.utils.prototypes.VeggiezPrototype;

public class LevelConfig {
    private List<VeggiezPrototype> veggiez;
    private List<PetPrototype> petz;
    private List<ManaPetPrototype> mana_petz;
    private List<Prototype> obstacles;

    public List<VeggiezPrototype> getVeggiez() {
        return veggiez;
    }

    public List<PetPrototype> getPetz() {
        return petz;
    }

    public List<ManaPetPrototype> getManaPetz() {
        return mana_petz;
    }

    public List<Prototype> getObstacles() {
        return obstacles;
    }

    public void setVeggiez(List<VeggiezPrototype> veggiez) {
        this.veggiez = veggiez;
    }

    public void setPetz(List<PetPrototype> petz) {
        this.petz = petz;
    }

    public void setManaPetz(List<ManaPetPrototype> mana_petz) {
        this.mana_petz = mana_petz;
    }
    
    public void setObstacles(List<Prototype> obstacles) {
        this.obstacles = obstacles;
    }
}
