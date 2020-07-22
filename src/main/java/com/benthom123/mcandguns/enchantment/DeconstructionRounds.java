package com.benthom123.mcandguns.enchantment;

import com.benthom123.mcandguns.McAndGuns;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class DeconstructionRounds extends LootBonusEnchantment {

	public DeconstructionRounds() {
		super(Enchantment.Rarity.RARE, FMJ.GUNS, EquipmentSlotType.MAINHAND);
		name = "Deconstruction Rounds";
		setRegistryName(McAndGuns.MODID, "dround");
	}

}
