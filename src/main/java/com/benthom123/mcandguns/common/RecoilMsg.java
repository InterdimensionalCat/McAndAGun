package com.benthom123.mcandguns.common;


import net.minecraft.network.*;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

import com.benthom123.mcandguns.client.PlayerHandler;

public class RecoilMsg {

	    private final float antiYaw;
	    private final float antiPitch;
		private static float currentclip;
		private static float currentreload;
		private static boolean reloading;
	  public RecoilMsg(PacketBuffer buf) {
	        this.antiYaw = buf.readFloat();
	        this.antiPitch = buf.readFloat();
	        currentclip = buf.readFloat();
	        currentreload = buf.readFloat();
	        reloading = buf.readBoolean();
	    }

	   public RecoilMsg(float antiYaw, float antiPitch, float clp, float rel, boolean reload) {
	        this.antiYaw = antiYaw;
	        this.antiPitch = antiPitch;
	        currentclip = clp;
	        currentreload = rel;
	        reloading = reload;
	    }

	    public void encode(PacketBuffer buf) {
	    	buf.writeFloat(antiYaw);
	    	buf.writeFloat(antiPitch);
	    	buf.writeFloat(currentclip);
	    	buf.writeFloat(currentreload);
	    	buf.writeBoolean(reloading);
	    }

	    public void handle(Supplier<NetworkEvent.Context> context) {
	    	context.get().enqueueWork(new RecoilHandler(antiYaw, antiPitch, currentclip, currentreload, reloading));
	    	context.get().setPacketHandled(true);
	    }
}

class RecoilHandler implements Runnable {
	
    private final float antiYaw;
    private final float antiPitch;
	private static float currentclip;
	private static float currentreload;
	private static boolean reloading;
	
	public RecoilHandler(float antiYaw, float antiPitch, float clp, float rel, boolean reload) {
		this.antiYaw = antiYaw;
		this.antiPitch = antiPitch;
        currentclip = clp;
        currentreload = rel;
        reloading = reload;
	}

	@Override
	public void run() {	
		PlayerHandler.yawMod = antiYaw;
		PlayerHandler.pitchMod = antiPitch;
		PlayerHandler.currentclip = currentclip;
		PlayerHandler.currentreload = currentreload;
		PlayerHandler.reloading = reloading;
	}
}
