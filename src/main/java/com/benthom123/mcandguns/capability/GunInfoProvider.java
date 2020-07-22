package com.benthom123.mcandguns.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class GunInfoProvider implements GunInfo, ICapabilitySerializable<INBT> {

	@CapabilityInject(GunInfo.class)
	public static Capability<GunInfo> guninfo = null;
	
	private LazyOptional<GunInfo> instance = LazyOptional.of(() -> this);
	
//	public GunInfoProvider(ItemStack owner) {
//		this.owner = owner;
//	}
	
	public GunInfoProvider() {
		
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == guninfo ? instance.cast() : LazyOptional.empty();
	}

	@Override
	public INBT serializeNBT() {
		
		System.out.println("Serializing NBT");
		
		INBT nbt = guninfo.getStorage().writeNBT(guninfo, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
//		if(owner.hasTag()) {
//			owner.getTag().merge((CompoundNBT)nbt);
//		} else {
//			owner.setTag((CompoundNBT)nbt);
//		}
		
		return nbt;
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		
		System.out.println("Deserializing NBT");
		
			guninfo.getStorage().readNBT(guninfo, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
	}

	public int currentClip;
	public int currentReload;
	public float recoil, yawRecoil, antiYaw, antiRecoil;
	public boolean reloading;
	public int cooldown;

	public void setClip(int clip) {
		currentClip = clip;
	}
	
	public void setReload(int reload) {
		currentReload = reload;
	}
	
	public void setRecoil(float rec, float yaw, float antiyaw, float antirec) {
		recoil = rec;
		yawRecoil = yaw;
		antiYaw = antiyaw;
		antiRecoil = antirec;
	}
	
	@Override
	public void setReloading(boolean reload) {
		reloading = reload;
	}
	
	@Override
	public void setCooldown(int cd) {
		cooldown = cd;
	}

	@Override
	public int getClip() {
		return currentClip;
	}

	@Override
	public int getReload() {
		return currentReload;
	}

	@Override
	public float getRecoil() {
		return recoil;
	}

	@Override
	public float getYaw() {
		return yawRecoil;
	}

	@Override
	public float getAntiYaw() {
		return antiYaw;
	}

	@Override
	public float getAntiRecoil() {
		return antiRecoil;
	}


	@Override
	public boolean isReloading() {
		return reloading;
	}


	@Override
	public int getCooldown() {
		return cooldown;
	}

}
