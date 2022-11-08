package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.vhoyd.plugins.miningoverhaul.Config;

/**
 * Class for adding plugin-unique information about an ItemStack.
 */
public class MiningItem {
	private static List<MiningItem> allItems = new ArrayList<>();
	public static MiningItem HAND = new MiningItem(10,0,0, null);
	public static MiningItem TESTSTICK = new MiningItem(50,150,100, ItemManager.TEST_STICK);
	;
	private int speed;
	private int fortune;
	private int power;
	private ItemStack heldItem;
	private MiningItem(int speed, int fortune, int power, ItemStack item) {
		this.speed = speed;
		this.power = power;
		this.fortune = fortune;
		heldItem = item;
		allItems.add(this);
	}
	
	/**
	 * @return the mining speed of the item
	 */
	public int getMiningSpeed() {
		return speed;
	}
	/**
	 * @return the mining fortune of the item
	 */
	public int getMiningFortune() {
		return fortune;
	}
	/**
	 * @return the breaking power of the item
	 */
	public int getBreakingPower() {
		return power;
	}
	/**
	 * @return the ItemStack representing this mining item
	 */
	public ItemStack getItem() {
		return heldItem;
	}
	
	/**
	 * Checks for an existing MiningItem represented by the given ItemStack
	 * @param item the ItemStack to filter by
	 * @return the matching MiningItem, or the data for the empty hand if none is found
	 */
	public static MiningItem query(ItemStack item) {
		if (item == null) return HAND;
		for (MiningItem mitem: allItems) {
			if (Config.isRequiringExactStackSize()) {
				if (item.equals(mitem.getItem())) {
					return mitem;
				}
			} else {				
				if (item.isSimilar(mitem.getItem())) {
					return mitem;
				}
			}
		}
		return HAND;
	}
	
}
