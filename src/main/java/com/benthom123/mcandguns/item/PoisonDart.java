package com.benthom123.mcandguns.item;

import com.benthom123.mcandguns.McAndGuns;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PoisonDart extends Item {

	//general variables
	private String name = "poisondart";
	
	public PoisonDart(Properties property) {
		super(property);
		setRegistryName(McAndGuns.MODID, name);
	}

	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Toxic Dart");
	}
}
