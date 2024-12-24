package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class FightingEntity extends Entity {
    
    protected int damage;
    protected float speed;
    protected Animation<TextureRegion> runAnimation;
    protected Animation<TextureRegion> walkAnimation;
    protected Animation<TextureRegion> specialAttackAnimation;
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> hurtAnimation;
    protected Animation<TextureRegion> dieAnimation;

    public FightingEntity(Texture image, Vector2 position, int health, int cost, int damage, float speed) {
        super(image, position, health, cost);
        this.damage = damage;
        this.speed = speed;
    }

    public void attack(Entity target) {
        target.health -= this.damage;
    }

    @Override
    public void update(float delta) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void setRunningAnimation(Animation<TextureRegion> animation) {
        this.runAnimation = animation;
    }

    public void setWalkingAnimation(Animation<TextureRegion> animation) {
        this.walkAnimation = animation;
    }

    public void setAttackAnimation(Animation<TextureRegion> animation) {
        this.specialAttackAnimation = animation;
    }

    public void setIdleAnimation(Animation<TextureRegion> animation) {
        this.idleAnimation = animation;
    }

    public void setHurtAnimation(Animation<TextureRegion> animation) {
        this.hurtAnimation = animation;
    }

    public void setDieAnimation(Animation<TextureRegion> animation) {
        this.dieAnimation = animation;
    }
}
