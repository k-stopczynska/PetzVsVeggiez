package com.ainspiring;

import java.util.ArrayList;

import com.ainspiring.entities.Pet;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class Player {
    protected String name;
    protected int level = 0;
    protected int gatheredMana = 30;
    protected List<Pet> pets;

    public void choosePetz() {
    // TODO: implement click event and touch event listener to add chosen pet to a list in the right phase
    }

    public void levelUp() {
        this.level++;
        this.pets.clear();
        this.gatheredMana = 30;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void gatherMana(int mana) {
        this.gatheredMana += mana;
    }
}
