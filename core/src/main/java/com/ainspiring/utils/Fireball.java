package com.ainspiring.utils;


import com.ainspiring.entities.Pet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fireball {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Vector2 position;
    private final Vector2 velocity;
    private boolean isActive;
    private int damage;
    private Rectangle boundingBox;

    Texture fireball1 = new Texture("fireball_1.png");
    Texture fireball2 = new Texture("fireball_2.png");
    Texture fireball3 = new Texture("fireball_3.png");
    Texture fireball4 = new Texture("fireball_4.png");
    Texture fireball5 = new Texture("fireball_5.png");
    Texture fireball6 = new Texture("fireball_6.png");

    Texture[] fireballTextures = { fireball1, fireball2, fireball3, fireball4, fireball5, fireball6 };

    public Fireball(Vector2 startPosition, Vector2 velocity, int damage) {
        TextureRegion[] frames = new TextureRegion[fireballTextures.length];
        for (int i = 0; i < fireballTextures.length; i++) {
            frames[i] = new TextureRegion(fireballTextures[i]);
        }

        this.animation = new Animation<>(0.1f, frames);
        this.stateTime = 0f;
        this.position = new Vector2(startPosition);
        this.velocity = velocity;
        this.isActive = true;
        this.damage = damage;
        this.boundingBox = new Rectangle(position.x, position.y, 160, 32);
    }

    public void update(float delta) {
        if (!isActive)
            return;
        stateTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);

        if (position.x > 1280 || position.y > 720) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!isActive)
            return;
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }

    public boolean isActive() {
        return isActive;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public int getDamage() {
        return this.damage;
    }

    public void dispose() {
        for (Texture texture : fireballTextures) {
            texture.dispose();
        }
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }
}

