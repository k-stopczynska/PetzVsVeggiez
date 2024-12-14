package com.ainspiring.utils;


import java.util.List;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Pet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PetHub {

 private static final Logger LOGGER = LoggerFactory.getLogger(PetHub.class);

    private Array<Pet> availablePets;
    private ConfigLoader configLoader;
    private Rectangle bounds;


    public PetHub() {
        availablePets = new Array<>();
        // TODO: move this to the screen where the player can choose which available pets 
        //he wants to play with and add them to the hub
        bounds = new Rectangle(370, 600, 150, 50);
        this.configLoader = new ConfigLoader();
        configLoader.loadConfig();
        loadPets();
    }

    public void loadPets() {
        LevelConfig levelConfig = configLoader.getLevelConfig(1);
        if (levelConfig != null && levelConfig.getPetz() != null && !levelConfig.getPetz().isEmpty()) {
            List<Prototype> petConfig = levelConfig.getPetz();
            float x = 400;
            float y = 600;
            for (Prototype config : petConfig) {
                Vector2 position = new Vector2(x, y);
                Texture image = new Texture(config.getImage());
                Pet pet = new Pet(image, position, config.getHealth(), config.getCost(),
                        config.getDamage(), config.getSpeed());
                pet.setPosition(x, y);
                pet.setOriginalPosition();
                availablePets.add(pet);
                x += 50;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Pet pet : availablePets) {
            pet.draw(batch);
        }
    }

    public Array<Pet> getAvailablePets() {
        return availablePets;
    }

    public boolean isWithinBounds(float x, float y) {
        return bounds.contains(x, y);
    }
}
