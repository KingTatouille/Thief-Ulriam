package fr.hillwalk.thief.listener;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.JoinPlayers;
import fr.hillwalk.thief.utils.UtilsRef;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StealInteractionEvent implements Listener {

    private BukkitTask task;


    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPlayer(PlayerInteractEntityEvent e){

        JoinPlayers takeConfig = new JoinPlayers();
        UtilsRef util = new UtilsRef();

        if(!takeConfig.getPlayer(e.getPlayer()).getBoolean("isThief"))return;

        if(e.getRightClicked() instanceof Player){


            if(e.getHand() == EquipmentSlot.HAND && e.getPlayer().isSneaking()){
            final BossBar bossBar = Bukkit.createBossBar("Vole en cours ...", BarColor.RED, BarStyle.SEGMENTED_10);
            final Inventory invTarget = ((Player) e.getRightClicked()).getInventory();
            final Inventory inv = e.getPlayer().getInventory();
            final Player player = e.getPlayer();

            this.task = new BukkitRunnable() {
                int seconds = 5;
                List<ItemStack> list = new ArrayList<ItemStack>();
                Random random = new Random();



                @Override
                public void run() {
                    if ((seconds -= 1) == 0) {
                        task.cancel();
                        bossBar.removeAll();

                        for(ItemStack item : invTarget.getContents()){

                            if(item != null){
                                list.add(item);
                            } else {
                                continue;
                            }

                        }
                        int rand = random.nextInt(list.size());
                        ItemStack newItem = list.get(rand);

                        invTarget.removeItem(newItem);
                        inv.addItem(newItem);




                    } else {
                        bossBar.setProgress(seconds / 5D);
                    }
                }
            }.runTaskTimer(Thief.instance, 0, 20);

            bossBar.setVisible(true);
            bossBar.addPlayer(e.getPlayer());





        }
        }
    }








}
