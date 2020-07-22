package com.benthom123.mcandguns.client;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.entity.EntityBullet;
import com.benthom123.mcandguns.entity.EntityDart;
import com.benthom123.mcandguns.entity.EntityDishonoredBullet;
import com.benthom123.mcandguns.entity.EntityRay;
import com.benthom123.mcandguns.entity.RenderBullet;
import com.benthom123.mcandguns.entity.RenderDart;
import com.benthom123.mcandguns.entity.RenderDishonoredBullet;
import com.benthom123.mcandguns.entity.RenderRay;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class StaticClientEventHandler {

	@SubscribeEvent
	public static void RegisterCustomEntityRender(final FMLClientSetupEvent event) {
		//RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, renderManager -> new SpriteRenderer<EntityBullet>(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(RegisterItems.entitybullet, renderManager -> new RenderBullet<EntityBullet>(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(RegisterItems.entitydart, renderManager -> new RenderDart<EntityDart>(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(RegisterItems.entityray, renderManager -> new RenderRay<EntityRay>(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(RegisterItems.entitydishoneredbullet, renderManager -> new RenderDishonoredBullet<EntityDishonoredBullet>(renderManager));
	}
	
}
