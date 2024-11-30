package com.ainspiring.utils;


import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import java.util.Map;
import java.util.HashMap;

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
        }

        public LevelConfig getLevelConfig(int level) {
            return levelConfigs.get(level);
        }
}
