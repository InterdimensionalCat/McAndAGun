package com.benthom123.mcandguns.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderRay<T extends EntityRay> extends EntityRenderer<T> {

	public static final ResourceLocation ray = new ResourceLocation("mcandguns:textures/item/raygun.png");

	public RenderRay(EntityRendererManager renderManager) {
		super(renderManager);
	}

//	 public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
//	      super.doRender(entity, x, y, z, entityYaw, partialTicks);
//	   }

	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public ResourceLocation getEntityTexture(EntityRay entity) {
		return ray;
	}

}
