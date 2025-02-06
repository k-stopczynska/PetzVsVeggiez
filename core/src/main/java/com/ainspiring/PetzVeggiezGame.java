package com.ainspiring;


import org.apache.logging.log4j.Logger;

import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.screens.MainGameScreen;
import com.ainspiring.utils.screens.NewPlayerScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PetzVeggiezGame extends Game implements InputProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetzVeggiezGame.class);

    private InputMultiplexer multiplexer;


    @Override
    public void create() {
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        Preferences prefs = Gdx.app.getPreferences("PlayerProgress");

        if (prefs.getString("playerName") == null) {
            setScreen(new NewPlayerScreen(this));
        } else {
            setScreen(new MainGameScreen(this));
        }
        
    }

    public void setInputProcessor(InputProcessor processor) {
        multiplexer.clear();
        multiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void addInputProcessor(InputProcessor processor) {
        multiplexer.addProcessor(processor);
    }

    public void removeInputProcessor(InputProcessor processor) {
        multiplexer.removeProcessor(processor);
    }
    
    @Override
    public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        return false;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        return false;
	}

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
}