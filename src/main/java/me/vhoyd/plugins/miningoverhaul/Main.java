package me.vhoyd.plugins.miningoverhaul;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.vhoyd.plugins.miningoverhaul.eventHandlers.MiningHandler;
import me.vhoyd.plugins.miningoverhaul.loops.BlockBreakTick;
import me.vhoyd.plugins.miningoverhaul.miningService.BlockBreakAction;
import me.vhoyd.plugins.miningoverhaul.miningService.BlockBreakSound;
import me.vhoyd.plugins.miningoverhaul.miningService.CachedOre;
import me.vhoyd.plugins.miningoverhaul.miningService.CircumstanceDrop;
import me.vhoyd.plugins.miningoverhaul.miningService.CustomOre;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayer;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayerManager;
import me.vhoyd.plugins.miningoverhaul.miningService.OreLootPackage;
import me.vhoyd.plugins.miningoverhaul.packets.BlockDigPacket;
import net.md_5.bungee.api.ChatColor;


//next: change QuickSound to be a general task execution using Runnables


public class Main extends JavaPlugin{
	private static Main plugin;
	private BlockBreakTick breakTick;
	private ProtocolManager manager;
	private MiningHandler handler = new MiningHandler();
	private BlockDigPacket packetHandler;
	private boolean handlerRegistered = false;
	
	
	@Override
	public void onEnable() {
		plugin = this;
//		registerEnchant(new CustomEnchant(NamespacedKey.minecraft("glow")));//unused currently
		
		//get packet manager for break stage handling
		manager = ProtocolLibrary.getProtocolManager();		
		
		//ask manager to listen for block digging events
		packetHandler = new BlockDigPacket(this, manager);
		manager.addPacketListener(packetHandler);

		breakTick = new BlockBreakTick(manager);

		CustomOre bedrock = new CustomOre("Bedrock", Material.BEDROCK, Material.GRASS_BLOCK, 0, 250, new BlockBreakSound(Sound.BLOCK_STONE_BREAK, 1f, 0.8f));
		bedrock.addDrop(new CircumstanceDrop(bedrock, 5, OreLootPackage.single(new ItemStack(Material.BEDROCK), 2)) {
			@Override
			public boolean dropRequirementsMet(MiningPlayer player) {
				return true;
			}
		});
		
		CustomOre grass = new CustomOre("Grass", Material.GRASS_BLOCK, Material.AIR, 0, 400, new BlockBreakSound(Sound.BLOCK_GRASS_BREAK, 1f, 0.8f));
		grass.addDrop(new CircumstanceDrop(grass, 0, OreLootPackage.single(new ItemStack(Material.DIRT), 1)) {
			@Override
			public boolean dropRequirementsMet(MiningPlayer player) {
				return true;
			}
		});
		final Random r = new Random();
		grass.addDrop(new CircumstanceDrop(grass, 1, OreLootPackage.single(new ItemStack(Material.MOSS_BLOCK), 1)) {
			@Override
			public boolean dropRequirementsMet(MiningPlayer player) {
				if (r.nextInt()%2 == 1) {
					return true;					
				}
				return false;
			}
		});
		BlockBreakAction action = new BlockBreakAction() {

			@Override
			public void run(CachedOre ore, MiningPlayer player) {
			BlockBreakSound s = new BlockBreakSound(Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 1.2f);
			s.run(ore, player);
			ore.getLocation().getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, ore.getLocation().add(0.5, 0.5, 0.5), 25);
			if (r.nextInt()%3 == 0) {
				ore.getLocation().getBlock().setType(Material.STONE);
			}
			}
			
			
		};
		CustomOre barrier = new CustomOre("Barrier", Material.BARRIER, Material.BEDROCK, 0, 1000, action);
		barrier.addDrop(new CircumstanceDrop(barrier, 15, OreLootPackage.single(new ItemStack(Material.AMETHYST_SHARD), 1)) {
			@Override
			public boolean dropRequirementsMet(MiningPlayer player) {
				return true;
			}
		});
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Mining plugin is running!");
		Config.setOperating(true);
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	 void start() {//called from Config
		if (!handlerRegistered) {
			getServer().getPluginManager().registerEvents(handler, this);			
			handlerRegistered = true;
		}
		
		//construct MiningPlayer objects out of any unregistered players
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (MiningPlayerManager.getMiningPlayer(player) == null) {
				MiningPlayerManager.registerPlayer(player);				
			}
		}
		
		//schedule block break progress on each tick
		breakTick.runTaskTimer(this, 0, 0);
		
	}
	 
	void stop() {
		
		//remove enforced mining fatigue
		for (MiningPlayer player : MiningPlayerManager.getPlayers()) {
			player.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
		}
		
		//stop currently-useless task
		breakTick.cancel();
	}
	
	
	public static void registerEnchant(Enchantment enchant) {	
		try {
			//force enchantment list to accept new registers
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			
			//reigster enchantment
			Enchantment.registerEnchantment(enchant);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}