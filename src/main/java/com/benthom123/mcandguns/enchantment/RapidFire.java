package com.benthom123.mcandguns.enchantment;

import com.benthom123.mcandguns.McAndGuns;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class RapidFire extends Enchantment {

	
	
	
	public RapidFire() {
		super(Enchantment.Rarity.VERY_RARE, FMJ.GUNS, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		name = "Rapid Fire";
		setRegistryName(McAndGuns.MODID, "rapidfire");
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
		return 5;
	}
	
	   public int getMinEnchantability(int enchantmentLevel) {
		      return 5 + enchantmentLevel * 10;
		   }

		   public int getMaxEnchantability(int enchantmentLevel) {
		      return this.getMinEnchantability(enchantmentLevel) + 3;
		   }

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return this != ench && !(ench instanceof FMJ);
	}

}
