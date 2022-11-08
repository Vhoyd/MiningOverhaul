package me.vhoyd.plugins.miningoverhaul.eventHandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.vhoyd.plugins.miningoverhaul.Config;
import me.vhoyd.plugins.miningoverhaul.miningService.ItemManager;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningItem;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayer;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayerManager;

/**
 * Class for handling all necessary game events for the plugin.
 */
public class MiningHandler implements Listener {

	/**
	 * Ensures a MiningPlayer object exists for any joining Player entity, and applies two potion effects:
	 * Mining fatigue, so player clients do not prematurely break blocks;
	 * haste, so players do not see a jarringly slow arm-swing animation.
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (Config.isOperating()) {
			
			//apply mining fatigue so clients cannot break the blocks themselves
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 5, true, false, false));
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, true, false, false));
			
			//create new MiningPlayer object to handle unregistered players
			if (MiningPlayerManager.getMiningPlayer(e.getPlayer()) == null) {
				MiningPlayerManager.registerPlayer(e.getPlayer());
				
				//testing only
				e.getPlayer().getInventory().addItem(ItemManager.TEST_STICK);
			}
		}
	}
	
	//update mining stats to reflect new item when switching items
	/**
	 * Updates tracked breaking tool for the Player's MiningPlayer representation to ensure proper block breaking behavior.
	 */
	@EventHandler
	public void onPlayerSwitchItem(PlayerItemHeldEvent e) {
		if (Config.isOperating()) {			
			ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());		
			MiningItem mitem = MiningItem.query(item);//defaults to hand stats if no custom item is found
			MiningPlayer mp = MiningPlayerManager.getMiningPlayer(e.getPlayer());
			mp.setMiningItem(mitem);
		}
	}
}
