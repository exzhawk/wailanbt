package me.exz.wailanbt.configuration;

import com.google.gson.Gson;

import java.io.File;

public class config {
    public static File configFile = null;
    public static Gson gson = new Gson();
    public static void loadConfig(File suggestedConfigurationFile) {
        configFile= suggestedConfigurationFile;
        parseConfig();
    }
    public static void parseConfig(){
        //gson
    }
}
