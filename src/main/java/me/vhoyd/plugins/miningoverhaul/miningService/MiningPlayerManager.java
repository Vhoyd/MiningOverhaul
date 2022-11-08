package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class MiningPlayerManager {
	private static List<MiningPlayer> players = new ArrayList<>();
	
	public static List<MiningPlayer> getPlayers() {
		return players;
	}
	
	public static MiningPlayer getMiningPlayer(Player player) {
		for (MiningPlayer m : players) {
			if (m.getPlayer().getName().equals(player.getName())) {
				return m;
			}
		}
		return null;
	}
	
	public static void registerPlayer(Player player) {
		MiningPlayer newPlayer = new MiningPlayer(player, 0, 0);
		players.add(newPlayer);
	}

}
