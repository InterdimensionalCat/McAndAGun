package com.benthom123.mcandguns.enchantment;

import com.benthom123.mcandguns.McAndGuns;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ExtendedMag extends Enchantment {

	
	
	
	public ExtendedMag() {
		super(Enchantment.Rarity.UNCOMMON, FMJ.GUNS, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		name = "Extended Mag";
		setRegistryName(McAndGuns.MODID, "extmag");
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
		      return 1 + enchantmentLevel * 10;
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
		return this != ench && !(ench instanceof FastMag);
	}

}
