package io.github.xnotro.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    /**
     * Create an item default one.
     * @param inventory Create an inventory.
     * @param material Set the material you want.
     * @param amount Set the amount of the material.
     * @param inventorySlot Set the inventory slot.
     * @param displayName Set the item name.
     * @param loreString Set the item lore.
     * @return The Item finished.
     */
    public static @NotNull ItemStack createItem(Inventory inventory, String material, int amount, int inventorySlot, String displayName, String @NotNull ... loreString) {
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack( Material.getMaterial( material ), amount );
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( Formatter.formatColor( displayName ) );
        for (String s : loreString) {
            lore.add( Formatter.formatColor(s) );
        }
        itemMeta.setLore( lore );
        item.setItemMeta( itemMeta );

        inventory.setItem( inventorySlot, item );

        return item;
    }

    /**
     * Create an item with custom damage.
     * @param inventory Create an inventory.
     * @param material Set the material you want.
     * @param amount Set the amount of the material.
     * @param damage Set the damage for the item.
     * @param inventorySlot Set the inventory slot.
     * @param displayName Set the item name.
     * @param loreString Set the item lore.
     * @return The Item finished.
     */
    public static @NotNull ItemStack createItem(Inventory inventory, String material, int amount, short damage, int inventorySlot, String displayName, String @NotNull ... loreString) {
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack( Material.getMaterial( material ), amount, damage);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName( Formatter.formatColor( displayName ) );
        for (String s : loreString) {
            lore.add( Formatter.formatColor(s) );
        }
        itemMeta.setLore( lore );
        item.setItemMeta( itemMeta );

        inventory.setItem( inventorySlot, item );

        return item;
    }

}
