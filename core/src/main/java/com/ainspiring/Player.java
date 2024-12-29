package com.ainspiring;


import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Entity;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.PetHub;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    protected String name;
    protected int level = 0;
    protected int gatheredMana = 30;
    protected List<Entity> pets;

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

    public void spendMana(int mana) {
        this.gatheredMana -= mana;
    }

    public CharSequence getGatheredMana() {
        return (CharSequence)String.valueOf(this.gatheredMana);
    }
}
