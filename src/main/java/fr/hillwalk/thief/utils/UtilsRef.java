package fr.hillwalk.thief.utils;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.guis.GuiSteal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UtilsRef {

    //On références toutes les choses utiles ici !

    public String getColor(String params){


        return ChatColor.translateAlternateColorCodes('&', params);
    }



    public boolean checkPlayer(UUID uuid){

        File file = new File(Bukkit.getServer().getPluginManager().getPlugin(Thief.instance.getName()).getDataFolder() + File.separator + "players", uuid + ".yml");

        for (Player player : Bukkit.getServer().getOnlinePlayers()){

            if(!file.exists()){

                return false;
            }

        }


        return true;
    }


    public Integer randomNumber(int number){
        Random rand = new Random();


        return rand.nextInt(number);

    }



}
