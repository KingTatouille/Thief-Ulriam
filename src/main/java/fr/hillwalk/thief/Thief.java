package fr.hillwalk.thief;

import fr.hillwalk.thief.commands.Commandes;
import fr.hillwalk.thief.configs.Items;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Thief extends JavaPlugin {

    //Ce sont les deux seuls variables en static.
    public static Thief instance;
    public static String prefix;

    //HashMap
    public HashMap<UUID, Boolean> isThief = new HashMap<UUID, Boolean>();
    public HashMap<UUID, Boolean> stealing = new HashMap<UUID, Boolean>();
    public HashMap<UUID, Inventory> invStealed = new HashMap<UUID, Inventory>();
    public HashMap<UUID, Player> takePlayer = new HashMap<UUID, Player>();
    public HashMap<UUID, Integer> taskId = new HashMap<UUID, Integer>();

    //Si le joueur ne peut pas enlever la barboss
    public HashMap<UUID, BossBar> bossBar = new HashMap<UUID, BossBar>();


    //List
    public List<ItemStack> list = new ArrayList<ItemStack>();


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

        //Items
        Items.setup();

        //Commandes
        getCommand("steal").setExecutor(new Commandes());

        //évènements
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),this);
        pm.registerEvents(new InteractionPlayer(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new PlayerDrop(), this);
        pm.registerEvents(new PlayerDeath(), this);


        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + " ");


        PlayersConfig config = new PlayersConfig();

        //On verifie les joueurs déjà connecté au cas ou c'est un reload
        for(Player player : Bukkit.getServer().getOnlinePlayers()){

            isThief.put(player.getUniqueId(), false);
            stealing.put(player.getUniqueId(), false);
            config.setup(player);


        }


    }



    @Override
    public void onDisable(){

        getLogger().info("est unload !");


    }


}
