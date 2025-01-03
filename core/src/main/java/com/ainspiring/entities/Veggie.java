package com.ainspiring.entities;


import java.util.List;

import org.apache.logging.log4j.Logger;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Veggie extends FightingEntity {

private static final Logger LOGGER = LoggerFactory.getLogger(Veggie.class);

    protected float elapsedTime = 0;

    public Veggie(String name, Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(name, image, position, health, cost, damage, speed);
    }

    @Override
    public void update(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            if (position.x > 0) {
                position.x -= this.speed;
                setPosition(position.x, position.y);
            }
        }
    }

    public void checkCollisions(List<Entity> petsOnBoard, List<Veggie> veggies) {
        for (Veggie veggie: veggies) {
            for (Entity petOnBoard : petsOnBoard) {
                if (petOnBoard.getBoundingBox().overlaps(veggie.getBoundingBox())) {
                    LOGGER.info("Damage!!! to " + petOnBoard.getName() + ": " + veggie.getDamage());
                    petOnBoard.takeDamage(veggie.getDamage());
                    break;
                }
            }
        }
    }

    @Override
    public Veggie clone() {
        return new Veggie(name, image, position, health, cost, damage, speed);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }   
}
