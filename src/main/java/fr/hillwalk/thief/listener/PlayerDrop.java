package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.apache.commons.lang.ObjectUtils;
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

    @EventHandler (priority = EventPriority.NORMAL)
    public void playerDropItem(PlayerDropItemEvent e){


        if(Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()) == null){
            return;
        }

        UtilsRef util = new UtilsRef();


        try{
            for(Player target : Bukkit.getServer().getOnlinePlayers()){

                //On prend le nom du voleur
                if(target.getName().equalsIgnoreCase(Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()).getName())){

                    for(int i = 0; i <= Thief.instance.getConfig().getInt("inventory.size") - 1; i++){


                            //Si les deux items se correspondent alors on effectue les actions.
                            if(e.getItemDrop().getItemStack().getType().equals(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType())){

                                if(e.getItemDrop().getItemStack().getAmount() <= 0){
                                    Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Material.AIR));

                                } else {
                                    Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType(), Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() - e.getItemDrop().getItemStack().getAmount()));
                                }

                                target.updateInventory();

                            }



                    }

                    util.resetAll(Thief.instance.takePlayer.get(target.getUniqueId()));

                }


            }
        } catch (NullPointerException ex){
            ex.getStackTrace();
        }



    }



}