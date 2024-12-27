package com.ainspiring.utils;


import java.util.List;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Entity;
import com.ainspiring.entities.ManaPet;
import com.ainspiring.entities.Obstacle;
import com.ainspiring.entities.Pet;
import com.ainspiring.utils.prototypes.PetPrototype;
import com.ainspiring.utils.prototypes.ManaPetPrototype;
import com.ainspiring.utils.prototypes.Prototype;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PetHub {

 private static final Logger LOGGER = LoggerFactory.getLogger(PetHub.class);

    private Array<Entity> availablePets;
    private ConfigLoader configLoader;
    private Rectangle bounds;


    public PetHub() {
        availablePets = new Array<>();
        // TODO: move this to the screen where the player can choose which available pets 
        //he wants to play with and add them to the hub
        // TODO2: set bounds dynamically
        bounds = new Rectangle(370, 600, 800, 50);
        this.configLoader = new ConfigLoader();
        configLoader.loadConfig();
        loadPets();
    }

    public void loadPets() {
        LevelConfig levelConfig = configLoader.getLevelConfig(1);
        if (levelConfig != null && levelConfig.getPetz() != null && !levelConfig.getPetz().isEmpty()) {
            List<PetPrototype> petConfig = levelConfig.getPetz();
            List<ManaPetPrototype> manaPetConfig = levelConfig.getManaPetz();
            List<Prototype> obstaclesConfig = levelConfig.getObstacles();
            float x = 400;
            float y = 600;
            for (PetPrototype config : petConfig) {
                Pet pet = createPetFromConfig(config, x, y);
                availablePets.add(pet);
                x += 50;
            }
            // TODO: consider how to load and manage obstacles and mana pets as they share drag'n'drop logic but behave differently
            // for (Prototype config : manaPetConfig) {
            //     ManaPet manaPet = createPetFromConfig(config, x, y);
            //     availablePets.add(manaPet);
            //     x += 50;
            // }
            // for (Prototype config : obstaclesConfig) {
            //     Obstacle obstacle = createPetFromConfig(config, x, y);
            //     availablePets.add(obstacle);
            //     x += 50;
            // }
        }
    }

    private Pet createPetFromConfig(PetPrototype config, float x, float y) {
    Vector2 position = new Vector2(x, y);
    Texture image = new Texture(config.getImage());
    Pet pet = new Pet(image, position, config.getHealth(), config.getCost(),
            config.getDamage(), config.getSpeed());
    pet.setPosition(x, y);
    pet.setOriginalPosition();
    return pet;
}

    public void render(SpriteBatch batch) {
        for (Entity entity : availablePets) {
            entity.draw(batch);
        }
    }

    public Array<Entity> getAvailablePets() {
        return availablePets;
    }

    public boolean isWithinBounds(float x, float y) {
        return bounds.contains(x, y);
    }
}
