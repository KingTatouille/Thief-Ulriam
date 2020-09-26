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
import java.util.UUID;

public class PlayersConfig {



    private File playerFileIn = new File(Bukkit.getServer().getPluginManager().getPlugin(Thief.instance.getName()).getDataFolder(), "players");
    private File playerFile;
    private FileConfiguration playerYml;


    public void setup(Player player) {
        playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");


        if(!playerFileIn.exists()) {

            try {
                playerFileIn.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if(!playerFile.exists()) {
            try {
                playerFile.createNewFile();

                getPlayer(player).set("name", player.getName());
                save(player);
                getPlayer(player).set("thief".toUpperCase(), false);
                save(player);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }




        playerYml = YamlConfiguration.loadConfiguration(playerFile);


    }


    public FileConfiguration getPlayer(Player player) {
        playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
        playerYml = YamlConfiguration.loadConfiguration(playerFile);

        return playerYml;
    }


    public void save(Player player) {

        try {
            playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
            playerYml.save(playerFile);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void reload(Player player) {
        if (playerFile == null) {
            playerFile = new File(playerFileIn, String.valueOf(player.getUniqueId()) + ".yml");
        }
        playerYml = YamlConfiguration.loadConfiguration(playerFile);



    }
}
