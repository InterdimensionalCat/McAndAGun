package com.benthom123.mcandguns.enchantment;

import com.benthom123.mcandguns.McAndGuns;
import com.benthom123.mcandguns.item.ItemGun;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.SwordItem;

public class FMJ extends Enchantment {

	public static final EnchantmentType GUNS = EnchantmentType.create("guns", (item)->(item instanceof ItemGun));
	public static final EnchantmentType LOOTMOD = EnchantmentType.create("lootmod", (item)->(item instanceof SwordItem || item instanceof ItemGun));
	
	
	public FMJ() {
		super(Enchantment.Rarity.RARE, FMJ.GUNS, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		name = "FMJ";
		setRegistryName(McAndGuns.MODID, name.toLowerCase());
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
		      return 1 + enchantmentLevel * 10;
		   }

		   public int getMaxEnchantability(int enchantmentLevel) {
		      return this.getMinEnchantability(enchantmentLevel) + 5;
		   }

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return this != ench && !(ench instanceof RapidFire);
	}

}
