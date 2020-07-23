package com.benthom123.mcandguns.entity;

import java.util.List;

import com.benthom123.mcandguns.RegisterItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityRay extends AbstractGunEntity  {
	
	
	public EntityRay(World worldIn, LivingEntity shooter, float velocity, float inaccuracy, float damage,
			float knockback, int range) {
		super(RegisterItems.entityray, worldIn, shooter, velocity, inaccuracy, damage, knockback, range);

	}
	
	
	   public EntityRay(FMLPlayMessages.SpawnEntity entity, World worldIn) {
		   super(RegisterItems.entityray, worldIn); 
	   }
	

	


	@Override
	protected void onImpact(RayTraceResult result) {
		
		  if (result.getType() == RayTraceResult.Type.ENTITY) {
		         Entity entity = ((EntityRayTraceResult)result).getEntity();
		         entity.hurtResistantTime = 0;
		         
		         //calculate damage
		         float reduction = (float)ticksExisted / (float)range;
		         
		         //Vec3d motionoverride = entity.getMotion();
		         
		         Vector3d boxCenter = new Vector3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
		         
		         double rad = 1.1D;
		         
		         AxisAlignedBB boundingBox = new AxisAlignedBB(boxCenter.add(new Vector3d(rad, rad, rad)), 
		        		 boxCenter.add(new Vector3d(-rad, -rad, -rad)));
		         
		         List<Entity> splash = this.world.getEntitiesInAABBexcluding(this.shooter, boundingBox, null);
		         
		         for(int i = 0; i < splash.size(); i++) {
		        	 if(splash.get(i) instanceof LivingEntity) {
		        		 splash.get(i).attackEntityFrom(DamageSource.causeIndirectDamage(this, shooter), Math.max((damage - reduction) / 2.0f, 1.0f));
		        	 }
		         }
		         
		         entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, shooter), Math.max(damage - reduction, 1.0f));
		         
		         //entity.setMotion(motionoverride);
		         
		          if (this.knockback > 0) {
		              Vector3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
		              if (vec3d.lengthSquared() > 0.0D) {
		                entity.addVelocity(vec3d.x, 0.1D, vec3d.z);
		              }
		           }
		          
		          if(!entity.getEntityWorld().isRemote) {
		        	  ServerWorld w = (ServerWorld)entity.getEntityWorld();
			          
			          w.spawnParticle(ParticleTypes.DRAGON_BREATH, this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(),
								this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(),
								this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 20, 
								(this.rand.nextDouble() - 0.5D) / 2.0D, (this.rand.nextDouble() - 0.5D) / 2.0D, (this.rand.nextDouble() - 0.5D) / 2.0D, 0.5);
		          }
		      }
		  

		      if (!this.world.isRemote) {
		         this.remove();
		      }
		
	}


	
	public void spawnParticles() {
		if (!this.world.isRemote) {
			ServerWorld w = (ServerWorld) world;
			w.spawnParticle(getParticle(), this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(),
					this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(),
					this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 8, 0, 0, 0, 0);
			for(int i = 0; i < 8; i++) {
				w.spawnParticle(ParticleTypes.SPLASH, this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(),
						this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(),
						this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 4, 
						(this.rand.nextDouble() - 0.5D) / 2.0D, (this.rand.nextDouble() - 0.5D) / 2.0D, (this.rand.nextDouble() - 0.5D) / 2.0D, 0.5);
			}
		}
	
	}
	@Override
	protected IParticleData getParticle() {
		return ParticleTypes.BUBBLE_COLUMN_UP;
	}
	
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public boolean hasNoGravity() {
		return true;
	}


	@Override
	protected Item getDefaultItem() {
		return RegisterItems.gun;
	}
}
