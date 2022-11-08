package me.vhoyd.plugins.miningoverhaul.miningService;

import org.bukkit.Sound;

/**
 * Subclass of BlockBreakAction both meant as an example of how to use the interface and 
 * also as a convenience class for just playing a sound.
 */
public class BlockBreakSound implements BlockBreakAction {
	private Sound sound;
	private float volume;
	private float pitch;
	
	/**
	 * Creates a sound data package with the provided info
	 * @param sound the Sound to play
	 * @param volume the volume to play at
	 * @param pitch the pitch to raise to
	 */
	public BlockBreakSound(Sound sound, float volume, float pitch) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public float getPitch() {
		return pitch;
	}
	/**
	 * Plays the sound at the location of the given CachedOre 
	 */
	public void run(CachedOre ore, MiningPlayer player) {
		ore.getLocation().getWorld().playSound(ore.getLocation(), sound, volume, pitch);
	}
	
}
