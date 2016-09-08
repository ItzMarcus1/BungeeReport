package me.itzmarcus.bungeereport.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by marcus on 07-09-2016.
 */
public class FileUtils {

    private final static String DIRECTORY = "plugins/BungeeReport/";

    public void getNewFile(String fileName) {
        File file = new File(DIRECTORY, fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile(Configuration configuration, String fileName) {
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, getFile(fileName));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig(String fileName) {
        File file = new File(DIRECTORY, fileName);
        try{
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFile(String name) {
        File file = new File(DIRECTORY, name);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public boolean fileExists(String name) {
        File file = new File(DIRECTORY, name);
        if(!file.exists()) {
            return false;
        }
        return true;
    }
}
