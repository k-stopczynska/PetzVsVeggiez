package com.ainspiring.utils;

import java.util.HashMap;

public class GameConfig {
     private HashMap<String, LevelConfig> levels;
        
    public GameConfig() {}

    public HashMap<String, LevelConfig> getLevels() {
        return levels;
    }

        public void setLevels(HashMap<String, LevelConfig> levels) {
        this.levels = levels;
    }
}
