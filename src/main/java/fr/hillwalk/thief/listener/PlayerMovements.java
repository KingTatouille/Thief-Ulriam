package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMovements implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void checkMovements(PlayerMoveEvent e){

        if(e.getPlayer() instanceof Player){

            UtilsRef util = new UtilsRef();
            GuiSteal gui = new GuiSteal(e.getPlayer());

            if(e.getPlayer().getName().equalsIgnoreCase(Thief.instance.target.get(Thief.instance.targetId.get(e.getPlayer().getUniqueId())))){


                Location loc = Thief.instance.takePlayer.get(e.getPlayer().getUniqueId()).getLocation();
                if (e.getPlayer().getLocation().distance(loc) >= Thief.instance.getConfig().getInt("distance")) {


                    for(Player target : Bukkit.getServer().getOnlinePlayers()){


                        //On prend le nom du voleur
                        if(target.getName().equalsIgnoreCase(Thief.instance.target.get(e.getPlayer().getUniqueId()))){

                            if(Thief.instance.taskId.get(target.getUniqueId()) != null){

                                //On arrête la task et on enlève la bossbar
                                Bukkit.getScheduler().cancelTask(Thief.instance.taskId.get(target.getUniqueId()));
                                Thief.instance.bossBar.get(target.getUniqueId()).removePlayer(target);


                                //On dégage tout ce qui a été fait.
                                Thief.instance.isThief.put(target.getUniqueId(), true);
                                Thief.instance.stealing.put(target.getUniqueId(), false);
                                Thief.instance.target.remove(target.getUniqueId());
                                Thief.instance.bossBar.remove(target.getUniqueId());
                                Thief.instance.taskId.remove(target.getUniqueId());

                                //ON remet à jour les hashmaps
                                Thief.instance.target.remove(e.getPlayer().getUniqueId());
                                Thief.instance.targetId.remove(e.getPlayer());
                                Thief.instance.takePlayer.remove(e.getPlayer());


                            }

                            //On ferme l'inventaire
                            target.closeInventory();

                            //On reset l'inventaire ayant eu une intéraction
                            gui.inventoryReset(target);

                            return;

                        }

                    }
                    gui.inventoryReset(e.getPlayer());
                }


        } else {
            return;
        }

    }
    }


}


/*




 */
