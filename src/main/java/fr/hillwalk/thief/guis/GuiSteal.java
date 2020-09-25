package fr.hillwalk.thief.guis;

import fr.hillwalk.thief.Thief;
import fr.hillwalk.thief.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiSteal {

    private final Inventory inv;
    private final Player player;

    public GuiSteal(Player player) {

        this.player = player;

        String word = Thief.instance.getConfig().getString("inventory.name");
        String replace = word.replaceAll("%target%", player.getName());


        inv = Bukkit.createInventory(null, Thief.instance.getConfig().getInt("inventory.size"), replace);


        initializeItems();
    }

    public void initializeItems() {
    }


    protected ItemStack createGuiItem( Material material, String name,  String... lore) {
         ItemStack item = new ItemStack(material, 1);
         ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(name);


        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }


    public void inventorySet(final HumanEntity ent) {


        Thief.instance.invStealed.put(ent.getUniqueId(), inv);


    }


}
