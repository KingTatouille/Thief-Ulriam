package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void inventoryClick(InventoryClickEvent e){


        PlayersConfig config = new PlayersConfig();
        UtilsRef util = new UtilsRef();

    try{
        if(e.getView().getTitle().equalsIgnoreCase(util.replaceName((Player) e.getWhoClicked()))){

        //On cancel l'action
        e.setCancelled(true);

        //Si l'item est null alors on return, si il est bon on ajoute
        if(e.getCurrentItem() == null){
            //On clear l'inventaire
            Thief.instance.invStealed.get(e.getWhoClicked().getUniqueId());
            return;
        } else {
            e.getView().getPlayer().getInventory().addItem(e.getCurrentItem());
        }

        //On verifie tout les joueurs et ensuite on retire l'item pris
        for(Player player : Bukkit.getServer().getOnlinePlayers()){

            if(player.getName().equalsIgnoreCase(Thief.instance.target.get(e.getWhoClicked().getUniqueId()))){
                player.getInventory().remove(e.getCurrentItem());
                Thief.instance.itemStealed.put(e.getWhoClicked().getUniqueId(), e.getCurrentItem());
            }

        }

            e.getView().getPlayer().closeInventory();
            //On clear l'inventaire
            Thief.instance.invStealed.get(e.getWhoClicked().getUniqueId()).clear();

            Thief.instance.list.clear();



        }

    } catch (NullPointerException ex){

        ex.getStackTrace();
    }

    }




}
