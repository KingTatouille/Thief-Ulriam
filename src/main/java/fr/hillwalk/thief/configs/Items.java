package fr.hillwalk.thief.configs;

import fr.hillwalk.thief.Thief;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Items {

    private static File itemsFiles;
    private static File itemsFilesIn;
    private static FileConfiguration items;


    public static void setup() {
        itemsFilesIn = new File(Bukkit.getServer().getPluginManager().getPlugin(Thief.instance.getName()).getDataFolder(),"items");
        itemsFiles = new File(itemsFilesIn, "items.yml");


        if(!itemsFilesIn.exists()) {

            try {
                Thief.instance.saveResource("items/items.yml", false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if(!itemsFiles.exists()) {
            try {
                itemsFiles.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        items = YamlConfiguration.loadConfiguration(itemsFiles);


    }


    public static FileConfiguration getItems() {

        return items;
    }


    public static void save() {

        try {
            items.save(itemsFiles);
        } catch (IOException e) {

            Thief.instance.getLogger().info("Save doesn't work ! We have a problem sir!");
        }
    }

    public static void reload() {
        if (itemsFiles == null) {
            itemsFiles = new File(itemsFilesIn, "items.yml");
        }
        items = YamlConfiguration.loadConfiguration(itemsFiles);



    }


}
