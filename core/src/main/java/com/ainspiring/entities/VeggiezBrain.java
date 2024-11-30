package com.ainspiring.entities;

import com.ainspiring.PetzVeggiezGame;
import com.ainspiring.board.Board;
import com.ainspiring.utils.ConfigLoader;
import com.ainspiring.utils.LevelConfig;
import com.ainspiring.utils.LoggerFactory;
import com.ainspiring.utils.Prototype;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import org.apache.logging.log4j.Logger;

public class VeggiezBrain {

    private static final Logger LOGGER = LoggerFactory.getLogger(VeggiezBrain.class);
    private Array<Veggie> veggies;
    private float elapsedTime;
    private float spawnInterval;
    private float waveDuration;
    private float waveStartTime;
    private Board board;
    private ConfigLoader configLoader;

    public VeggiezBrain(Board board) {
        this.veggies = new Array<>();
        this.elapsedTime = 0;
        this.spawnInterval = 5f; 
        this.waveDuration = 180f;
        this.board = board;
        this.configLoader = new ConfigLoader();
        configLoader.loadConfig();
    }
    
    public void update(float delta) {
        elapsedTime += delta;

        if (elapsedTime - waveStartTime < waveDuration) {
            if (elapsedTime % spawnInterval < delta) {
                spawnVeggieWave();
            }

            for (Veggie veggie : veggies) {
                veggie.move(delta);
            }
        }
    }

    private void spawnVeggie(Set<Integer> usedRows) {
        Vector2 position = getPosition(usedRows);
        LevelConfig levelConfig = configLoader.getLevelConfig(1);

        if (levelConfig != null && levelConfig.getVeggiez() != null && !levelConfig.getVeggiez().isEmpty()) {
            Prototype veggieConfig = levelConfig.getVeggiez()
                    .get(new Random().nextInt(levelConfig.getVeggiez().size()));

            Texture image = new Texture(veggieConfig.getImage());
            Veggie veggie = new Veggie(image, position, veggieConfig.getHealth(), veggieConfig.getCost(), 
                    veggieConfig.getDamage(), veggieConfig.getSpeed());
            veggies.add(veggie);
        }
    }

    private Vector2 getPosition(Set<Integer> usedRows) {
        int randomRow;
        do {
            randomRow = new Random().nextInt(board.getRowCount() + 1);
        } while (usedRows.contains(randomRow));
        usedRows.add(randomRow);


        int y = board.getOffsetY() + randomRow * board.getCellHeight() - 16 + board.getCellHeight() / 2;
        Vector2 position = new Vector2(board.getBoardWidth() + board.getOffsetX() + 64, y);
        return position;
    }
    
    private void spawnVeggieWave() {
        int veggieCount = (int) (Math.random() * 4) + 1;
        Set<Integer> usedRows = new HashSet<>();
        for (int i = 0; i < veggieCount; i++) {
            spawnVeggie(usedRows);
        }
    }

    public Array<Veggie> getVeggies() {
        return veggies;
    }

    public void startWave() {
        waveStartTime = elapsedTime;
        veggies.clear();
    }

    public boolean isWaveOver() {
        return elapsedTime - waveStartTime >= waveDuration;
    }
}
