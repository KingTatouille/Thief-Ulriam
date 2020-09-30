package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInvEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent e){

        UtilsRef util = new UtilsRef();

        if(Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()) == null){
            return ;
        }

        //On remplace le mot : %target% par le nom de la personne ciblé
        String word = Thief.instance.getConfig().getString("inventory.name");
        String replace = word.replaceAll("%target%", Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()).getName());

        if(e.getView().getTitle().equalsIgnoreCase(replace)){

            if(Thief.instance.taskId.get((Player) e.getPlayer()) != null){
                Bukkit.getScheduler().cancelTask(Thief.instance.taskId.get(e.getPlayer().getUniqueId()));

            }
            util.resetAll((Player) e.getPlayer());



        }
    }


}
