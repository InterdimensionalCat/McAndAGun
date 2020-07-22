package com.benthom123.mcandguns.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemVector extends ItemGun {

	public ItemVector(Properties property) {
		super(property, "vector");
		rpm = 1500;
		reloadTime = 45;
		clipSize = 25;
		baseRecoil = 4f;
		baseYawRecoil = 2.2f;
		damage = 7;
		knockback = 0.2f;
		inacc = 0.2f;
		range = 5;
		auto = true;
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof UziBullet) {
            	itemstack1.shrink(1);
            	return true;
            }
         }

         return player.abilities.isCreativeMode;
	}
	
	@Override
	void playFireSound(Entity entityIn, World worldIn) {
		if(!worldIn.isRemote()) {
			double x = entityIn.getPosX();
			double y = entityIn.getPosY();
			double z = entityIn.getPosZ();
			worldIn.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.4f, 1 - pitchMod / 2.0f + pitchRandom.nextFloat() * pitchMod / 2.0f);
		}
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Vector");
	}
	
	@Override
    public int getItemEnchantability() {
        return 17;
    }
}