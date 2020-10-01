package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.configs.PlayersConfig;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.runnable.TaskForward;
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
import org.bukkit.scheduler.BukkitScheduler;
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




        if(e.getRightClicked() instanceof Player){

            //Instance
            final GuiSteal gui = new GuiSteal(((Player) e.getRightClicked()));
            final UtilsRef util = new UtilsRef();
            final TaskForward secondTask = new TaskForward(e.getPlayer());


            if(e.getHand() == EquipmentSlot.HAND && e.getPlayer().isSneaking()){

                //Final variables
                final BossBar bossBar = Bukkit.createBossBar(null, BarColor.RED, BarStyle.SEGMENTED_10, BarFlag.DARKEN_SKY);
                final Inventory invTarget = ((Player) e.getRightClicked()).getInventory();
                final Player player = e.getPlayer();

                //La personne est en train de voler
                Thief.instance.stealing.put(player.getUniqueId(), true);

                //On prend le joueur cette fois
                Thief.instance.takePlayer.put(((Player) e.getRightClicked()).getUniqueId(), e.getPlayer());
                Thief.instance.takePlayer.put(player.getUniqueId(), ((Player) e.getRightClicked()));


                //Contenu RUNNABLE
                this.task = new BukkitRunnable() {

                    int seconds = Thief.instance.getConfig().getInt("seconds") * 20;
                    int secondesMax = Thief.instance.getConfig().getInt("seconds") * 20;
                    int secondes = 1;
                    int itemsSlot = 0;
                    Location loc = e.getRightClicked().getLocation();
                    List<ItemStack> unique = new ArrayList<ItemStack>();
                    Random rand = new Random();


                    @Override
                    public void run() {

                        if (seconds == seconds) {
                            Thief.instance.taskId.put(player.getUniqueId(), getTaskId());
                            Thief.instance.bossBar.put(player.getUniqueId(), bossBar);
                        }

                        if(Thief.instance.getConfig().getBoolean("debugDistance")){
                            System.out.println(e.getPlayer().getLocation().distanceSquared(loc));
                        }

                        if(e.getPlayer().getLocation().distanceSquared(loc) > Thief.instance.getConfig().getInt("distance") || e.getRightClicked().getLocation().distanceSquared(e.getPlayer().getLocation()) > Thief.instance.getConfig().getInt("distance")) {

                            player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("bossbar.distance")));

                            util.resetAll(player);
                            bossBar.removePlayer(player);
                            this.cancel();
                        }

                        if(!player.isSneaking() && seconds >= 1) {

                            player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("bossbar.pressShift")));

                            util.resetAll(player);
                            bossBar.removePlayer(player);
                            this.cancel();

                        }

                        if ((seconds -= 1) == 0) {
                            this.cancel();
                            bossBar.removePlayer(player);
                            gui.inventorySet(player);


                            for(ItemStack item : invTarget.getContents()){

                                    if(item != null){
                                        if(!util.materialList().contains(item.getType())){

                                            if(!util.checkItemsLore(item)){


                                                    Thief.instance.list.add(item);

                                            }
                                        }

                                    } else {
                                        continue;
                                    }


                            }

                            for(int i = 0; i < Thief.instance.list.size(); i++){

                                if(itemsSlot <= Thief.instance.getConfig().getInt("inventory.size") - 1){



                                if(rand.nextInt(2) == 1){
                                    Thief.instance.invStealed.get(player.getUniqueId()).setItem(itemsSlot, new ItemStack(Material.AIR));
                                } else {

                                    ItemStack item = Thief.instance.list.get(rand.nextInt(Thief.instance.list.size()));
                                    if(!unique.contains(item)){

                                        unique.add(item);
                                        Thief.instance.invStealed.get(player.getUniqueId()).setItem(itemsSlot, item);

                                    } else {
                                        Thief.instance.invStealed.get(player.getUniqueId()).setItem(itemsSlot, new ItemStack(Material.AIR));
                                    }


                                }

                                itemsSlot++;

                                    if(itemsSlot == Thief.instance.getConfig().getInt("inventory.size") - 1){

                                        unique.clear();

                                    }

                                }
                            }

                            //On ouvre l'inventaire instancié au voleur
                            player.openInventory(Thief.instance.invStealed.get(player.getUniqueId()));

                            //La personne est dans l'inventaire
                            Thief.instance.stealing.put(player.getUniqueId(), false);

                            //Effacement de la liste contenant les matériaux.
                            Thief.instance.list.clear();

                            Thief.instance.taskId.remove(player.getUniqueId());
                            secondTask.runTaskTimer(Thief.instance, 0, 10);



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
                }.runTaskTimer(Thief.instance, 0, 1);


                bossBar.setVisible(true);
                bossBar.addPlayer(e.getPlayer());



            }
        }

    }


}
