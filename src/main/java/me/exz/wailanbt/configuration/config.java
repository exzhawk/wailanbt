package me.exz.wailanbt.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.exz.wailanbt.reference.Reference;
import me.exz.wailanbt.util.LogHelper;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class config {
    public static JsonObject configJson;
    private static File configDir;

    public static void init(File dir) {
        configDir = new File(dir, Reference.MOD_ID);
        if (!configDir.exists()) {
            try {
                if (configDir.mkdir()) {
                    File defaultFile = new File(configDir, "default.json");
                    if (defaultFile.createNewFile()) {
                        LogHelper.info("Empty config created");

                    } else {
                        LogHelper.error("Failed to create empty config file");
                    }
                } else {
                    LogHelper.error("Failed to create config folder");
                }
            } catch (Exception e) {
                LogHelper.info("Create config file failed");
                e.printStackTrace();
            }
        }
        loadConfig();
    }

    public static void loadConfig() {
        configJson = new JsonObject();
        File[] configFiles = configDir.listFiles();
        if (!(configFiles == null)) {
            for (File configFile : configFiles) {
                try {
                    InputStream inputStream = new FileInputStream(configFile);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonElementCurrent = parser.parse(inputStreamReader);
                    if (jsonElementCurrent.isJsonObject()) {
                        JsonObject jsonObjectCurrent = jsonElementCurrent.getAsJsonObject();
                        mergeJson(jsonObjectCurrent);
                    } else {
                        LogHelper.error("Parse " + configFile.toString() + " failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        LogHelper.info("Config Loaded");
    }

    private static void mergeJson(JsonObject jsonObjectCurrent) {
        Set<Map.Entry<String, JsonElement>> holdItemA = jsonObjectCurrent.entrySet();
        try {
            for (Map.Entry<String, JsonElement> holdItem : holdItemA) {
                if (!configJson.has(holdItem.getKey())) {
                    configJson.add(holdItem.getKey(), holdItem.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

