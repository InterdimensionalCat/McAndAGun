package com.benthom123.mcandguns.common;

import java.util.function.Supplier;

import com.benthom123.mcandguns.McAndGuns;
import com.benthom123.mcandguns.capability.GunInfo;
import com.benthom123.mcandguns.item.ItemGun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class GunInfoSyncMsg {

	private CompoundNBT sync;
	public GunInfoSyncMsg(PacketBuffer buf) {
		sync = buf.readCompoundTag();
	}
	
	
	
	public GunInfoSyncMsg(GunInfo data) {
		if(sync == null) {
			sync = new CompoundNBT();
		}
		sync.putInt("clip", data.getClip());
		sync.putInt("reload", data.getReload());
		sync.putBoolean("reloading", data.isReloading());
		sync.putFloat("recoil", data.getRecoil());
		sync.putFloat("yaw", data.getYaw());
		sync.putFloat("antirecoil", data.getAntiRecoil());
		sync.putFloat("antiyaw", data.getAntiYaw());
	}
	
    public void encode(PacketBuffer buf) {
        buf.writeCompoundTag(sync);
    }

    @SuppressWarnings("deprecation")
	public void handle(Supplier<NetworkEvent.Context> context) {
    	//context.get().enqueueWork(new SyncHandler(sync, context.get().getSender()));
    	//context.get().setPacketHandled(true);
    	DistExecutor.runWhenOn(Dist.CLIENT,(()->()-> {
        	context.get().enqueueWork(() -> {
        		
        		ClientPlayerEntity p = Minecraft.getInstance().player;
        		if(p.getHeldItemMainhand() != null) {
        			if(p.getHeldItemMainhand().getItem() instanceof ItemGun) {
        				ItemStack gun = (p.getHeldItemMainhand());
        				GunInfo data = gun.getCapability(McAndGuns.guninfo).orElseThrow(IllegalStateException::new);
        				data.setClip(sync.getInt("clip"));
        				data.setReload(sync.getInt("reload"));
        				data.setReloading(sync.getBoolean("reloading"));
        				
        				data.setRecoil(sync.getFloat("recoil"),
        						sync.getFloat("yaw"),
        						sync.getFloat("antiyaw"),
        						sync.getFloat("antirecoil"));
        			}
        		}
        		
        	});
        	context.get().setPacketHandled(true);
    	}));
    }
    
    class SyncHandler implements Runnable {
    	
    	public CompoundNBT sync;
    	
    	
    	public SyncHandler(CompoundNBT sync) {
    		this.sync = sync;
    	}

    	@Override
    	public void run() {	
//    		if(p.getHeldItemMainhand() != null) {
//    			if(p.getHeldItemMainhand().getItem() instanceof ItemGun) {
//    				ItemStack gun = (p.getHeldItemMainhand());
//    				GunInfo data = gun.getCapability(ItemGun.guninfo).orElseThrow(IllegalStateException::new);
//    				data.setClip(sync.getInt("clip"));
//    				data.setReload(sync.getInt("reload"));
//    				data.setReloading(sync.getBoolean("reloading"));
//    				
//    				data.setRecoil(sync.getFloat("recoil"),
//    						sync.getFloat("yaw"),
//    						sync.getFloat("antiyaw"),
//    						sync.getFloat("antirecoil"));
//    			}
//    		}
    	}
    }
	
}
