package com.benthom123.mcandguns.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderBullet<T extends EntityBullet> extends EntityRenderer<T> {

	public static final ResourceLocation bullet = new ResourceLocation("mcandguns:textures/entity/riflebullet.png");
	
	
	public RenderBullet(EntityRendererManager renderManager) {
		super(renderManager);
	}

//	 public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
//	      this.bindEntityTexture(entity);
//	      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//	      GlStateManager.pushMatrix();
//	      GlStateManager.disableLighting();
//	      GlStateManager.translatef((float)x, (float)y, (float)z);
//	      GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F, 0.0F, 1.0F, 0.0F);
//	      GlStateManager.rotatef(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch), 0.0F, 0.0F, 1.0F);
//	      Tessellator tessellator = Tessellator.getInstance();
//	      BufferBuilder bufferbuilder = tessellator.getBuffer();
//	      
//	      
//	      GlStateManager.enableRescaleNormal();
//	      float f9 = (float)0 - partialTicks;
//	      if (f9 > 0.0F) {
//	         float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
//	         GlStateManager.rotatef(f10, 0.0F, 0.0F, 1.0F);
//	      }
//
//	      GlStateManager.rotatef(45.0F, 1.0F, 0.0F, 0.0F);
//	      GlStateManager.scalef(0.05625F, 0.05625F, 0.05625F);
//	      GlStateManager.translatef(-4.0F, 0.0F, 0.0F);
//	      if (this.renderOutlines) {
//	         GlStateManager.enableColorMaterial();
//	         GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
//	      }
//
//	      GlStateManager.normal3f(0.05625F, 0.0F, 0.0F);
//	      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//	      bufferbuilder.pos(-3.5D, -2.0D, -2.0D).tex(0D / 32.0D, 6D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, -2.0D, 2.0D).tex(5D / 32.0D, 6D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, 2.0D, 2.0D).tex(5D / 32.0D, 11D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, 2.0D, -2.0D).tex(0D / 32.0D, 11D / 32.0D).endVertex();
//	      tessellator.draw();
//	      GlStateManager.normal3f(-0.05625F, 0.0F, 0.0F);
//	      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//	      bufferbuilder.pos(-3.5D, 2.0D, -2.0D).tex(0D / 32.0D, 6D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, 2.0D, 2.0D).tex(5D / 32.0D, 6D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, -2.0D, 2.0D).tex(5D / 32.0D, 11D / 32.0D).endVertex();
//	      bufferbuilder.pos(-3.5D, -2.0D, -2.0D).tex(0D / 32.0D, 11D / 32.0D).endVertex();
//	      tessellator.draw();
//
//	      for(int j = 0; j < 4; ++j) {
//	         GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
//	         GlStateManager.normal3f(0.0F, 0.0F, 0.05625F);
//	         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//	         bufferbuilder.pos(-4.0D, -2.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
//	         bufferbuilder.pos(4.0D, -2.0D, 0.0D).tex(0.53125D, 0.0D).endVertex();
//	         bufferbuilder.pos(4.0D, 2.0D, 0.0D).tex(0.53125D, 0.1875D).endVertex();
//	         bufferbuilder.pos(-4.0D, 2.0D, 0.0D).tex(0.0D, 0.1875D).endVertex();
//	         tessellator.draw();
//	      }
//
//	      if (this.renderOutlines) {
//	         GlStateManager.tearDownSolidRenderingTextureCombine();
//	         GlStateManager.disableColorMaterial();
//	      }
//
//	      GlStateManager.disableRescaleNormal();
//	      GlStateManager.enableLighting();
//	      GlStateManager.popMatrix();
//	      super.doRender(entity, x, y, z, entityYaw, partialTicks);
//	   }
	
	   public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		      matrixStackIn.push();
		      matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
		      matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));

		      matrixStackIn.rotate(Vector3f.XP.rotationDegrees(45.0F));
		      matrixStackIn.scale(0.05625F, 0.05625F, 0.05625F);
		      matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
		      IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entityIn)));
		      MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
		      Matrix4f matrix4f = matrixstack$entry.getMatrix();
		      Matrix3f matrix3f = matrixstack$entry.getNormal();
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, -2.0f, -2.0f, 0f / 32.0f, 6f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, -2.0f, 2.0f, 5f / 32.0f, 6f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, 2.0f, 2.0f, 5f / 32.0f, 11f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, 2.0f, -2.0f, 0f / 32.0f, 11f / 32.0f, -1, 0, 0, packedLightIn);
		      
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, 2.0f, -2.0f, 0f / 32.0f, 6f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, 2.0f, 2.0f, 5f / 32.0f, 6f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, -2.0f, 2.0f, 5f / 32.0f, 11f / 32.0f, -1, 0, 0, packedLightIn);
		      this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -3.5f, -2.0f, -2.0f, 0f / 32.0f, 11f / 32.0f, -1, 0, 0, packedLightIn);

		      for(int j = 0; j < 4; ++j) {
		         matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
		         this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -4.0f, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
		         this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 4.0f, -2, 0, 0.53125f, 0.0f, 0, 1, 0, packedLightIn);
		         this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 4.0f, 2, 0, 0.53125f, 0.1875f, 0, 1, 0, packedLightIn);
		         this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -4.0f, 2, 0, 0.0f, 0.1875f, 0, 1, 0, packedLightIn);
		      }

		      matrixStackIn.pop();
		      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		   }

		   public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn) {
		      vertexBuilder.pos(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).tex(textureX, textureY).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(normals, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
		   }
  

	@Override
	public ResourceLocation getEntityTexture(EntityBullet entity) {
		return bullet;
	}

}
