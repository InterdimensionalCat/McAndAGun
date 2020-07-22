package com.benthom123.mcandguns.item;

import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.entity.EntityRay;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemRayGun extends ItemGun {

	public ItemRayGun(Properties property) {
		super(property, "raygun");
		rpm = 180;
		reloadTime = 60;
		clipSize = 20;
		baseRecoil = 8f;
		baseYawRecoil = 2.1f;
		damage = 21;
		knockback = 0.0f;
		inacc = 0.0f;
		range = 100;
		auto = true;
		speed = 3.0f;
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		return true;
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Ray Gun");
	}
	
	@Override
	void spawnProjectile(Entity entityIn, World worldIn, ItemStack stack) {
        if (!worldIn.isRemote())
        {
        	float damageIn = damage;
        	float inaccIn = inacc;
        	int rangeIn = range;
        	
        	Integer enchant = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.FMJ);
        	if(enchant != null) {
        		damageIn += damage * (0.2f * enchant.floatValue());
        	}
        	
        	Integer enchant2 = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.LongBarrel);
        	if(enchant2 != null) {
        		inaccIn *= (1.0f - 0.1f * enchant2);
        		rangeIn = (int)Math.ceil(range * (1.0D + 0.1D * enchant2));
        	}
        	
        	EntityRay shot = new EntityRay(worldIn, (LivingEntity)entityIn, speed, inaccIn, damageIn, knockback, rangeIn);
        	worldIn.addEntity(shot);
        }
	}
	
	@Override
	void playFireSound(Entity entityIn, World worldIn) {
		if(!worldIn.isRemote()) {
			double x = entityIn.getPosX();
			double y = entityIn.getPosY();
			double z = entityIn.getPosZ();
			worldIn.playSound(null, x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, SoundCategory.PLAYERS, 2.0f, 1 - pitchMod / 2.0f + pitchRandom.nextFloat() * pitchMod / 2.0f);
		}
	}
	
	@Override
    public int getItemEnchantability() {
        return 30;
    }
}