package me.vhoyd.plugins.miningoverhaul.packets;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;

import me.vhoyd.plugins.miningoverhaul.Config;
import me.vhoyd.plugins.miningoverhaul.miningService.CachedOre;
import me.vhoyd.plugins.miningoverhaul.miningService.CustomOre;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayer;
import me.vhoyd.plugins.miningoverhaul.miningService.MiningPlayerManager;

//Class for handling players starting and stopping the mining of a block
public class BlockDigPacket extends PacketAdapter {
	
	private ProtocolManager manager;
	
	public BlockDigPacket(Plugin plugin, ProtocolManager manager) {
		super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG);
		this.manager = manager;
	}
	
	@Override
	public void onPacketReceiving(PacketEvent e) {
		if (Config.isOperating()) {	
			PacketContainer packet = e.getPacket();
			Player player = e.getPlayer();
			
			//get block involved in event
			StructureModifier<BlockPosition> position = packet.getBlockPositionModifier();
			Location location;
			int breakState;
			try {
				
				//get location of dug block
				location = position.read(0).toLocation(player.getWorld());
				
				breakState = packet.getIntegers().read(0);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
				return;
			}
			
			//grab block from pulled location
			Block block = location.getBlock();
			
			//grab loaded MiningPlayer from player breaking block
			MiningPlayer mp = MiningPlayerManager.getMiningPlayer(player);
			
			if (breakState == 0) {//player stopped mining block
				handleBlockRelease(mp);
			} else if (breakState > 0) {//player is mining block (ID goes up by 1 each time a new block breaking session starts)
				handleBlockBreak(mp, block);
			}
		}
	}
	
	private void handleBlockBreak(MiningPlayer player, Block block) {
		CustomOre ore = CustomOre.query(block);
		if (ore != null) {//block is custom ore
			if (ore.getBreakingPower() <= player.getBreakingPower() || Config.isIgnoringBreakingPower()) {
				CachedOre newOre = new CachedOre(CustomOre.query(block), block.getLocation());
				player.setCachedOre(newOre);
			}
		}
	}
	
	private void handleBlockRelease(MiningPlayer player) {
		CachedOre currentOre = player.getCurrentOre();
		if (currentOre != null) {//player stopped mining a custom ore, have to remove break animation
			PacketContainer packetOut = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
			packetOut.getIntegers().write(1, 10);//any number besides 0-9 for the animation stage removes it, so 10 works fine for this
			packetOut.getBlockPositionModifier().write(0, new BlockPosition(currentOre.getLocation().toVector()));
			try {
				manager.sendServerPacket(player.getPlayer(), packetOut);
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		player.setCachedOre(null);
		
	}
}
