package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InteractionPlayer implements Listener {

    private BukkitTask task;



    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPlayer(PlayerInteractEntityEvent e){

        //Si les conditions ne sont pas bonnes alors on retourne
        if(!Thief.instance.isThief.get(e.getPlayer().getUniqueId()))return;
        if(Thief.instance.stealing.get(e.getPlayer().getUniqueId()))return;

        //Instance
        final GuiSteal gui = new GuiSteal(((Player) e.getRightClicked()));
        final UtilsRef util = new UtilsRef();




        if(e.getRightClicked() instanceof Player){


            if(e.getHand() == EquipmentSlot.HAND && e.getPlayer().isSneaking()){

                //Final variables
                final BossBar bossBar = Bukkit.createBossBar(null, BarColor.RED, BarStyle.SEGMENTED_10, BarFlag.DARKEN_SKY);
                final Inventory invTarget = ((Player) e.getRightClicked()).getInventory();
                final Player player = e.getPlayer();

                Thief.instance.target.put(player.getUniqueId(), ((Player) e.getRightClicked()).getName());
                Thief.instance.target.put(((Player) e.getRightClicked()).getUniqueId(), e.getPlayer().getName());

                //La personne est en train de voler
                Thief.instance.stealing.put(player.getUniqueId(), true);

                //On obtient l'unique id du joueur qui se fait actuellement voler et son contraire
                Thief.instance.targetId.put(((Player) e.getRightClicked()).getUniqueId(), player.getUniqueId());

                //On prend le joueur cette fois
                Thief.instance.takePlayer.put(((Player) e.getRightClicked()).getUniqueId(), e.getPlayer());


                //Contenu RUNNABLE
                this.task = new BukkitRunnable() {

                    int seconds = Thief.instance.getConfig().getInt("seconds");
                    int secondesMax = Thief.instance.getConfig().getInt("seconds");
                    int secondes = 1;
                    int itemsSlot = 0;
                    Random rand = new Random();


                    @Override
                    public void run() {

                        if (seconds == seconds) {
                            Thief.instance.taskId.put(player.getUniqueId(), getTaskId());
                            Thief.instance.bossBar.put(player.getUniqueId(), bossBar);
                        }

                        if(!player.isSneaking()) {

                            player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("bossbar.pressShift")));

                            //La personne s'arrête de voler
                            Thief.instance.stealing.put(player.getUniqueId(), false);

                            bossBar.removePlayer(player);
                            this.cancel();
                        }

                        if ((seconds -= 1) == 0) {
                            task.cancel();
                            bossBar.removePlayer(player);
                            gui.inventorySet(player);




                            for(ItemStack item : invTarget.getContents()){

                                    if(item != null){
                                        System.out.println("Names : " + util.checkItemsNames(item));
                                        System.out.println("Lore : " + util.checkItemsLore(item));
                                        if(!util.materialList().contains(item.getType())){

                                            if(!util.checkItemsNames(item)){

                                                Thief.instance.list.add(item);

                                            } else if(!util.checkItemsLore(item)){

                                                Thief.instance.list.add(item);

                                            }
                                        }

                                    } else {
                                        continue;
                                    }


                            }

                            for(ItemStack item : Thief.instance.list){

                                if(rand.nextInt(2) == 1){
                                    item.setType(Material.AIR);
                                } else {

                                }

                                Thief.instance.invStealed.get(player.getUniqueId()).setItem(itemsSlot, item);
                                itemsSlot++;

                            }


                            //On ouvre l'inventaire instancié au voleur
                            player.openInventory(Thief.instance.invStealed.get(player.getUniqueId()));

                            //La personne est dans l'inventaire
                            Thief.instance.stealing.put(player.getUniqueId(), false);

                            //Effacement de la liste contenant les matériaux.
                            Thief.instance.list.clear();


                        } else {
                            try{
                                Double result = (Double.valueOf(secondes) / Double.valueOf(secondesMax)) * 100D;

                                String word = Messages.getMessages().getString("bossbar.name");
                                String replace = word.replaceAll("%number%", String.format("%.0f%%",result));

                                bossBar.setTitle(replace);
                                bossBar.setProgress(result / 100);
                                secondes++;
                            } catch (IllegalArgumentException e){
                                e.getStackTrace();
                            }
                        }
                    }
                }.runTaskTimer(Thief.instance, 0, 20);


                bossBar.setVisible(true);
                bossBar.addPlayer(e.getPlayer());



            }
        }

    }


}
