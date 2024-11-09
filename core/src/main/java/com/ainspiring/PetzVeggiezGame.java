package com.ainspiring;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PetzVeggiezGame extends Game {
    private SpriteBatch batch;
    private Sprite sprite;
    private Texture image;
    private Vector2 position;
    @Override
    public void create() {
        setScreen(new FirstScreen());
        batch = new SpriteBatch();
        image = new Texture("yellow.png");
        sprite = new Sprite(image);
        position = new Vector2(Gdx.graphics.getWidth() / 2, sprite.getHeight() / 2 * sprite.getScaleY() / 2);
    }

        @Override
    public void render() {
        ScreenUtils.clear(0.34f, 1.54f, 0.74f, 1f);
        batch.begin();
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
        
        batch.end();
    }
}