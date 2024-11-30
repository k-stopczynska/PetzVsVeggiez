package com.ainspiring.utils;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.ainspiring.PetzVeggiezGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;
import java.util.List;

public class ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
        private Map<Integer, LevelConfig> levelConfigs;

        public ConfigLoader() {
            this.levelConfigs = new HashMap<>();
        }

        public void loadConfig() {
               Json json = new Json();
               GameConfig gameConfig = json.fromJson(GameConfig.class, Gdx.files.internal("level_config.json"));
                
               for (Map.Entry<String, LevelConfig> entry : gameConfig.getLevels().entrySet()) {
                   int level = Integer.parseInt(entry.getKey());
                   levelConfigs.put(level, entry.getValue());
               }
               LOGGER.info("LEVEL CONFIGS: " + levelConfigs);
        }

        public LevelConfig getLevelConfig(int level) {
            return levelConfigs.get(level);
        }
}
