package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import javax.rmi.CORBA.Util;

public class PlayerDrop implements Listener {

    @EventHandler (priority = EventPriority.LOW)
    public void playerDropItem(PlayerDropItemEvent e){

        UtilsRef util = new UtilsRef();
        GuiSteal gui = new GuiSteal(e.getPlayer());

        //On prend le nom de la personne volé
        if(e.getPlayer().getName().equalsIgnoreCase(Thief.instance.target.get(Thief.instance.targetId.get(e.getPlayer().getUniqueId())))){
            System.out.println("1");

            for(Player target : Bukkit.getServer().getOnlinePlayers()){

                //On prend le nom du voleur
                if(target.getName().equalsIgnoreCase(Thief.instance.target.get(e.getPlayer().getUniqueId()))){

                    for(int i = 0; i <= Thief.instance.getConfig().getInt("inventory.size") - 1; i++){

                        //On regarde si l'item n'est pas null (AIR)
                        if(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i) != null){

                            //Si les deux items se correspondent alors on effectue les actions.
                            if(e.getItemDrop().getItemStack().getType().equals(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType())){

                                Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType(), Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() - e.getItemDrop().getItemStack().getAmount()));
                                target.openInventory(Thief.instance.invStealed.get(target.getUniqueId()));
                                return;
                            }

                        } else {
                            continue;
                        }


                    }

                    return;

                }


            }

        }

    }



}
