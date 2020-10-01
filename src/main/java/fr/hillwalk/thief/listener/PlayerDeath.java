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

            if(Thief.instance.takePlayer.get(e.getEntity().getUniqueId()) == null){
                return;
            }

            UtilsRef util = new UtilsRef();
            GuiSteal gui = new GuiSteal(e.getEntity());

            if(e.getEntity().getName().equalsIgnoreCase(Thief.instance.takePlayer.get(Thief.instance.takePlayer.get(e.getEntity().getUniqueId())).getName())){

                for(Player target : Bukkit.getServer().getOnlinePlayers()){

                    //On prend le nom du voleur
                    if(target.getName().equalsIgnoreCase(Thief.instance.takePlayer.get(e.getEntity().getUniqueId()).getName())){

                        //On ferme l'inventaire
                        target.closeInventory();

                        //On envoit un message au voleur
                        target.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("cancelled.deathPlayer")));


                        util.resetAll(target);
                        return;

                    }


                }
            }


        } else {
            return;
        }


    }


}
