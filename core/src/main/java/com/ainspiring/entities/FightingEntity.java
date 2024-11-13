package com.ainspiring.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class FightingEntity extends Entity {
    
    protected int damage;
    protected int speed;
    protected Animation<TextureRegion> runningAnimation;
    protected Animation<TextureRegion> walkingAnimation;
    protected Animation<TextureRegion> attackingAnimation;
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> hurtAnimation;
    protected Animation<TextureRegion> dieAnimation;

    public FightingEntity(Texture image, Vector2 position, int health, int cost, int damage, int speed) {
        super(image, position, health, cost);
        this.damage = damage;
        this.speed = speed;
    }

    public void attack(Entity target) {
        target.health -= this.damage;
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void setRunningAnimation(Animation<TextureRegion> animation) {
        this.runningAnimation = animation;
    }

    public void setWalkingAnimation(Animation<TextureRegion> animation) {
        this.walkingAnimation = animation;
    }

    public void setAttackingAnimation(Animation<TextureRegion> animation) {
        this.attackingAnimation = animation;
    }

    public void setIdleAnimation(Animation<TextureRegion> animation) {
        this.idleAnimation = animation;
    }

    public void setHurtAnimation(Animation<TextureRegion> animation) {
        this.hurtAnimation = animation;
    }

    public void setDyingAnimation(Animation<TextureRegion> animation) {
        this.dieAnimation = animation;
    }

}
