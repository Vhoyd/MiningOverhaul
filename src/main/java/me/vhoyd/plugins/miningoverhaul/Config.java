package me.vhoyd.plugins.miningoverhaul;

/*
 * Class for changing parts of the plugin's function.
 * All booleans default to false.
 */
public class Config {

	private static boolean operating = false;
	private static boolean ignoreBreakingPower = false;
	private static boolean ignoreMiningFortune = false;
	private static boolean requireExactStackSize = false;
	private static int miningSpeedScale = 30;
	private static int oreToughnessScale = 60;
	
	/**
	 * @return whether the plugin is operating
	 */
	public static boolean isOperating() {
		return operating;
	}
	/**
	 * @param operating whether the plugin should operate
	 */
	public static void setOperating(boolean operating) {
		Config.operating = operating;
		if (operating) {
			Main.getPlugin().start();
		} else {
			Main.getPlugin().stop();
		}
	}
	/**
	 * @return whether the plugin is ignoring breaking power
	 */
	public static boolean isIgnoringBreakingPower() {
		return ignoreBreakingPower;
	}
	/**
	 * @param ignoreBreakingPower whether the plugin should ignore the breaking power feature
	 */
	public static void shouldIgnoreBreakingPower(boolean ignoreBreakingPower) {
		Config.ignoreBreakingPower = ignoreBreakingPower;
	}
	/**
	 * @return whether the plugin is requiring exact stack size matches of mining items
	 */
	public static boolean isRequiringExactStackSize() {
		return requireExactStackSize;
	}
	/**
	 * @param requireExactStackSize whether the plugin should require exact stack size matches of mining items
	 */
	public static void shouldRequireExactStackSize(boolean requireExactStackSize) {
		Config.requireExactStackSize = requireExactStackSize;
	}
	/**
	 * @return whether the plugin is ignoring mining fortune
	 */
	public static boolean isIgnoringMiningFortune() {
		return ignoreMiningFortune;
	}
	/**
	 * @param ignoreMiningFortune whether the plugin should ignore the mining fortune feature
	 */
	public static void shouldIgnoreMiningFortune(boolean ignoreMiningFortune) {
		Config.ignoreMiningFortune = ignoreMiningFortune;
	}
	/**
	 * @return the miningSpeedmining speed scaling
	 */
	public static int getMiningSpeedScale() {
		return miningSpeedScale;
	}
	/**
	 * @param miningSpeedScale the new mining speed scaling 
	 */
	public static void setMiningSpeedScale(int miningSpeedScale) {
		Config.miningSpeedScale = miningSpeedScale;
	}
	/**
	 * @return the toughness scaling of custom ores
	 */
	public static int getOreToughnessScale() {
		return oreToughnessScale;
	}
	/**
	 * @param oreToughnessScale the new toughness scaling of custom ores
	 */
	public static void setOreToughnessScale(int oreToughnessScale) {
		Config.oreToughnessScale = oreToughnessScale;
	}
	
	
	
}
