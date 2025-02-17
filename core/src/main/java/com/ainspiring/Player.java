package com.ainspiring;


import org.apache.logging.log4j.Logger;

import java.util.List;
import com.ainspiring.entities.Entity;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.PetHub;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    protected String name;
    protected int level = 1;
    protected int gatheredMana = 30;
    protected List<Entity> pets;

    public Player(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public void saveProgress() {
        Preferences prefs = Gdx.app.getPreferences("PlayerProgress");
        prefs.putString("playerName", name);
        prefs.putInteger("playerLevel", level);
        prefs.flush();
        LOGGER.info("Saving player's progress: " + prefs.getString("playerName") + ", level: " + prefs.getInteger("playerLevel"));
    }

    public void loadProgress() {
        Preferences prefs = Gdx.app.getPreferences("PlayerProgress");
        this.name = prefs.getString("playerName", "PlayerOne");
        this.level = prefs.getInteger("playerLevel", 1);
    }


    public void choosePetz() {
        // TODO: implement click event and touch event listener to add chosen pet to a list in the right phase
    }

    public void levelUp() {
        this.level++;
        // TODO: uncomment this when level selection of pets will be
        //this.pets.clear();
        this.gatheredMana = 30;
        this.saveProgress();
    }

    public void resetLevel() {
        this.loadProgress();
        this.gatheredMana = 30;
    }

    public void gatherMana(int mana) {
        this.gatheredMana += mana;
    }

    public void spendMana(int mana) {
        this.gatheredMana -= mana;
    }

    public CharSequence getGatheredMana() {
        return (CharSequence) String.valueOf(this.gatheredMana);
    }

        public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
