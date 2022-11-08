package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.List;

/**
 * Abstract framework for any set of block drops meant to be provided when the block is broken.
 * This allows for a check to happen if the drop should only be rewarded under certain circumstances (hence the name).
 */
public abstract class CircumstanceDrop {
	private CustomOre sourceOre;
	private List<OreLootPackage> drops;
	private int exp;
	
	/**
	 * Creates a new CircumstanceDrop with respect to a CustomOre
	 * @param source the CustomOre this drop should be associated with
	 */
	public CircumstanceDrop(CustomOre source, int expReward, List<OreLootPackage> drops) {
		sourceOre = source;
		this.drops = drops;
		exp = expReward;
	}
	
	/**
	 * @return the list of drops from this CircumstanceDrop
	 */
	public List<OreLootPackage> getDrops() {
		return drops;
	}
	
	/**
	 * @return the CustomOre this drop is associated with
	 */
	public CustomOre getSourceOre() {
		return sourceOre;
	}
	
	/**
	 * @return the amount of exp this CircumstanceDrop should reward the player
	 */
	public int getExpReward() {
		return exp;
	}
	
	/**
	 * Checks whether a MiningPlayer meets the requirements for this CircumstanceDrop to trigger
	 * @param player the MiningPlayer that is attempting to get this CircumstanceDrop
	 * @return whether the MiningPlayer meets the requirement
	 */
	public abstract boolean dropRequirementsMet(MiningPlayer player);
	
}
