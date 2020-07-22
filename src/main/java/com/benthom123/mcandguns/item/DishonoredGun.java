package com.benthom123.mcandguns.item;

import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.entity.EntityDishonoredBullet;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class DishonoredGun extends ItemGun {

	public DishonoredGun(Properties property) {
		super(property, "dishonoredgun");
		rpm = 250;
		reloadTime = 35;
		clipSize = 4;
		baseRecoil = 8f;
		baseYawRecoil = 2.2f;
		damage = 17;
		knockback = 1.2f;
		inacc = 0.2f;
		range = 5;
		speed = 5.0f;
		auto = false;
	}
	
	@Override
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof DishonoredBullet) {
            	itemstack1.shrink(1);
            	return true;
            }
         }

         return player.abilities.isCreativeMode;
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
        	
        	EntityDishonoredBullet shot = new EntityDishonoredBullet(worldIn, (LivingEntity)entityIn, speed, inaccIn, damageIn, knockback, rangeIn);
        	worldIn.addEntity(shot);
        }
	}
	
	@Override
    public int getItemEnchantability() {
        return 18;
    }
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("The Chad");
	}
}