package me.vhoyd.plugins.miningoverhaul.miningService;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Represents a custom-created ore to replace the behavior of an existing block type
 */
public class CustomOre {
	
	private static List<CustomOre> registeredOres = new ArrayList<>();
	
	private String name;
	private Material material;
	private List<CircumstanceDrop> possibleDrops = new ArrayList<>();
	private int power;
	private int strength;
	private Material brokenMaterial;
	private BlockBreakAction action;
	
	/**
	 * Creates a new CustomOre object.
	 * This acts like a behavior change towards a block with the matching material.
	 * @param name the name of this ore
	 * @param physicalBlock the Material this ore will have when physically placed
	 * @param minBreakPower the minimum breaking power required to break this ore
	 * @param hardness how hard it is to break this ore
	 * @param exp the exp reward for breaking this ore
	 * @param breakSound the sound data for this ore when broken
	 */
	public CustomOre(String name, Material physicalBlock, Material brokenMaterial, int minBreakPower, int hardness, BlockBreakAction breakAction) {
		this.name = name;
		material = physicalBlock;
		this.brokenMaterial = brokenMaterial;
		power = minBreakPower;
		strength = hardness;
		action = breakAction;
		registeredOres.add(this);
	}
	
	/**
	 * @return the phyiscal material of the ore
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * @param drop the data containing a loot set for when an ore breaks
	 */
	public void addDrop(CircumstanceDrop drop) {
		possibleDrops.add(drop);
	}
	
	/**
	 * @return all registered CircumstanceDrops for this CustomOre
	 */
	public List<CircumstanceDrop> getDrops() {
		return possibleDrops;
	}
	
	/**
	 * @return the breaking power required to break the block
	 */
	public int getBreakingPower() {
		return power;
	}
	/**
	 * @return the block strength
	 */
	public int getStrength() {
		return strength;
	}
	
	/**
	 * @return the String name label of this CustomOre
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the BlockBreakAction that should run when this ore is broken
	 */
	public BlockBreakAction getAction() {
		return action;
	}
	
	/**
	 * @return the material that should replace this ore when broken
	 */
	public Material getBrokenMaterial() {
		return brokenMaterial;
	}
	
	/**
	 * Searches for a CustomOre existing at the provided Block
	 * @param block the Block where this ore should be located
	 * @return the matching CustomOre, or null if none is found
	 */
	public static CustomOre query(Block block) {
		for (CustomOre ore : registeredOres) {
			if (ore.material.equals(block.getType())) {
				return ore;
			}
		}
		return null;
	}
	
	/**
	 * @return all created CustomOre objects
	 */
	public static List<CustomOre> getRegisteredOres() {
		return registeredOres;
	}
	
	/**
	 * Filters for a CustomOre by name
	 * @param name the name of the CustomOre desired
	 * @return the matching CustomOre, or null if none is found
	 */
	public static CustomOre getByName(String name) {
		for (CustomOre ore : registeredOres) {
			if (ore.name.equals(name)) {
				return ore;
			}
		}
		return null;
	}
}
