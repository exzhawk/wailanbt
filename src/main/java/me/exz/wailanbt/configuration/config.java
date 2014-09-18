package me.exz.wailanbt.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.exz.wailanbt.util.LogHelper;

import java.io.*;

public class config {
    public static JsonElement configJson;
    private static File configFile = null;

    public static void init(File file){
        configFile=file;
        if (file.exists()){
            loadConfig();
        }else{
            LogHelper.info("No config file found.");
            try {
                if (file.createNewFile()){
                    LogHelper.info("Empty config created");
                }
            } catch (IOException e) {
                LogHelper.info("Create config file failed");
                e.printStackTrace();
            }
        }
    }
    public static void loadConfig() {
        try {
            InputStream inputStream = new FileInputStream(configFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JsonParser parser = new JsonParser();
            configJson = parser.parse(inputStreamReader);
            LogHelper.info("Config Loaded");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
