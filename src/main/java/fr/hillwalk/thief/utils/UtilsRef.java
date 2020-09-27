package fr.hillwalk.thief.utils;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Items;
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



    public Integer randomNumber(int number){
        Random rand = new Random();


        return rand.nextInt(number);

    }


    //On va CHECKER tous les items
    public boolean checkItemsNames(ItemStack item){


        for (String string : Items.getItems().getConfigurationSection("items").getKeys(false)) {
            String name = Items.getItems().getString("items." + string + ".name");

            if(item.getItemMeta().getDisplayName() != null){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(name)){

                    return true;

                }
            } else {
                continue;
            }


        }

        return false;

    }


    //On va CHECKER tous les items
    public boolean checkItemsLore(ItemStack item){


        for (String string : Items.getItems().getConfigurationSection("items").getKeys(false)) {
            List<String> lore = Items.getItems().getStringList("items." + string + ".lore");


            if(item.getItemMeta().getLore() != null){

                for(String str : lore){

                    if(item.getItemMeta().getLore().contains(str)){
                        return true;
                    }

                }


            } else {
                continue;
            }

        }

        return false;

    }

}
