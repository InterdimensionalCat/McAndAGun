package com.benthom123.mcandguns.entity;

import com.benthom123.mcandguns.RegisterItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;


public class EntityBullet extends AbstractGunEntity {

	
	
	public EntityBullet(World worldIn, LivingEntity shooter, float velocity, float inaccuracy, float damage,
			float knockback, int range) {
		super(RegisterItems.entitybullet, worldIn, shooter, velocity, inaccuracy, damage, knockback, range);

	}
	
	   public EntityBullet(FMLPlayMessages.SpawnEntity entity, World worldIn) {
		   super(RegisterItems.entitybullet, worldIn); 
	   }
	

	


	@Override
	protected void onImpact(RayTraceResult result) {
		
		  if (result.getType() == RayTraceResult.Type.ENTITY) {
		         Entity entity = ((EntityRayTraceResult)result).getEntity();
		         entity.hurtResistantTime = 0;
		         
		         //calculate damage
		         float reduction = (float)ticksExisted / (float)range;
		         
		         Vector3d motionoverride = entity.getMotion();
		         
		         entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, shooter), Math.max(damage - reduction, 1.0f));
		         
		         entity.setMotion(motionoverride);
		         
		          if (this.knockback > 0) {
		              Vector3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D);
		              if (vec3d.lengthSquared() > 0.0D) {
		                entity.addVelocity(vec3d.x, 0.1D, vec3d.z);
		              }
		           }
		      }
		  

		      if (!this.world.isRemote) {
		         this.remove();
		      }
		
	}



	@Override
	protected IParticleData getParticle() {
		return ParticleTypes.SMOKE;
	}
	
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}




	@Override
	protected Item getDefaultItem() {
		return RegisterItems.gun;
	}
	
}
