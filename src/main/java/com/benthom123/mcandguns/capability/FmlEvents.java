package com.benthom123.mcandguns.capability;

import com.benthom123.mcandguns.item.ItemGun;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FmlEvents {

  //  static int capid = 0;
    
	@CapabilityInject(GunInfo.class)
	public static Capability<GunInfo> guninfo = null;
    
    @SubscribeEvent
	public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
    	
		if (event.getObject().getItem() instanceof ItemGun) {
			
			event.addCapability(new ResourceLocation("gunmod", "guninfo"), new GunInfoProvider());
		}
	}
	
}
