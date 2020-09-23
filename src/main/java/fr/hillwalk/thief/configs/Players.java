package fr.hillwalk.thief.configs;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Players {


    private static File messageFiles;
    private static File messageFilesIn;
    private static FileConfiguration messages;


    public static void setup(Player player) {
        messageFilesIn = new File(Bukkit.getServer().getPluginManager().getPlugin(Thief.instance.getName()).getDataFolder(),"players");
        messageFiles = new File(messageFilesIn, player.getUniqueId() + ".yml");


        if(!messageFilesIn.exists()) {

            try {
                Thief.instance.saveResource("players", false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if(!messageFiles.exists()) {
            try {
                messageFiles.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        messages = YamlConfiguration.loadConfiguration(messageFiles);


    }


    public static FileConfiguration getMessages() {
        return messages;
    }


    public static void save() {

        try {
            messages.save(messageFiles);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void reload(Player player) throws UnsupportedEncodingException {
        if (messageFiles == null) {
            messageFiles = new File(messageFilesIn, player.getUniqueId() + ".yml");
        }
        messages = YamlConfiguration.loadConfiguration(messageFiles);



    }
}
