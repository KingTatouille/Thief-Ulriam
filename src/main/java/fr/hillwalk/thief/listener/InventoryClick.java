package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.PlayersConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void inventoryClick(InventoryClickEvent e){


        PlayersConfig config = new PlayersConfig();


        if(config.getPlayer((Player) e.getView().getPlayer()).getBoolean("thief".toUpperCase())){
            return;
        }

        //On remplace le mot : %target% par le nom de la personne ciblé
            String word = Thief.instance.getConfig().getString("inventory.name");
            String replace = word.replaceAll("%target%", Thief.instance.target.get(e.getWhoClicked().getUniqueId()));

        if(e.getView().getTitle().equalsIgnoreCase(replace)){

        e.setCancelled(true);
        e.getView().getPlayer().getInventory().addItem(e.getCurrentItem());
        for(Player player : Bukkit.getServer().getOnlinePlayers()){

            if(player.getName().equalsIgnoreCase(Thief.instance.target.get(e.getWhoClicked().getUniqueId()))){
                player.getInventory().remove(e.getCurrentItem());
            }

        }

            e.getView().getPlayer().closeInventory();



        }



    }



}
