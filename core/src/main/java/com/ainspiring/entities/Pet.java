package com.ainspiring.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.ainspiring.board.Board;
import com.ainspiring.utils.Fireball;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Pet extends FightingEntity {

private static final Logger LOGGER = LoggerFactory.getLogger(Pet.class);

    protected float elapsedTime = 0;
    private static final float FIREBALL_INTERVAL = 5f;
    private float fireballElapsedTime = 0f;
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private ShapeRenderer shapeRenderer;


    public Pet(String name, Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(name, image, position, health, cost, damage, speed);
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(float delta) {
        if (this.health > 0) {
            elapsedTime += delta;
            fireballElapsedTime += delta;

            if (fireballElapsedTime >= FIREBALL_INTERVAL) {
                fireballElapsedTime = 0f;
                spawnFireball();
            }

            Iterator<Fireball> iterator = fireballs.iterator();
            while (iterator.hasNext()) {
                Fireball fireball = iterator.next();
                fireball.update(delta);
                if (!fireball.isActive()) {
                    iterator.remove();
                }
            }
        }
    }
    
    private void spawnFireball() {
        Vector2 fireballStartPosition = new Vector2(position.x + getWidth(), position.y);
        Vector2 fireballVelocity = new Vector2(500, 0);
        fireballs.add(new Fireball(fireballStartPosition, fireballVelocity, this.damage));
    }

    public void checkCollisions(List<Fireball> fireballs, List<Veggie> veggies) {
        for (Fireball fireball : fireballs) {
            if (!fireball.isActive())
                continue;

            for (Veggie veggie : veggies) {
                if (fireball.getBoundingBox().overlaps(veggie.getBoundingBox())) {
                    LOGGER.info("Damage!!! to " + veggie.getName() + ": " + fireball.getDamage());
                    veggie.takeDamage(fireball.getDamage());
                    fireball.setPosition(position.x, 2000);
                    break;
                }
            }
        }
    }

    public List<Fireball> getFireballs() {
        return fireballs;
    }

    public Pet clone() {
        return new Pet(
                this.name,
                new Texture(this.image.getTextureData()),
                new Vector2(this.position.x, this.position.y),
                this.health,
                this.cost,
                this.damage,
                this.speed);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (isPlaced()) {
            for (Fireball fireball : fireballs) {
                fireball.render(batch);
            }} 
    }
}
