package com.ainspiring.entities;


import com.ainspiring.board.Board;
import com.ainspiring.utils.ConfigLoader;
import com.ainspiring.utils.LevelConfig;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.prototypes.VeggiezPrototype;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.Logger;

public class VeggiezBrain {

    private static final Logger LOGGER = LoggerFactory.getLogger(VeggiezBrain.class);
    private List<Veggie> veggies;
    private float elapsedTime;
    private float spawnInterval;
    private float waveDuration;
    private float waveStartTime;
    private Board board;
    private ConfigLoader configLoader;
    private boolean isWaveActive;

    public VeggiezBrain(Board board) {
        this.veggies = new ArrayList<Veggie>();
        this.elapsedTime = 0;
        this.spawnInterval = 15f; 
        this.waveDuration = 60f;
        this.board = board;
        this.configLoader = new ConfigLoader();
        configLoader.loadConfig();
        this.isWaveActive = false;
    }
    
    public void update(float delta) {
        if (!isWaveActive) {
            return;
        }

        elapsedTime += delta;

        if (elapsedTime - waveStartTime < waveDuration) {
            if (elapsedTime % spawnInterval < delta) {
                spawnVeggieWave();
            }

            for (Veggie veggie : veggies) {
                veggie.update(delta);
            }
        } else {
            stopWave();
        }
    }

    private void spawnVeggie(Set<Integer> usedRows) {
        Vector2 position = getPosition(usedRows);
        LevelConfig levelConfig = configLoader.getLevelConfig(1);

        if (levelConfig != null && levelConfig.getVeggiez() != null && !levelConfig.getVeggiez().isEmpty()) {
            VeggiezPrototype veggieConfig = levelConfig.getVeggiez()
                    .get(new Random().nextInt(levelConfig.getVeggiez().size()));

            Texture image = new Texture(veggieConfig.getImage());
            Veggie veggie = new Veggie(veggieConfig.getName(), image, position, veggieConfig.getHealth(), veggieConfig.getCost(), 
                    veggieConfig.getDamage(), veggieConfig.getSpeed());
            veggies.add(veggie);
        }
    }

    private Vector2 getPosition(Set<Integer> usedRows) {
        int randomRow;
        do {
            randomRow = new Random().nextInt(board.getRowCount());
        } while (usedRows.contains(randomRow));
        usedRows.add(randomRow);

        int y = board.getOffsetY() + randomRow * board.getCellHeight() - 16 + board.getCellHeight() / 2;
        Vector2 position = new Vector2(board.getBoardWidth() + board.getOffsetX() + 64, y);
        return position;
    }
    
    private void spawnVeggieWave() {
        int veggieCount = (int) (Math.random() * 3) + 1;
        Set<Integer> usedRows = new HashSet<>();
        for (int i = 0; i < veggieCount; i++) {
            spawnVeggie(usedRows);
        }
    }

    public List<Veggie> getVeggies() {
        return veggies;
    }

    public void startWave() {
        isWaveActive = true;
        waveStartTime = elapsedTime;
        veggies.clear();
    }

    public boolean isWaveOver() {
        return !isWaveActive || (elapsedTime - waveStartTime >= waveDuration);
    }

    public boolean isRoundOver() {
        return (elapsedTime - waveStartTime >= waveDuration);
    }

    public boolean getIsWaveActive() {
        return isWaveActive;
    }

    public void stopWave() {
        elapsedTime = 0;
        LOGGER.info("wave stopped");
        for (Veggie veggie : veggies) {
            veggie.dispose();
        }
        veggies.clear();
        LOGGER.info("setting wave to inactive");
        isWaveActive = false;
    }
}
