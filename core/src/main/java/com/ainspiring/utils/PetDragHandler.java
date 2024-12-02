package com.ainspiring.utils;

import org.apache.logging.log4j.Logger;

import com.ainspiring.entities.Pet;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PetDragHandler extends InputAdapter {

private static final Logger LOGGER = LoggerFactory.getLogger(PetDragHandler.class);

    private PetHub petHub;
    private Vector2 touchPosition;
    private Pet selectedPet;
    private OrthographicCamera camera;

    public PetDragHandler(PetHub petHub, OrthographicCamera camera) {
        this.petHub = petHub;
        this.camera = camera;
        this.touchPosition = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
         super.touchDown(screenX, screenY, pointer, button);
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        LOGGER.info("Coordinates: " + worldCoordinates);
        touchPosition.set(worldCoordinates.x, worldCoordinates.y);
        LOGGER.info("Available pets: " + petHub.getAvailablePets());
        for (Pet pet : petHub.getAvailablePets()) {
            LOGGER.info(pet.getBoundingBox());
            if (pet.getBoundingBox().contains(touchPosition.x, touchPosition.y)) {
                selectedPet = pet;
                LOGGER.info("Touched pet: " + pet.getTexture());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (selectedPet != null) {
            selectedPet.setPosition(screenX - selectedPet.getTexture().getWidth() / 2,
                    screenY - selectedPet.getTexture().getHeight() / 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (selectedPet != null) {
            // TODO:Handle drop logic for valid board position
            selectedPet = null;
            return true;
        }
        return false;
    }
}
