package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
	public static final ItemStack TEST_STICK;
	
	static {
		ItemStack stick = new ItemStack(Material.STICK);
		stick.setAmount(1);
		ItemMeta meta = stick.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("§d§oFor testing purposes only");
		meta.setLore(lore);
		meta.setDisplayName("§6§lGod Stick");
		stick.setItemMeta(meta);
		TEST_STICK = stick;
	}
}
