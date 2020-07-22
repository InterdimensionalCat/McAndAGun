package com.benthom123.mcandguns.enchantment;

import com.benthom123.mcandguns.McAndGuns;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class FastMag extends Enchantment {

	
	
	
	public FastMag() {
		super(Enchantment.Rarity.COMMON, FMJ.GUNS, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		name = "Fast Mag";
		setRegistryName(McAndGuns.MODID, "fastmag");
	}

	/**
	 * Returns the minimum level that the enchantment can have.
	 */
	@Override
	public int getMinLevel() {
		return 1;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 2;
	}
	
	   public int getMinEnchantability(int enchantmentLevel) {
		      return 1 + (enchantmentLevel - 1) * 20;
		   }

		   public int getMaxEnchantability(int enchantmentLevel) {
		      return this.getMinEnchantability(enchantmentLevel) + 10;
		   }

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return this != ench && !(ench instanceof ExtendedMag);
	}

}
