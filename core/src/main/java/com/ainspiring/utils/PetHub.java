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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

            for (ManaPetPrototype config : manaPetConfig) {
                ManaPet manaPet = createManaPetFromConfig(config, x, y);
                availablePets.add(manaPet);
                x += 100;
            }
            for (PetPrototype config : petConfig) {
                Pet pet = createPetFromConfig(config, x, y);
                availablePets.add(pet);
                x += 100;
            }
            for (Prototype config : obstaclesConfig) {
                Obstacle obstacle = createObstacleFromConfig(config, x, y);
                availablePets.add(obstacle);
                x += 100;
            }
        }
    }

    private Pet createPetFromConfig(PetPrototype config, float x, float y) {
        Vector2 position = new Vector2(x, y);
        Texture image = new Texture(config.getImage());
        Pet pet = new Pet(config.getName(), image, position, config.getHealth(), config.getCost(),
                config.getDamage(), config.getSpeed());
        pet.setPosition(x, y);
        pet.setOriginalPosition();
        return pet;
    }

    private ManaPet createManaPetFromConfig(ManaPetPrototype config, float x, float y) {
        Vector2 position = new Vector2(x, y);
        Texture image = new Texture(config.getImage());
        ManaPet manaPet = new ManaPet(config.getName(), image, position, config.getHealth(), config.getCost(),
                config.getMana(), config.getInterval());
        manaPet.setPosition(x, y);
        manaPet.setOriginalPosition();
        return manaPet;
    }

    private Obstacle createObstacleFromConfig(Prototype config, float x, float y) {
    Vector2 position = new Vector2(x, y);
    Texture image = new Texture(config.getImage());
    Obstacle obstacle = new Obstacle(config.getName(), image, position, config.getHealth(), config.getCost());
    obstacle.setPosition(x, y);
    obstacle.setOriginalPosition();
    return obstacle;
}

    public void render(SpriteBatch batch, BitmapFont font) {
        for (Entity entity : availablePets) {
            entity.draw(batch);
            font.setColor(Color.WHITE);
            font.getData().setScale(1.0f);
            font.draw(batch, (CharSequence) String.valueOf(entity.getCost()),
                    entity.getPosition().x + entity.getWidth() / 2, entity.getPosition().y - 40);
            font.getData().setScale(0.8f);
            font.draw(batch, (CharSequence) String.valueOf(entity.getName()),entity.getPosition().x - entity.getWidth()/2, entity.getPosition().y - 20); 
        }
    }

    public Array<Entity> getAvailablePets() {
        return availablePets;
    }

    public boolean isWithinBounds(float x, float y) {
        return bounds.contains(x, y);
    }
}
