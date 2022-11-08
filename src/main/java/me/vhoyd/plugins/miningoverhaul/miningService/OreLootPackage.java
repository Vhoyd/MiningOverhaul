package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

/**
 * Light class for a single resource drop from a broken custom ore.
 * Designed so more than one can be dropped if a correct circumstance is met.
 */

public class OreLootPackage {
	private ItemStack item;
	private int quantity;
	
	/**
	 * Constructs a new info set on a drop
	 * @param item the ItemStack type to drop
	 * @param quantity the amount of the item to drop
	 */
	public OreLootPackage(ItemStack item, int quantity) {
		this.item = item;
		this.quantity = quantity;
		
	}
	
	/**
	 * @return the ItemStack intended to drop
	 */
	public ItemStack getItem() {
		return item;
	}
	
	/**
	 * @return the amount of the ItemStack that should drop
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Provides a list of a single drop for quick creation
	 * @param item the ItemStack type to drop
	 * @param quantity the amount of the item to drop
	 * @return
	 */
	public static List<OreLootPackage> single(ItemStack item, int quantity) {
		List<OreLootPackage> l = new ArrayList<>();
		l.add(new OreLootPackage(item, quantity));
		return l;
	}
	
}
