package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDie(PlayerDeathEvent e){



        if(e.getEntity() instanceof Player){

            UtilsRef util = new UtilsRef();
            GuiSteal gui = new GuiSteal(e.getEntity());

            if(e.getEntity().getName().equalsIgnoreCase(Thief.instance.target.get(Thief.instance.targetId.get(e.getEntity().getUniqueId())))){

                for(Player target : Bukkit.getServer().getOnlinePlayers()){

                    //On prend le nom du voleur
                    if(target.getName().equalsIgnoreCase(Thief.instance.target.get(e.getEntity().getUniqueId()))){

                        //On ferme l'inventaire
                        target.closeInventory();

                        //On envoit un message au voleur
                        target.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("cancelled.dropItem")));

                        //On reset l'inventaire ayant eu une intéraction
                        gui.inventoryReset(target);

                        return;

                    }


                }
                gui.inventoryReset(e.getEntity());
            }


        } else {
            return;
        }


    }


}