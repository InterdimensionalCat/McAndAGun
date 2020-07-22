package com.benthom123.mcandguns.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemTommy extends ItemGun {

	public ItemTommy(Properties property) {
		super(property, "tommy");
		rpm = 750;
		reloadTime = 70;
		clipSize = 100;
		baseRecoil = 3f;
		baseYawRecoil = 1.2f;
		damage = 4;
		knockback = 0.2f;
		inacc = 0.1f;
		range = 4;
		auto = true;
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof TommyBullet) {
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
		return new TranslationTextComponent("Thomson");
	}
	
	@Override
    public int getItemEnchantability() {
        return 12;
    }
}