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
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void inventoryClick(InventoryClickEvent e){



        if(Thief.instance.takePlayer.get(e.getWhoClicked().getUniqueId()) == null){
            return ;
        }


        PlayersConfig config = new PlayersConfig();
        UtilsRef util = new UtilsRef();

    try{
        //On remplace le mot : %target% par le nom de la personne ciblé
        String word = Thief.instance.getConfig().getString("inventory.name");
        String replace = word.replaceAll("%target%", Thief.instance.takePlayer.get(e.getWhoClicked().getUniqueId()).getName());

        if(e.getView().getTitle().equalsIgnoreCase(replace)){

        //On cancel l'action
        e.setCancelled(true);

        //Si l'item est null alors on return, si il est bon on ajoute
        if(e.getCurrentItem() == null){
            return;
        } else {
            e.getView().getPlayer().getInventory().addItem(e.getCurrentItem());
        }

        //On verifie tout les joueurs et ensuite on retire l'item pris
        for(Player player : Bukkit.getServer().getOnlinePlayers()){

            if(player.getName().equalsIgnoreCase(Thief.instance.takePlayer.get(e.getWhoClicked().getUniqueId()).getName())){
               for(ItemStack item : player.getInventory().getStorageContents()){

                       player.getInventory().remove(e.getCurrentItem());
                       e.getView().getPlayer().closeInventory();

                       //On clear l'inventaire
                       Thief.instance.invStealed.remove(e.getWhoClicked().getUniqueId());

                       Thief.instance.list.clear();
                       return;
                   }


            }

        }
        }

    } catch (NullPointerException ex){

        ex.printStackTrace();
    }

    }




}
