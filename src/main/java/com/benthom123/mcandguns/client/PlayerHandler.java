package com.benthom123.mcandguns.client;

import com.benthom123.mcandguns.McAndGuns;
import com.benthom123.mcandguns.RegisterItems;
import com.benthom123.mcandguns.capability.GunInfo;
import com.benthom123.mcandguns.common.BooleanMsg;
import com.benthom123.mcandguns.common.PacketHandler;
import com.benthom123.mcandguns.item.ItemGun;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerHandler {
	
	//public static float yawMod, pitchMod;
	//public static float currentclip;
	//public static float currentreload;
	//public static boolean reloading;
	//public static boolean shouldzoom = false;
	
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if(player == null) return;
		if(player.getHeldItemMainhand() != null) {
			if(player.getHeldItemMainhand().getItem() instanceof ItemGun) {
				
				
				ItemGun gun = (ItemGun)(player.getHeldItemMainhand().getItem());
				
				//Minecraft mc = Minecraft.getInstance();
				
				
				
		       // int width = mc.mainWindow.getScaledWidth();
		       // int height = mc.mainWindow.getScaledHeight();
		        
		       // ClipDisplayScreen scr = new ClipDisplayScreen();
		 
		       // scr.drawRightAlignedString(mc.fontRenderer, "Clip: ", width / 2, (height / 2) - 4, Integer.parseInt("FFAA00", 16));
		        
		       // mc.displayGuiScreen(scr);
				
				//fire every frame
				//if(Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown()) {
				//	gun.mouseDown();
				//}
				
				
				 
				
				//fire on initial press only
				
				if(gun.auto) {
					if(Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown()) {
						PacketHandler.INSTANCE.sendToServer(new BooleanMsg(true));
					}
				} else {
					if(Minecraft.getInstance().gameSettings.keyBindUseItem.isPressed()) {
						
						PacketHandler.INSTANCE.sendToServer(new BooleanMsg(true));
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		if (player == null) return;
		if (player.getHeldItemMainhand() != null) {
			if (player.getHeldItemMainhand().getItem() instanceof ItemGun) {
			}

			ItemStack stack = player.getHeldItemMainhand();
			GunInfo data = stack.getCapability(McAndGuns.guninfo).orElse(null);
			if(data != null) {
				
				float modyaw = data.getYaw()* 0.7f + data.getAntiYaw()* 0.05f;
				float modpitch =data.getRecoil()* 0.7f + data.getAntiRecoil()* 0.05f;
				
				//System.out.println(modyaw + " " + modpitch);
				
				player.rotateTowards(modyaw, modpitch);
			}
		}

	}

	
	@SubscribeEvent
	public static void onFOVUpdateEvent(final FOVUpdateEvent event) {
    	
		ClientPlayerEntity player = Minecraft.getInstance().player;
    	
    	if(player == null) return;
		if(player.getHeldItemMainhand() != null) {
			if(player.getHeldItemMainhand().getItem() instanceof ItemGun) {
				
				ItemStack stack = player.getHeldItemMainhand();
				GunInfo data = stack.getCapability(McAndGuns.guninfo).orElseThrow(IllegalStateException::new);
				
				 Integer zoomfactor = EnchantmentHelper.getEnchantments(stack).get(RegisterItems.Zoom);
			        if(zoomfactor != null && data.getZoom()) {
			        	event.setNewfov(1.0f - 0.2f * zoomfactor.floatValue());
			        }
			}
		}
		
	}
	
    @SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onInitGuiEvent(final RenderGameOverlayEvent.Post event) {
    	Minecraft mc = Minecraft.getInstance();
    	
		ClientPlayerEntity player = Minecraft.getInstance().player;
    	
    	if(player == null) return;
		if(player.getHeldItemMainhand() != null) {
			if(player.getHeldItemMainhand().getItem() instanceof ItemGun) {
				
				
				ItemGun gun = (ItemGun)(player.getHeldItemMainhand().getItem());
				
				ItemStack stack = player.getHeldItemMainhand();
				GunInfo data = stack.getCapability(McAndGuns.guninfo).orElseThrow(IllegalStateException::new);
				
		        int width = mc.getMainWindow().getScaledWidth();
		        int height = mc.getMainWindow().getScaledHeight();
		        
		        //float sf = (float)mc.getMainWindow().getGuiScaleFactor();
		        
		        Integer zoomfactor = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand()).get(RegisterItems.Zoom);
		        if(zoomfactor != null && data.getZoom()) {
		            RenderSystem.disableDepthTest();
		            RenderSystem.depthMask(false);
		            
		            RenderSystem.enableBlend();
		            
		            double squareAxis = Math.min(width, height);
		            double marginHorz = 0;
		            double marginVert = 0;
		            if(width > height) {
		            	marginHorz = (width - squareAxis) / 2.0D;
		            } else {
		            	marginVert = (height - squareAxis) / 2.0D;
		            }
		            
		            //GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		            RenderSystem.disableAlphaTest();
		            RenderSystem.alphaFunc(516, 0.003921569F);
		            mc.getTextureManager().bindTexture(new ResourceLocation(McAndGuns.MODID, "textures/scopetexture.png"));
		            //mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/misc/pumpkinblur.png"));
		            Tessellator tessellator = Tessellator.getInstance();
		            BufferBuilder bufferbuilder = tessellator.getBuffer();
		            
		            
		            //square scope
		            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		            bufferbuilder.pos(marginHorz, squareAxis + marginVert, -90.0D).tex(0.0f, 1.0f).endVertex();
		            bufferbuilder.pos(squareAxis + marginHorz, squareAxis + marginVert, -90.0f).tex(1.0f, 1.0f).endVertex();
		            bufferbuilder.pos(squareAxis + marginHorz, marginVert, -90.0D).tex(1.0f, 0.0f).endVertex();
		            bufferbuilder.pos(marginHorz, marginVert, -90.0D).tex(0.0f, 0.0f).endVertex();
		            tessellator.draw();
		            
		            //left
		            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		            bufferbuilder.pos(0.0D, (double)height, -90.0D).tex(0.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos(marginHorz, (double)height, -90.0D).tex(2.0f/1200.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos(marginHorz, 0.0D, -90.0D).tex(2.0f/1200.0f, 0.0f).endVertex();
		            bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0f, 0.0f).endVertex();
		            tessellator.draw();
		            
		            //right
		            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		            bufferbuilder.pos((double)width - marginHorz, (double)height, -90.0D).tex(0.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, (double)height, -90.0D).tex(2.0f/1200.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, 0.0D, -90.0D).tex(2.0f/1200.0f, 0.0f).endVertex();
		            bufferbuilder.pos((double)width - marginHorz, 0.0D, -90.0D).tex(0.0f, 0.0f).endVertex();
		            tessellator.draw();
		            
		            //top
		            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		            bufferbuilder.pos(0.0D, marginVert, -90.0D).tex(0.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, marginVert, -90.0D).tex(2.0f/1200.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, 0.0D, -90.0D).tex(2.0f/1200.0f, 0.0f).endVertex();
		            bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0f, 0.0f).endVertex();
		            tessellator.draw();
		            
		            //bottom
		            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		            bufferbuilder.pos(0.0D, (double)height, -90.0D).tex(0.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, (double)height, -90.0D).tex(2.0f/1200.0f, 2.0f/1200.0f).endVertex();
		            bufferbuilder.pos((double)width, (double)height - marginVert, -90.0D).tex(2.0f/1200.0f, 0.0f).endVertex();
		            bufferbuilder.pos(0.0D, (double)height - marginVert, -90.0D).tex(0.0f, 0.0f).endVertex();
		            tessellator.draw();
		            
		            
		            
		            RenderSystem.depthMask(true);
		            RenderSystem.enableDepthTest();
		            RenderSystem.disableBlend();
		           // GlStateManager.enableAlphaTest();
		            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		        }
				
				if(!data.isReloading()) {
			 
			       //mc.fontRenderer.drawStringWithShadow("Clip: 20/20", width - 160, height - 40, Integer.parseInt("FFAA00", 16));
					
					int clipSize;
					Integer enchant = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand()).get(RegisterItems.ExtendedMag);
			    	if(enchant != null) {
						clipSize = gun.clipSize + (int)(Math.max(gun.clipSize * 0.1f, 1.0f) * enchant.floatValue());
			    	} else {
			    		clipSize = gun.clipSize;
			    	}
			    	
			    	MatrixStack matrixstack = new MatrixStack();
			        matrixstack.translate(0.0D, 0.0D, (double)(0 + 200.0F));
			        IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
			        String s = "Clip: " + (int)data.getClip() + "/" + clipSize;
			        mc.fontRenderer.renderString(s, 
			        		(float)(width - (float)(mc.fontRenderer.getStringWidth(s))) / 2.0F + 55, (float)(height - 35 - (float)(mc.fontRenderer.FONT_HEIGHT) / 2.0F), 
			        		Integer.parseInt("FFAA00", 16), true, matrixstack.getLast().getMatrix(), irendertypebuffer$impl, true, 0, 15728880);
			            irendertypebuffer$impl.finish();
			    	
					
//			    	mc.fontRenderer.renderString("Clip: " + (int)currentclip + "/" + clipSize, , 
//			    			height - 0.15f * height, Integer.parseInt("FFAA00", 16), true, 
//			    			matrix, buffer, transparentIn, colorBackgroundIn, packedLight)
			    	
			       // mc.fontRenderer.drawStringWithShadow("Clip: " + (int)currentclip + "/" + clipSize, width - 0.47f * width, 
			       // 		height - 0.15f * height, Integer.parseInt("FFAA00", 16));
				} else {
					
					float reloadTime;
					Integer enchant = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand()).get(RegisterItems.FastMag);
					if(enchant != null) {
						reloadTime = (gun.reloadTime * (1.0f - 0.12f*enchant.floatValue()));
					} else {
						reloadTime = gun.reloadTime;
					}
					
					int percent = (int)(100 - Math.ceil((float)data.getReload() / (float)reloadTime * 100));
					int color = 0;
					for(int i = 0; i < 3; i++) {
						if(percent < 50) {
							color = color << 8;
							color += percent + 30;
						} else {
							color = color << 8;
							color +=  100 - percent + 30;
						}
					}
					
					MatrixStack matrixstack = new MatrixStack();
			        matrixstack.translate(0.0D, 0.0D, (double)(0 + 200.0F));
			        IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
			        String s = "Reloading " + percent + "%";
			        mc.fontRenderer.renderString(s, 
			        		(float)(width - (float)(mc.fontRenderer.getStringWidth(s))) / 2.0F + 55, (float)(height - 35 - (float)(mc.fontRenderer.FONT_HEIGHT) / 2.0F), 
			        		color, true, matrixstack.getLast().getMatrix(), irendertypebuffer$impl, true, 0, 15728880);
			            irendertypebuffer$impl.finish();
	
					 //mc.fontRenderer.drawStringWithShadow("Reloading " + percent + "%", width - 0.47f * width, height - 0.15f * height, color);
					
				}

				
				return;
				
			}
		}
		
	}

}
