package com.ainspiring.entities;


import java.util.List;

import org.apache.logging.log4j.Logger;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Veggie extends FightingEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Veggie.class);

    protected float elapsedTime = 0;
    private float attackCooldown = 2.0f;
    private float attackTimer = 0;
    private boolean isAttacking = false;
    private Texture rightGloveImage;
    private Texture leftGloveImage;

    public Veggie(String name, Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(name, image, position, health, cost, damage, speed);
        this.rightGloveImage = new Texture("boxing_glove_right.png");
        this.leftGloveImage = new Texture("boxing_glove_left.png");
    }

    @Override
    public void update(float delta) {
            if (this.health > 0) {
                elapsedTime += delta;
                if (position.x > 0 && !isAttacking) {
                    position.x -= this.speed;
                    setPosition(position.x, position.y);
                }
                attackTimer += delta;
            }
        }


    public void checkCollisions(List<Entity> petsOnBoard,  SpriteBatch batch) {
        for (Entity petOnBoard : petsOnBoard) {
            if (petOnBoard.getBoundingBox().overlaps(getBoundingBox())) {
                isAttacking = true;
                attackPet(petOnBoard, batch);
                break;
            }
        }
    }
    
    private void attackPet(Entity petOnBoard, SpriteBatch batch) {
        renderAttack(batch);
        if (attackTimer >= attackCooldown) {
            LOGGER.info("Veggie punches " + petOnBoard.getName() + " for " + damage + " damage.");
            petOnBoard.takeDamage(this.damage);
            attackTimer = 0;
        }
        if (petOnBoard.getHealth() <= 0) {
            isAttacking = false;
        }
    }
    
        private void renderAttack(SpriteBatch batch) {
            batch.draw(rightGloveImage, position.x - rightGloveImage.getWidth() / 4, position.y);
            batch.draw(leftGloveImage, position.x - leftGloveImage.getWidth() / 2, position.y + 12);
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
