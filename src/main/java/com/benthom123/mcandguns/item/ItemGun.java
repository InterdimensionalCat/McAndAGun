package com.benthom123.mcandguns.item;

import java.util.Random;


import com.benthom123.mcandguns.McAndGuns;
import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.capability.GunInfo;
import com.benthom123.mcandguns.capability.GunInfoProvider;
import com.benthom123.mcandguns.common.GunInfoSyncMsg;
import com.benthom123.mcandguns.common.PacketHandler;
import com.benthom123.mcandguns.entity.EntityBullet;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;


public class ItemGun extends Item {

	//general variables
	private String name = "rifle";
	
	//firing speed variables
	protected int rpm = 300; //rounds per minute
	protected boolean maxFireRate = false; //if true the gun has no cooldown
	public boolean auto = false;
	
	//clip and reloading variables
	public int clipSize = 10;//, currentClip = 0;
	public int reloadTime = 43;//, currentReload = 0;
	//public boolean reloading = false;
	
	//recoil variables
	protected float baseRecoil = 15f, baseYawRecoil = 2.2f;
	//private float recoil, yawRecoil, antiYaw, antiRecoil;
	protected Random recoilRandom = new Random();
	
	//sound variables
	protected Random pitchRandom = new Random();
	protected float pitchMod = 0.03f;
	
	//entity variables
	protected float speed = 5.0f;
	protected float inacc = 0.0f;
	public boolean render = false;
	protected Random rand = new Random();
	protected float damage = 10;
	protected float knockback = 1.3f;
	protected int range = 7;
	
	public ItemGun(Item.Properties property) {
		super(property);
		setRegistryName(McAndGuns.MODID, name);
		this.isDamageable();
	}
	
	public ItemGun(Item.Properties property, String name) {
		super(property);
		this.name = name;
		setRegistryName(McAndGuns.MODID, this.name);
		this.isDamageable();
	}
	
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		if(!worldIn.isRemote()) {
			
			GunInfo data = stack.getCapability(McAndGuns.guninfo).orElseThrow(IllegalStateException::new);
			
			
			if(!isSelected) {
				//set recoil values to zero
				data.setRecoil(0,0,0,0);
				return;
			}
			
			//server stuff
			if(render) {
				render = false;
				if(shoot(data)) {
					playFireSound(entityIn, worldIn);
					spawnProjectile(entityIn, worldIn, stack);
					addRecoil(data, stack);
					addCooldown(data, stack);
					updateClip(data);
					PlayerEntity playerentity = (PlayerEntity)entityIn;
	                  stack.damageItem(1, playerentity, (p_220009_1_) -> {
	                      p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
	                   });
				}
				
				if(data.getClip() <= 0) {
					reload((ServerPlayerEntity)entityIn, data, stack);
				}
				render = false;
			}
			updateCooldown(data);
			data.setReloading(updateReload(data));
			updateRecoil((ServerPlayerEntity)entityIn, data);
			if(data != null) {
				GunInfoSyncMsg msg = new GunInfoSyncMsg(data);
				
				PacketHandler.INSTANCE.sendTo(msg,((ServerPlayerEntity)entityIn).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
			}
			
			
		} else {
			//client stuff
			
			if(!isSelected) {
				return;
			}
			
			GunInfo data = stack.getCapability(McAndGuns.guninfo).orElse(null);
			if(data != null) {
				
				Integer zoomfactor = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.Zoom);
		        if(zoomfactor != null) {
		        	if(Minecraft.getInstance().gameSettings.keyBindAttack.isPressed()) {
		        		data.setZoom(true);
		        	}
		        } else {
		        	data.setZoom(false);
		        }
				
			} 
		}
	}
	
	
	//determines if gun can successfully fire, and fires if it can
	boolean shoot(GunInfo data) {
		if(!data.isReloading() && data.getClip() > 0 && data.getCooldown() <= 0) {
			//successful shot
			return true;
		} else {
			return false;
		}
	}

	//subtract a bullet from the gun after firing
	
	public void updateClip(GunInfo data) {
		
		//decrement clip
		data.setClip(data.getClip() - 1);
		
		//insure current clip does not go below 0
		if(data.getClip() < 0) {
			data.setClip(0);
		}
	}
	
	//decrease reload duration
	public boolean updateReload(GunInfo data) {
		
		//decrement reload time
		data.setReload(data.getReload() - 1);
		
		//make sure reload time does not go below zero
		if(data.getReload() <= 0) {
			data.setReload(0);
			return false;
		}
		return true;
	}
	
	
	//start reload sequence
	public boolean reload(ServerPlayerEntity entityIn, GunInfo data, ItemStack stack) {
		if(hasAmmo(entityIn)) {
			
			
			Integer enchant = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.ExtendedMag);
	    	if(enchant != null) {
				data.setClip(clipSize + (int)(Math.max(clipSize * 0.1f, 1.0f) * enchant.floatValue()));
	    	} else {
				data.setClip(clipSize);
	    	}
			
			enchant = null;
			
			enchant = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.FastMag);
        	if(enchant != null) {
    			data.setReload((int)(reloadTime * (1.0f - 0.12*enchant.floatValue())));
        	} else {
        		data.setReload(reloadTime);
        	}
			
			data.setReloading(true);
			return true;
		}
		return false;
	}
	
	//called to update gun Cooldown
	
	public void updateCooldown(GunInfo data) {
		if(data.getCooldown() > 0 && !maxFireRate) {
			data.setCooldown(data.getCooldown() -1);
		} else {
			data.setCooldown(0);
		}
	}
	
	//put gun on cooldown after a shot
	public void addCooldown(GunInfo data, ItemStack stack) {
		int modrpm = rpm;
		
		
		final int TPM = 30 * 60;
		data.setCooldown(data.getCooldown() + TPM / modrpm);
	}
	
	//changes recoil in onUpdate
	public void updateRecoil(ServerPlayerEntity entityIn, GunInfo data) {
		
		
		//data.setRecoil(data.getRecoil() * 0.7f, data.getYaw() * 0.7f, data.getAntiYaw() * 0.05f, data.getAntiRecoil() * 0.05f);
		
//		RecoilMsg msg = new RecoilMsg(data.getYaw() * 0.7f +  data.getAntiYaw() * 0.05f, 
//				data.getRecoil() * 0.7f +  data.getAntiRecoil() * 0.05f, data.getClip(), data.getReload(), data.isReloading());
		
//		PacketHandler.INSTANCE.sendTo(msg,entityIn.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		
		
		
//		data.setRecoil(
//				data.getRecoil() * 0.4f,
//				data.getYaw() * 0.4f,
//				data.getAntiYaw() * 0.95f,
//				data.getAntiRecoil() * 0.95f);
		
		data.setRecoil(
		data.getRecoil() * 0.4f,
		data.getYaw() * 0.4f,
		data.getAntiYaw() * 0.95f,
		data.getAntiRecoil() * 0.95f);
//		
//		if(data.getRecoil() < 0.1f) {
//			data.setRecoil(
//					0,
//					data.getYaw(),
//					data.getAntiYaw(),
//					data.getAntiRecoil());
//		}
//		
//		if(data.getYaw() < 0.1f) {
//			data.setRecoil(
//					data.getRecoil(),
//					0,
//					data.getAntiYaw(),
//					data.getAntiRecoil());
//		}
//		
//		if(data.getAntiYaw() < 0.1f) {
//			data.setRecoil(
//					data.getRecoil(),
//					data.getYaw(),
//					0,
//					data.getAntiRecoil());
//		}
//		
//		if(data.getAntiRecoil() < 0.1f) {
//			data.setRecoil(
//					data.getRecoil(),
//					data.getYaw(),
//					data.getAntiYaw(),
//					0);
//		}
	}
	
	//add recoil after shot
	void addRecoil(GunInfo data, ItemStack stack) {


    	double dir = MathHelper.nextDouble(recoilRandom, 0, 1);
    	
    	float yawApply = baseYawRecoil;
    	float pitchApply = baseRecoil;
    	
    	Integer enchant = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.Grip);
    	
    	
    	if(enchant != null) {
    		yawApply -= baseYawRecoil * (0.15f * enchant.floatValue());
    		pitchApply -= baseRecoil * (0.15f * enchant.floatValue());
    	}
    	
    	
    	if(dir > 0.5) {
    		
    		data.setRecoil(
    				data.getRecoil() - pitchApply,
    				data.getYaw() + yawApply,
    				data.getAntiYaw() - yawApply * 0.4f,
    				data.getAntiRecoil() + pitchApply * 0.4f);
    		
    	} else {
    		
    		data.setRecoil(
    				data.getRecoil() - pitchApply,
    				data.getYaw() - yawApply,
    				data.getAntiYaw() + yawApply * 0.4f,
    				data.getAntiRecoil() + pitchApply * 0.4f);
    	}
    	
	}
	
	
	
	//plays the firing sound on a successful shoot
	void playFireSound(Entity entityIn, World worldIn) {
		if(!worldIn.isRemote()) {
			double x = entityIn.getPosX();
			double y = entityIn.getPosY();
			double z = entityIn.getPosZ();
			worldIn.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2, 1 - pitchMod / 2.0f + pitchRandom.nextFloat() * pitchMod / 2.0f);
		}
		//double x = Minecraft.getInstance().player.posX;
		//double y = Minecraft.getInstance().player.posY;
		//double z = Minecraft.getInstance().player.posZ;
		//Minecraft.getInstance().getIntegratedServer().getWorld(		Minecraft.getInstance().player.world.getDimension().getType())
		//.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 5, 1 - pitchMod / 2.0f + pitchRandom.nextFloat() * pitchMod / 2.0f);
	}
	
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
        	
        	EntityBullet shot = new EntityBullet(worldIn, (LivingEntity)entityIn, speed, inaccIn, damageIn, knockback, rangeIn);
        	worldIn.addEntity(shot);
        }
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.MAINHAND;
	}
	
	@Override
    public int getItemEnchantability() {
        return 15;
    }
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new TranslationTextComponent("Rifle");
	}
	
	
	boolean hasAmmo(ServerPlayerEntity player) {
		
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if(itemstack1.getItem() instanceof ItemBullet) {
            	itemstack1.shrink(1);
            	return true;
            }
         }

         return player.abilities.isCreativeMode;
	}	
	
	@Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
    {
		return new GunInfoProvider();
    }
	
}
