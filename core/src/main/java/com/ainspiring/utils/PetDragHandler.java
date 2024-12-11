package com.ainspiring.utils;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Pet;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;


public class PetDragHandler implements InputProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetDragHandler.class);

    private PetHub petHub;
    private Vector3 touchPosition;
    private Pet selectedPet;
    private OrthographicCamera camera;
    private boolean dragging;

    public PetDragHandler(PetHub petHub, OrthographicCamera camera) {
        this.petHub = petHub;
        this.camera = camera;
        this.touchPosition = new Vector3();
    } 


    // @Override
    // public boolean touchDown(int screenX, int screenY, int pointer, int button) {

    //     // super.touchDown(screenX, screenY, pointer, button);
    //     // Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
    //     LOGGER.info("Coordinates: " + screenX + ", " + screenY + ", " + pointer + ", " + button);
    //     touchPosition.set(screenX, screenY);
    //     LOGGER.info("Available pets: " + petHub.getAvailablePets());
    //     for (Pet pet : petHub.getAvailablePets()) {
    //         LOGGER.info(pet.getBoundingBox());
    //         if (pet.getBoundingBox().contains(touchPosition.x, touchPosition.y)) {
    //             selectedPet = pet;
    //             LOGGER.info("Touched pet: " + pet.getTexture());
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // @Override
    // public boolean touchDragged(int screenX, int screenY, int pointer) {
    //     if (selectedPet != null) {
    //         selectedPet.setPosition(screenX - selectedPet.getTexture().getWidth() / 2,
    //                 screenY - selectedPet.getTexture().getHeight() / 2);
    //         return true;
    //     }
    //     return false;
    // }

    // @Override
    // public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    //     if (selectedPet != null) {
    //         // TODO:Handle drop logic for valid board position
    //         selectedPet = null;
    //         return true;
    //     }
    //     return false;
    // }

	@Override public boolean mouseMoved (int screenX, int screenY) {
		// we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
		return false;
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        if (button != Input.Buttons.LEFT || pointer > 0)
            return false;
        camera.unproject(touchPosition.set(screenX, screenY, 0));
        dragging = true;
 		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(touchPosition.x, touchPosition.y, 0.25f, 16);
		shapeRenderer.end();
        for (Pet pet : petHub.getAvailablePets()) {
            LOGGER.info(pet.getBoundingBox());
            LOGGER.info("Touch position: " + screenX + ", " + screenY);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
    shapeRenderer.circle(pet.getBoundingBox().x, pet.getBoundingBox().y, 
            pet.getBoundingBox().width * 3, 16);
    shapeRenderer.end();
            if (pet.getBoundingBox().contains(touchPosition.x, touchPosition.y)) {
                selectedPet = pet;
                LOGGER.info("Touched pet: " + selectedPet.getTexture());
                return true;
            }
        }
        return true;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (!dragging) return false;
		camera.unproject(touchPosition.set(screenX, screenY, 0));
		return true;
	}

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0)
            return false;
        camera.unproject(touchPosition.set(screenX, screenY, 0));
        dragging = false;
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
	}}
