package fr.hillwalk.thief.utils;

import fr.hillwalk.thief.Thief;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    //On ajoute les items dans la liste
    public List<Material> materialList(){
        List<Material> list = new ArrayList<>();

        for(String str : Thief.instance.getConfig().getStringList("material")){

            if(!list.contains(Material.valueOf(str.toUpperCase()))){
                list.add(Material.valueOf(str.toUpperCase()));
            }

        }

        return list;
    }


    public String replaceName(Player player){

            //On remplace le mot : %target% par le nom de la personne ciblé
            String word = Thief.instance.getConfig().getString("inventory.name");
            String replace = word.replaceAll("%target%", Thief.instance.target.get(player.getUniqueId()));
            return replace;

    }

    public Boolean checkItem(ItemStack item){

        System.out.println(Thief.instance.list);

        for(int i = 0; i < Thief.instance.list.size(); i++){

            System.out.println(item);
            if(Thief.instance.list.contains(item)){

                System.out.println(item);
                return true;

            }

        }

        return false;
    }


    public Integer randomNumber(int number){
        Random rand = new Random();


        return rand.nextInt(number);

    }



}
