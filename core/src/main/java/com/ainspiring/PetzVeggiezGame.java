package com.ainspiring;

import org.apache.logging.log4j.Logger;

import com.ainspiring.board.Board;
import com.ainspiring.entities.Entity;
import com.ainspiring.entities.ManaPet;
import com.ainspiring.entities.Veggie;
import com.ainspiring.entities.VeggiezBrain;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.PetHub;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PetzVeggiezGame extends Game implements InputProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetzVeggiezGame.class);

    private Viewport viewport;
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private BitmapFont font;

    private Player player;
    private Board board;
    private VeggiezBrain veggiezBrain;
    private PetHub petHub;

    private Vector3 touchPosition;
    private Entity selectedPet;
    private boolean dragging;

    // public final static float SCALE = 32f;
	// public final static float INV_SCALE = 1.f/SCALE;
	// public final static float VP_WIDTH = Gdx.graphics.getWidth();
	// public final static float VP_HEIGHT = Gdx.graphics.getHeight();

    @Override
    public void create() {
        // setScreen(new FirstScreen());       
        board = new Board(); 
        batch = new SpriteBatch();
        font = new BitmapFont();
        veggiezBrain = new VeggiezBrain(board);
        petHub = new PetHub();
        player = new Player();

        camera = new OrthographicCamera();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        viewport = new ExtendViewport(screenWidth, screenHeight, camera);
        viewport.update(screenWidth, screenHeight, true);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
 
        touchPosition = new Vector3();

		Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.04f, 2.54f, 0.44f, 1f);
        board.render();

        float delta = Gdx.graphics.getDeltaTime();
        veggiezBrain.update(delta);
        if (veggiezBrain.isWaveOver()) {
            veggiezBrain.startWave();
        }
        batch.begin();
        renderGatheredMana();

        petHub.render(batch);
        for (Veggie veggie : veggiezBrain.getVeggies()) {
            batch.draw(veggie.getImage(), veggie.getPosition().x, veggie.getPosition().y);
            // TODO: implement drawing veggies so they will scale up properly and not cause memory leaks 
            // veggie.draw(batch);
        }
        for (Entity pet : board.getPetsOnBoard()) {
            pet.draw(batch);
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

    protected void spawnManaStar(ManaPet manaPet) {
        Texture starImage = new Texture("star.png");
        Sprite star = new Sprite(starImage);
        Vector2 starPosition = new Vector2(manaPet.getPosition().x, manaPet.getPosition().y + 30);
        star.setPosition(starPosition.x, starPosition.y);
        manaPet.setStarBoundingBox(starPosition, star);
        star.draw(batch);
    }

    protected void renderGatheredMana() {
        font.setColor(Color.BLACK);
        font.getData().setScale(3.0f);
        font.draw(batch, player.getGatheredMana(), 300, 650);
    }
    
    @Override
    public boolean mouseMoved (int screenX, int screenY) {
		// we can also handle mouse movement without anything pressed
		// camera.unproject(touchPosition.set(screenX, screenY, 0));
		return false;
	}

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0)
            return false;
        touchPosition.set(screenX, screenY, 0);
        camera.unproject(touchPosition);
		
        this.dragging = true;
     
        for (Entity entity : this.petHub.getAvailablePets()) {
            if (entity.getBoundingBox().contains(touchPosition.x, touchPosition.y)) {
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
                    LOGGER.info("Gathered mana: " + player.getGatheredMana());
                }
            }
        }
        return true;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
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
    dragging = false;
    selectedPet = null;
    return true;
    }
    
    @Override
    public boolean keyDown (int keycode) {
		return false;
	}

    @Override
    public boolean keyUp (int keycode) {
		return false;
	}

    @Override
    public boolean keyTyped (char character) {
		return false;
	}

    @Override
    public boolean scrolled(float x, float y) {
        return false;
    }
    
        @Override
    public boolean touchCancelled (int x, int y, int z, int w) {
		return false;
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        batch.dispose();
        font.dispose();
        board.dispose();
    }
}