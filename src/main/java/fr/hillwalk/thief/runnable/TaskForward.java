package fr.hillwalk.thief.runnable;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskForward extends BukkitRunnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

   private Player player;

   public TaskForward(Player player){

       this.player = player;

   }

    @Override
    public void run() {

       try{
        if(Thief.instance.takePlayer.get(player.getUniqueId()) == null){
            this.cancel();
        }

        UtilsRef util = new UtilsRef();
        GuiSteal gui = new GuiSteal(player);


        if (Thief.instance.taskId.get(player.getUniqueId()) == null) {
            Thief.instance.taskId.put(player.getUniqueId(), getTaskId());
        }


        if(Thief.instance.takePlayer.get(player.getUniqueId()).getLocation().distanceSquared(player.getLocation()) > Thief.instance.getConfig().getInt("distance")){


                //On prend le nom du voleur
                if(player.getName().equalsIgnoreCase(player.getName())){

                    if(Thief.instance.taskId.get(player.getUniqueId()) != null){

                        //On arrête la task et on enlève la bossbar
                        Thief.instance.bossBar.get(player.getUniqueId()).removePlayer(player);

                    }

                    player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("bossbar.targetDistance")));

                    //On ferme l'inventaire
                    player.closeInventory();


                    this.cancel();

                }


            util.resetAll(player);
            this.cancel();

        } else {
            return;
        }

       }catch (NullPointerException ex){
           ex.getStackTrace();
       }
    }
}
