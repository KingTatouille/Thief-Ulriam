package fr.hillwalk.thief;

import fr.hillwalk.thief.commands.StealCommand;
import fr.hillwalk.thief.listener.OnJoin;
import fr.hillwalk.thief.listener.StealInteractionEvent;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class Thief extends JavaPlugin {

    //Ce sont les deux seuls variables en static.
    public static Thief instance;
    public static String prefix;


    @Override
    public void onEnable(){

        //On instantie
        instance = this;

        //On annonce le logger
        this.getLogger().info("est maintenant load !");

        //Config
        saveDefaultConfig();

        //Commandes
        getCommand("steal").setExecutor(new StealCommand());

        //évènements
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),this);
        pm.registerEvents(new StealInteractionEvent(), this);


        //Première fois que je met ça comme ça.
        UtilsRef util = new UtilsRef();


        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + " ");


    }



    @Override
    public void onDisable(){

        getLogger().info("est unload !");


    }


}
