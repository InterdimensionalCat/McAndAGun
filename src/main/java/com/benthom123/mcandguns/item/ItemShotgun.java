package com.benthom123.mcandguns.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemShotgun extends ItemGun {

	public ItemShotgun(Properties property) {
		super(property, "shotgun");
		rpm = 500;
		reloadTime = 40;
		clipSize = 4;
		baseRecoil = 25f;
		baseYawRecoil = 2.2f;
		damage = 7;
		knockback = 0.4f;
		inacc = 0.5f;
		range = 2;
		auto = true;
	}

	@Override
	void spawnProjectile(Entity entityIn, World worldIn, ItemStack stack) {
		for(int i = 0; i < 8; i++) {
			super.spawnProjectile(entityIn, worldIn, stack);
		}
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Shotgun");
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof ItemShell) {
            	itemstack1.shrink(1);
            	return true;
            }
         }

         return player.abilities.isCreativeMode;
	}
	
	@Override
    public int getItemEnchantability() {
        return 10;
    }
}
