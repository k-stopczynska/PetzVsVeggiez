package com.ainspiring.utils.screens;

import org.apache.logging.log4j.Logger;

import com.ainspiring.PetzVeggiezGame;
import com.ainspiring.utils.LoggerFactory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NewPlayerScreen extends ApplicationAdapter implements Screen {

private static final Logger LOGGER = LoggerFactory.getLogger(NewPlayerScreen.class);

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private TextField nameInput;
    private PetzVeggiezGame game;
    private Viewport viewport;
    private OrthographicCamera camera;

    public NewPlayerScreen(PetzVeggiezGame game) {
        this.game = game;
    }


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private void saveNewPlayer(String name) {
         Preferences prefs = Gdx.app.getPreferences("PlayerProgress");
         prefs.putInteger("playerLevel", 1);
         prefs.putString("playerName", name);
        prefs.flush();
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);
        game.setInputProcessor(stage);

        atlas = new TextureAtlas("rainbow-ui.atlas");
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"), atlas);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label nameLabel = new Label("Enter your name:", skin);
        table.add(nameLabel).padBottom(10);
        table.row();

        nameInput = new TextField("", skin);
        table.add(nameInput).width(200).padBottom(10);
        table.row();

        TextButton startButton = new TextButton("Start Game", skin);
        table.add(startButton).padTop(10);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String playerName = nameInput.getText().trim();
                 if (!playerName.isEmpty()) {
                    saveNewPlayer(playerName);
                    LOGGER.info("Player Name: " + playerName);
                    game.setScreen(new MainGameScreen(game));
                }
            }
        });
    }
}
