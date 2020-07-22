package com.benthom123.mcandguns.common;


import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.*;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

import com.benthom123.mcandguns.item.ItemGun;

public class BooleanMsg {

	    private final boolean data;
	  public BooleanMsg(PacketBuffer buf) {
	        this.data = buf.readBoolean();
	    }

	   public BooleanMsg(boolean data) {
	        this.data = data;
	    }

	    public void encode(PacketBuffer buf) {
	        buf.writeBoolean(data);
	    }

	    public void handle(Supplier<NetworkEvent.Context> context) {
	    	context.get().enqueueWork(new BoolHandler(data, context.get().getSender()));
	    	context.get().setPacketHandled(true);
	    }
}

class BoolHandler implements Runnable {
	
	public boolean d;
	 ServerPlayerEntity p;
	
	public BoolHandler(boolean data,  ServerPlayerEntity player) {
		d = data;
		p = player;
	}

	@Override
	public void run() {	
		if(p.getHeldItemMainhand() != null) {
			if(p.getHeldItemMainhand().getItem() instanceof ItemGun) {
				ItemGun gun = (ItemGun)(p.getHeldItemMainhand().getItem());
				gun.render = this.d;
			}
		}
	}
}
