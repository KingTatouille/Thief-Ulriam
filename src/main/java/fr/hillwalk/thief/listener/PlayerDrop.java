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


                //On prend le nom du voleur
            for(Player target : Bukkit.getServer().getOnlinePlayers()){


                //On prend le nom du voleur
                if(target.getName().equalsIgnoreCase(Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()).getName())){


                    for(int i = 0; i < Thief.instance.getConfig().getInt("inventory.size"); i++){


                        //Si les deux items se correspondent alors on effectue les actions.
                        if(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i) != null){

                            if(e.getItemDrop().getItemStack().getType() == Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType()){

                                if(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() == 1){
                                    Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Material.AIR));
                                    target.updateInventory();
                                }

                                System.out.println("ça passe");
                                Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType(), Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() - e.getItemDrop().getItemStack().getAmount()));
                                target.updateInventory();
                            }

                    } else {
                            continue;
                        }
                    }


                }


            }




        } catch (NullPointerException ex){
            ex.getStackTrace();
        }



    }



}