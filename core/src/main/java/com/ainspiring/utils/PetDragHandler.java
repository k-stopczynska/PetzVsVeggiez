package com.ainspiring.utils;

import com.ainspiring.entities.Pet;
import com.ainspiring.utils.PetHub;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class PetDragHandler extends InputAdapter {
    private PetHub petHub;
    private Vector2 touchPosition;
    private Pet selectedPet;

    public PetDragHandler(PetHub petHub) {
        this.petHub = petHub;
        this.touchPosition = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, screenY);
        for (Pet pet : petHub.getAvailablePets()) {
            if (pet.contains(touchPosition.x, touchPosition.y)) {
                selectedPet = pet;
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
