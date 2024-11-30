package com.ainspiring;

import org.apache.logging.log4j.Logger;

import com.ainspiring.board.Board;
import com.ainspiring.entities.Veggie;
import com.ainspiring.entities.VeggiezBrain;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PetzVeggiezGame extends Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetzVeggiezGame.class);

    private Viewport viewport;
    private Camera camera;
    private Stage stage;

    private SpriteBatch batch;
    private Sprite sprite;
    private Texture image;
    private Vector2 position;

    private Board board;
    private VeggiezBrain veggiezBrain;

    @Override
    public void create() {
        // setScreen(new FirstScreen());       
        board = new Board(); 
        batch = new SpriteBatch();
        image = new Texture("yellow.png");
        sprite = new Sprite(image);
        position = new Vector2(Gdx.graphics.getWidth() / 2, sprite.getHeight() / 2 * sprite.getScaleY() / 2);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(30, 30 * (h / w));
        viewport = new ExtendViewport(720, 475, camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        stage = new Stage(viewport, batch);
        veggiezBrain = new VeggiezBrain(board);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.34f, 1.54f, 0.74f, 1f);
        board.render();
        float delta = Gdx.graphics.getDeltaTime();
        veggiezBrain.update(delta);
        if (veggiezBrain.isWaveOver()) {
            veggiezBrain.startWave();
        }
        batch.begin();

        for (Veggie veggie : veggiezBrain.getVeggies()) {
            batch.draw(veggie.getImage(), veggie.getPosition().x, veggie.getPosition().y);
        }
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height / width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        batch.dispose();
        board.dispose();
    }


}