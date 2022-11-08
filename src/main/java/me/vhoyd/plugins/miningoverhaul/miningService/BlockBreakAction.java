package me.vhoyd.plugins.miningoverhaul.miningService;

/**
 * Interface for an event meant to trigger when a block is broken
 */
public interface BlockBreakAction {
	public void run(CachedOre ore, MiningPlayer player);
}
