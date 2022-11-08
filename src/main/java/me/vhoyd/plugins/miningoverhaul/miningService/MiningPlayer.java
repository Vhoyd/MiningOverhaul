package me.vhoyd.plugins.miningoverhaul.miningService;

import org.bukkit.entity.Player;

/**
 * Class for tracking extra plugin data about any Player object.
 */
public class MiningPlayer {
	private Player player;
	private int miningSpeed;
	private int miningFortune;
	private CachedOre ore;
	private MiningItem mitem = MiningItem.HAND;
	
	/**
	 * Constructs a MiningPlayer from given data
	 * @param player the Player object tied to this MiningPlayer
	 * @param miningSpeed the persistent mining speed stat this player should have
	 * @param miningFortune the persistent mining fortune stat this player should have
	 */
	public MiningPlayer(Player player, int miningSpeed, int miningFortune) {
		this.player = player;
		this.miningSpeed = miningSpeed;
		this.miningFortune = miningFortune;
	}
	
	/**
	 * @return the Player object this MiningPlayer is tied to
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return the total mining speed potential of this MiningPlayer
	 */
	public int getMiningSpeed() {
		return miningSpeed+mitem.getMiningSpeed();
	}
	
	/**
	 * @return the total mining fortune potential of this MiningPlayer
	 */
	public int getMiningFortune() {
		return miningFortune+mitem.getMiningFortune();
	}
	
	/**
	 * @return the total breaking power potential of this MiningPlayer
	 */
	public int getBreakingPower() {
		return mitem.getBreakingPower();
	}
	
	/**
	 * @return the CachedOre this player is currently mining
	 */
	public CachedOre getCurrentOre() {
		return ore;
	}
	
	/**
	 * Reassigns the CachedOre the player is mining
	 * @param ore the CachedOre this player should be mining
	 */
	public void setCachedOre(CachedOre ore) {
		this.ore = ore;
	}
	
	/**
	 * @return the MiningItem represented by the item in the player's hand
	 */
	public MiningItem getMiningItem() {
		return mitem;
	}
	
	/**
	 * @param item changes the MiningItem used in current mining calculations
	 */
	public void setMiningItem(MiningItem item) {
		mitem = item;
	}
}
