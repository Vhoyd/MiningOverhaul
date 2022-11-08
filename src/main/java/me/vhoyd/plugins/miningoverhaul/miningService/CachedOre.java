package me.vhoyd.plugins.miningoverhaul.miningService;

import org.bukkit.Location;

import me.vhoyd.plugins.miningoverhaul.Config;
import me.vhoyd.plugins.miningoverhaul.loops.BlockBreakTick;

/**
 * Class for handling interaction between a CustomOre's data and actual gameplay.
 * Think of it as CustomOres being classes while CachedOres are objects of those classes.
 */
public class CachedOre {
	private CustomOre ore;
	private int damage;
	private Location location;
	private boolean canDrop = true;
	
	/**
	 * Creates a new instance at a given location
	 * @param ore the CustomOre this block is supposed to represent
	 * @param location the location of the block being broken
	 */
	public CachedOre(CustomOre ore, Location location) {
		this.ore = ore;
		this.location = location;
	}
	
	/**
	 * @return the CustomOre this ore is behaving as
	 */
	public CustomOre getOre() {
		return ore;
	}

	/**
	 * @return the current flat damage progress on this ore
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @param damage the damage this ore has received
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	

	/**
	 * @return the location of the ore
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * @return whether the total damage sustained exceeds the required damage to break the ore
	 */
	public boolean isBroken() {
		return (damage >= ore.getStrength()*Config.getOreToughnessScale());
	}
	/**
	 * @param canBeBroken whether this ore should drop anything when broken
	 */
	public void setCanDrop(boolean canDrop) {
		this.canDrop = canDrop;
	}
	/**
	 * @return whether this ore should drop anything when broken
	 */
	public boolean canDrop() {
		return canDrop;
	}
	/**
	 * Yields a range 0-11 for the current breaking stage of the ore. <br></br>
	 * Break stage values are 0-9.
	 * Since blocks start off with no animation, an extraneous value is needed.
	 * See {@link BlockBreakTick#run()} for how this is used
	 * @return the progress stage of the ore
	 */
	public int getProgress() {
		return (int)(11*(((float)damage)/((float)ore.getStrength()*Config.getOreToughnessScale())))-1;
	}
}
