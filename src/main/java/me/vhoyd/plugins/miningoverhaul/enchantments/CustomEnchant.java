package me.vhoyd.plugins.miningoverhaul.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/*
 * unimplemented class for a silent enchantment, purely meant to give the enchantment glow
 * intended to be used when custom enchantments are supported
 * more fleshed-out comments will arrive when this is utilized
 */

public class CustomEnchant extends Enchantment {

	public CustomEnchant(NamespacedKey key) {
		super(key);
	}

	@Override
	public String getName() {
		return "Glow";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	//no specific item type, not meant to be manually applicable
	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	//should not be catchable or traded
	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	//no conflicting enchants, silent
	@Override
	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	//should not show in table
	@Override
	public boolean canEnchantItem(ItemStack item) {
		return false;
	}
	
}
