package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import fr.hillwalk.thief.guis.GuiSteal;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StealInteractionEvent implements Listener {

    private BukkitTask task;


    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPlayer(PlayerInteractEntityEvent e){

        //Si les conditions ne sont pas bonnes alors on retourne
        if(!Thief.instance.isThief.get(e.getPlayer().getUniqueId()))return;
        if(Thief.instance.stealing.get(e.getPlayer().getUniqueId()))return;

        //Instance
        final GuiSteal gui = new GuiSteal();
        final UtilsRef util = new UtilsRef();




        if(e.getRightClicked() instanceof Player){


            if(e.getHand() == EquipmentSlot.HAND && e.getPlayer().isSneaking()){

            //Final variables
            final BossBar bossBar = Bukkit.createBossBar(null, BarColor.RED, BarStyle.SEGMENTED_10, BarFlag.DARKEN_SKY);
            final Inventory invTarget = ((Player) e.getRightClicked()).getInventory();
            final Inventory inv = e.getPlayer().getInventory();
            final Player player = e.getPlayer();

            //La personne est en train de voler
            Thief.instance.stealing.put(player.getUniqueId(), true);


            //Contenu RUNNABLE
            this.task = new BukkitRunnable() {
                int seconds = Thief.instance.getConfig().getInt("seconds");
                int secondesMax = Thief.instance.getConfig().getInt("seconds");
                int secondes = 1;


                @Override
                public void run() {

                    if(!player.isSneaking()) {

                        player.sendMessage(Thief.prefix + util.getColor(Messages.getMessages().getString("bossbar.pressShift")));

                        //La personne s'arrête de voler
                        Thief.instance.stealing.put(player.getUniqueId(), false);

                        bossBar.removeAll();
                        this.cancel();
                    }

                    if ((seconds -= 1) == 0) {
                        task.cancel();
                        bossBar.removeAll();
                        gui.inventorySet(player);

                        for(ItemStack item : invTarget.getContents()){
                            for(String str : Thief.instance.getConfig().getStringList("material")){

                            if(item != null){
                                if(item.getType() == Material.valueOf(str.toUpperCase())){
                                    Thief.instance.list.add(item);
                                }


                            } else {
                                continue;
                            }

                          }
                        }

                        for(ItemStack item : Thief.instance.list){

                                Thief.instance.invStealed.get(player.getUniqueId()).addItem(item);

                        }


                        player.openInventory(Thief.instance.invStealed.get(player.getUniqueId()));
                        //La personne est dans l'inventaire
                        Thief.instance.stealing.put(player.getUniqueId(), false);

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



    @EventHandler(priority = EventPriority.NORMAL)
    public void inventoryClick(InventoryClickEvent e){


        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Thief.instance.getConfig().getString("inventory.name")))){




        }


    }






}
