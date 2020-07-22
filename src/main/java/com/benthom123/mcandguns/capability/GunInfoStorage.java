package com.benthom123.mcandguns.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class GunInfoStorage implements IStorage<GunInfo> {

	@Override
	public INBT writeNBT(Capability<GunInfo> capability, GunInfo instance, Direction side) {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("clip", instance.getClip());
		tag.putInt("reload", instance.getReload());
		tag.putBoolean("reloading", instance.isReloading());
		
		
		tag.putFloat("recoil", instance.getRecoil());
		tag.putFloat("yaw", instance.getYaw());
		tag.putFloat("antirecoil", instance.getAntiRecoil());
		tag.putFloat("antiyaw", instance.getAntiYaw());
		
		return tag;
	
	}

	@Override
	public void readNBT(Capability<GunInfo> capability, GunInfo instance, Direction side, INBT nbt) {
		CompoundNBT tag = (CompoundNBT) nbt;
		
		instance.setClip(tag.getInt("clip"));
		instance.setReload(tag.getInt("reload"));
		instance.setReloading(tag.getBoolean("reloading"));
		
		instance.setRecoil(tag.getFloat("recoil"),
				tag.getFloat("yaw"),
				tag.getFloat("antiyaw"),
				tag.getFloat("antirecoil"));
	}

}
