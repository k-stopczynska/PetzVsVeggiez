package com.ainspiring.entities;

import com.ainspiring.board.Board;
import com.ainspiring.utils.ConfigLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

public class VeggiezBrain {
    private Array<Veggie> veggies;
    private float elapsedTime;
    private float spawnInterval;
    private float waveDuration;
    private float waveStartTime;
    private Board board;

    public VeggiezBrain(Board board) {
        this.veggies = new Array<>();
        this.elapsedTime = 0;
        this.spawnInterval = 5f; 
        this.waveDuration = 180f;
        this.board = board;
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

    private void spawnVeggie() {
        int randomRow = new Random().nextInt(board.getRowCount() + 1);
        int y = board.getOffsetY() + randomRow * board.getCellHeight() - 16 + board.getCellHeight() / 2;
        Vector2 position = new Vector2(board.getBoardWidth() + board.getOffsetX() + 64, y);
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfig();
        Texture image = new Texture("Broccoli.png");
        Veggie veggie = new Veggie(image, position, 100, 0, 10, 2);
        veggies.add(veggie);
    }
    
    private void spawnVeggieWave() {
    int veggieCount = (int) (Math.random() * 4) + 1;
    for (int i = 0; i < veggieCount; i++) {
        spawnVeggie();
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
