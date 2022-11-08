package me.vhoyd.plugins.miningoverhaul.loops;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import me.vhoyd.plugins.miningoverhaul.Config;
import me.vhoyd.plugins.miningoverhaul.miningService.CachedOre;
import me.vhoyd.plugins.miningoverhaul.miningService.CircumstanceDrop;
import me.vhoyd.plugins.miningoverhaul.miningService.CustomOre;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayer;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayerManager;
import me.vhoyd.plugins.miningoverhaul.miningService.OreLootPackage;

/*
 * task meant to update all "cached" ores currently being mined
 */
public class BlockBreakTick extends BukkitRunnable {
	
	private ProtocolManager manager;//for breaking progress packets
	
	public BlockBreakTick(ProtocolManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public void run() {
		for (MiningPlayer player : MiningPlayerManager.getPlayers()) {
			CachedOre currentOre = player.getCurrentOre();
			if (currentOre != null) {
				//increase damage progress
				currentOre.setDamage(currentOre.getDamage() + player.getMiningSpeed()*Config.getMiningSpeedScale());
				
				//construct packet for visual progress feedback
				PacketContainer packetOut = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
				
				/*
				 * 1st index of packet is for break stage.
				 * getProgress returns 0-10, break stage values are 0-9
				 * since blocks start off with no animation, an extraneous value (I used -1) is needed
				 */
				packetOut.getIntegers().write(1, currentOre.getProgress()-1);
				
				//location for animation
				packetOut.getBlockPositionModifier().write(0, new BlockPosition(currentOre.getLocation().toVector()));
				
				try {
					manager.sendServerPacket(player.getPlayer(), packetOut);
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
				
				
				if (currentOre.isBroken() && currentOre.canDrop()) {
					//remove block
					currentOre.setCanDrop(false);
					
					currentOre.getLocation().getBlock().setType(currentOre.getOre().getBrokenMaterial());
					
					//play registered sound at broken block
					currentOre.getOre().getAction().run(currentOre, player);
					
					//create loot for ore drop 
					List<CircumstanceDrop> cds = currentOre.getOre().getDrops();
					for (CircumstanceDrop cd : cds) {
						if (cd.dropRequirementsMet(player)) {
							for (OreLootPackage olp : cd.getDrops()) {
								ItemStack lootDrop = olp.getItem();
								lootDrop.setAmount(64);
								int fortuneMultiplier = player.getMiningFortune()/100 + 1;
								
								//randomly decide if the extra fortune should apply as a +1 to the multiplier
								if (player.getMiningFortune()%100 > new Random().nextInt(100)) {
									fortuneMultiplier++;
								}
								if (Config.isIgnoringMiningFortune()) {
									fortuneMultiplier = 1;
								}
								/*
								 * drop all loot defined for ore
								 * if an ItemStack is dropped with above 64 amount, it will not render,
								 * so drops are split into stacks + extra
								 */
								int dropValue = olp.getQuantity()*fortuneMultiplier;
								for (int i = 0; i < dropValue/64; i++) {
									player.getPlayer().getWorld().dropItemNaturally(currentOre.getLocation(), lootDrop.clone());
								}
								
								//get leftover drops after all full stacks
								lootDrop.setAmount(dropValue%64);
								player.getPlayer().getWorld().dropItemNaturally(currentOre.getLocation(), lootDrop);
								
							}
							//drop experience orbs if ore is meant to 
							if (cd.getExpReward() > 0) {						
								ExperienceOrb orb = player.getPlayer().getWorld().spawn(currentOre.getLocation().clone().add(0.5,0.5,0.5), ExperienceOrb.class);
								orb.setExperience(cd.getExpReward());
							}
						}
					}
					CustomOre newOre = CustomOre.query(currentOre.getLocation().getBlock());
					if (newOre != null) {
						player.setCachedOre(new CachedOre(newOre, currentOre.getLocation()));
					}
				}
			}
		}
	}
	
}
