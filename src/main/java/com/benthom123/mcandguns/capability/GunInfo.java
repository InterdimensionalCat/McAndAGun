package com.benthom123.mcandguns.capability;

public interface GunInfo {
	
	public void setClip(int clip);
	public void setReload(int reload);
	public void setRecoil(float recoil, float yaw, float antiyaw, float antirec);
	public void setReloading(boolean reload);
	public void setCooldown(int cd);
	public int getClip();
	public int getReload();
	public float getRecoil();
	public float getYaw();
	public float getAntiYaw();
	public float getAntiRecoil();
	public boolean isReloading();
	public int getCooldown();
	
}


