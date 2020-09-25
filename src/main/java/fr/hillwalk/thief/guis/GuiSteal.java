package fr.hillwalk.thief.guis;

import fr.hillwalk.thief.Thief;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiSteal {

    private final Inventory inv;

    public GuiSteal() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Inventaire");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem( Material material, String name,  String... lore) {
         ItemStack item = new ItemStack(material, 1);
         ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void inventorySet(final HumanEntity ent) {


        Thief.instance.invStealed.put(ent.getUniqueId(), inv);


    }


}
