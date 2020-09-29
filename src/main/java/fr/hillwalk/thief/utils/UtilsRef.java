package fr.hillwalk.thief.utils;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Items;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

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


    public Integer randomNumber(int number){
        Random rand = new Random();


        return rand.nextInt(number);

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


    public void resetAll(Player player){

        for(Player target : Bukkit.getServer().getOnlinePlayers()){


            //On prend le nom du voleur

                if(Thief.instance.taskId.get(target.getUniqueId()) != null){

                    //On dégage tout ce qui a été fait.
                    Thief.instance.isThief.put(target.getUniqueId(), true);
                    Thief.instance.stealing.put(target.getUniqueId(), false);
                    Thief.instance.takePlayer.remove(target.getUniqueId());
                    Thief.instance.bossBar.remove(target.getUniqueId());
                    Thief.instance.taskId.remove(target.getUniqueId());
                    Thief.instance.invStealed.remove(target.getUniqueId());

                }

            }


        //ON remet à jour les hashmaps
        Thief.instance.takePlayer.remove(player.getUniqueId());



    }

    public Location getLoc(Player player){

        int count = 0;

        while(count < 50){
            Location loc = Thief.instance.takePlayer.get(player.getUniqueId()).getLocation();
            System.out.println(loc);
            count++;
            return loc;

        }


    return null;
    }




    /*//On va CHECKER tous les items
    public boolean checkItemsNames(ItemStack item){


        for (String string : Items.getItems().getConfigurationSection("items").getKeys(false)) {
            System.out.println(string);
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

    }*/


}
