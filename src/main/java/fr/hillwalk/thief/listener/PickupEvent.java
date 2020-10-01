package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupEvent implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void playerPickup(PlayerPickupItemEvent e){


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
                        if(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i) != null){
                            if(e.getItem().getItemStack().getType() == Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType()){

                                if(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() <= 1){
                                    Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(e.getItem().getItemStack().getType(), 1));
                                    return;
                                }

                                Thief.instance.invStealed.get(target.getUniqueId()).setItem(i, new ItemStack(Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getType(), Thief.instance.invStealed.get(target.getUniqueId()).getItem(i).getAmount() + e.getItem().getItemStack().getAmount()));
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
