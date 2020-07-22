package com.benthom123.mcandguns.item;

import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.entity.EntityDart;

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

public class BambooGun extends ItemGun {

	public BambooGun(Properties property) {
		super(property, "bamboogun");
		rpm = 300;
		reloadTime = 20;
		clipSize = 1;
		baseRecoil = 4.0f;
		baseYawRecoil = 0.0f;
		damage = 6;
		knockback = 0.4f;
		inacc = 0.1f;
		range = 15;
		auto = false;
		speed = 2.0f;
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
        	
        	EntityDart shot = new EntityDart(worldIn, (LivingEntity)entityIn, speed, inaccIn, damageIn, knockback, rangeIn);
        	worldIn.addEntity(shot);
        }
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof PoisonDart) {
            	itemstack1.shrink(1);
            	return true;
            }
         }

         return player.abilities.isCreativeMode;
	}
	
	@Override
    public int getItemEnchantability() {
        return 22;
    }
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Blowgun");
	}
	
	//plays the firing sound on a successful shoot
	@Override
	void playFireSound(Entity entityIn, World worldIn) {
		if(!worldIn.isRemote()) {
			double x = entityIn.getPosX();
			double y = entityIn.getPosY();
			double z = entityIn.getPosZ();
			worldIn.playSound(null, x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT, SoundCategory.PLAYERS, 5, 1.2f - pitchMod / 2.0f + pitchRandom.nextFloat() * pitchMod / 2.0f);
		}
	}
}
