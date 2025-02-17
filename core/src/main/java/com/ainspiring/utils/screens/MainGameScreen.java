package com.ainspiring.utils.screens;

import org.apache.logging.log4j.Logger;

import com.ainspiring.PetzVeggiezGame;
import com.ainspiring.Player;
import com.ainspiring.board.Board;
import com.ainspiring.entities.Entity;
import com.ainspiring.entities.ManaPet;
import com.ainspiring.entities.Pet;
import com.ainspiring.entities.Veggie;
import com.ainspiring.entities.VeggiezBrain;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.PetHub;
import com.ainspiring.utils.screens.NewPlayerScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGameScreen implements Screen, InputProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(MainGameScreen.class);

    private PetzVeggiezGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    private Player player;
    private Board board;
    private VeggiezBrain veggiezBrain;
    private PetHub petHub;

    private Vector3 touchPosition;
    private Entity selectedPet;
    private boolean dragging;
    private boolean isLoser = false;
    private boolean isWinner = false;
    private boolean isFirstGame = true;

        // public final static float SCALE = 32f;
	// public final static float INV_SCALE = 1.f/SCALE;
	// public final static float VP_WIDTH = Gdx.graphics.getWidth();
	// public final static float VP_HEIGHT = Gdx.graphics.getHeight();

    public MainGameScreen(PetzVeggiezGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        viewport = new ExtendViewport(screenWidth, screenHeight, camera);
        viewport.update(screenWidth, screenHeight, true);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);

        batch = new SpriteBatch();
        font = new BitmapFont();
        board = new Board();
        veggiezBrain = new VeggiezBrain(board);
        petHub = new PetHub();

        Preferences prefs = Gdx.app.getPreferences("PlayerProgress");
        String name = prefs.getString("playerName", "PlayerOne");
        int level = prefs.getInteger("playerLevel", 1);
        player = new Player(name, level);

        touchPosition = new Vector3();

        game.addInputProcessor(this);
        game.addInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
        board.render();
        veggiezBrain.update(delta);

        if (veggiezBrain.isWaveOver() && !isFirstGame)
        {
            LOGGER.info("Time is up, player won");
            isWinner = true;
        }
       
        if (veggiezBrain.isWaveOver() && isFirstGame) {
            LOGGER.info("Starting loading progress on new game...");
            LOGGER.info("Player " + player.getName() + " is on level " + player.getLevel());
            player.loadProgress();
            board.clearPetsOnBoard();
            isFirstGame = false;
            // TODO: loading game screen
            veggiezBrain.startWave();
        }

        if (isWinner) {
            board.clearPetsOnBoard();
            LOGGER.info("Starting new level");
            player.levelUp();
            LOGGER.info("Player " + player.getName() + " level up to " + player.getLevel());

            isWinner = false;
            //TODO: level up screen
            veggiezBrain.startWave();
        }

        if (isLoser) {
            LOGGER.info("Player lost, game over, loading same level");
            veggiezBrain.stopWave();
            board.clearPetsOnBoard();
            //TODO: game over screen

            player.resetLevel();
            isLoser = false;
            veggiezBrain.startWave();
        }

        batch.begin();
        renderPlayerName();
        renderGatheredMana();
        renderLevel();

        petHub.render(batch, font);
        for (Veggie veggie : veggiezBrain.getVeggies()) {
            batch.draw(veggie.getImage(), veggie.getPosition().x, veggie.getPosition().y);
            veggie.checkCollisions(board.getPetsOnBoard(), batch);
            board.getPetsOnBoard().removeIf(pet -> pet.getHealth() <= 0);
            if (veggie.getPosition().x <= board.getOffsetX()) {
                isLoser = true;
            }
        }

        for (Entity pet : board.getPetsOnBoard()) {
            pet.draw(batch);
            if (pet instanceof Pet) {
                ((Pet) pet).checkCollisions(((Pet) pet).getFireballs(), veggiezBrain.getVeggies());
                ((Pet) pet).getFireballs().removeIf(fireball -> !fireball.isActive());
                veggiezBrain.getVeggies().removeIf(veggie -> veggie.getHealth() <= 0);
            }
            if (pet instanceof ManaPet) {
                ManaPet manaPet = (ManaPet) pet;
                if (manaPet.getHasGeneratedMana())
                    spawnManaStar(manaPet);
            }
        }

        if (dragging && selectedPet != null) {
            selectedPet.draw(batch);
        }

        batch.end();
    }

    private void spawnManaStar(ManaPet manaPet) {
        Texture starImage = new Texture("star.png");
        Sprite star = new Sprite(starImage);
        Vector2 starPosition = new Vector2(manaPet.getPosition().x, manaPet.getPosition().y + 30);
        star.setPosition(starPosition.x, starPosition.y);
        manaPet.setStarBoundingBox(starPosition, star);
        star.draw(batch);
    }

    private void renderGatheredMana() {
        font.setColor(Color.WHITE);
        font.getData().setScale(3.0f);
        font.draw(batch, player.getGatheredMana(), 300, 650);
    }

    private void renderPlayerName() {
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        font.draw(batch, player.getName(), 50, 650);
    }

    private void renderLevel() {
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        font.draw(batch, String.valueOf(player.getLevel()), 250, 650);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        board.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button != Input.Buttons.LEFT || pointer > 0)
            return false;
        touchPosition.set(screenX, screenY, 0);
        camera.unproject(touchPosition);
		
        this.dragging = true;
     
        for (Entity entity : this.petHub.getAvailablePets()) {
            if (entity.getBoundingBox().contains(touchPosition.x, touchPosition.y)
                    && Integer.valueOf(String.valueOf(player.getGatheredMana())) >= entity.getCost()) {
                selectedPet = entity.clone();
                selectedPet.setOriginalPosition();
                return true;
            }
        }
        
        for (Entity entity : board.getPetsOnBoard()) {
            if (entity instanceof ManaPet) {
                ManaPet manaPet = (ManaPet) entity;
                if (manaPet.isWithinBounds(touchPosition.x, touchPosition.y)) {
                    manaPet.setHasGeneratedMana();
                    player.gatherMana(manaPet.getMana());
                }
            }
        }
        return true;
     }

     @Override
     public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (!dragging || selectedPet == null || selectedPet.isPlaced()) 
        return false;

    Vector3 worldCoords = camera.unproject(touchPosition.set(screenX, screenY, 0));
    float newX = worldCoords.x - (selectedPet.getWidth() / 2);
    float newY = worldCoords.y - (selectedPet.getHeight() / 2);
    selectedPet.setPosition(newX, newY);
    return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (button != Input.Buttons.LEFT || pointer > 0 || selectedPet == null) 
        return false;

    Vector3 worldCoords = camera.unproject(touchPosition.set(screenX, screenY, 0));
    Vector2 position = new Vector2(worldCoords.x, worldCoords.y);

    board.placePet(selectedPet, position);
    if (selectedPet.isPlaced()) player.spendMana(selectedPet.getCost());
    dragging = false;
    selectedPet = null;
    return true;
     }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

    @Override
    public boolean touchCancelled(int arg0, int arg1, int arg2, int arg3) {
        return false;
    }
}

