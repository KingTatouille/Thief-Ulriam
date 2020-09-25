package fr.hillwalk.thief;

import fr.hillwalk.thief.commands.StealCommand;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.listener.OnJoin;
import fr.hillwalk.thief.listener.StealInteractionEvent;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Thief extends JavaPlugin {

    //Ce sont les deux seuls variables en static.
    public static Thief instance;
    public static String prefix;

    //HashMap
    public HashMap<UUID, Boolean> isThief = new HashMap<UUID, Boolean>();
    public HashMap<UUID, Boolean> stealing = new HashMap<UUID, Boolean>();
    public HashMap<UUID, Inventory> invStealed = new HashMap<UUID, Inventory>();
    public List<ItemStack> list = new ArrayList<ItemStack>();

    //List



    @Override
    public void onEnable(){

        //On instantie
        instance = this;

        //On annonce le logger
        this.getLogger().info("est maintenant load !");

        //Config
        saveDefaultConfig();

        //Messages
        Messages.setup();

        //Commandes
        getCommand("steal").setExecutor(new StealCommand());

        //évènements
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),this);
        pm.registerEvents(new StealInteractionEvent(), this);


        //Première fois que je met ça comme ça.
        UtilsRef util = new UtilsRef();


        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + " ");


        //On verifie les joueurs déjà connecté au cas ou c'est un reload
        for(Player player : Bukkit.getServer().getOnlinePlayers()){

            isThief.put(player.getUniqueId(), false);
            stealing.put(player.getUniqueId(), false);


        }


    }



    @Override
    public void onDisable(){

        getLogger().info("est unload !");


    }


}
