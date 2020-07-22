package com.benthom123.mcandguns.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractGunEntity extends ProjectileItemEntity {

	
	protected float damage;
	protected float knockback;
	protected int range;

	protected float duration = 100;

	LivingEntity shooter;

	// initial constructor that gets called server side
	// also shoots the projectile
	public AbstractGunEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn, LivingEntity shooter,
			float velocity, float inaccuracy, float damage, float knockback, int range) {
		super(type, shooter, worldIn);
		this.shooter = shooter;
		this.range = range;
		this.damage = damage;
		this.knockback = knockback;

		int i = 1;
		boolean sign = this.rand.nextBoolean();
		if (sign) {
			i = -1;
		}

		double lookmodYaw = i * this.rand.nextDouble() * (double) 30 * (double) inaccuracy;
		double lookmodPitch = i * this.rand.nextDouble() * (double) 20 * (double) inaccuracy;
		
		this.setPositionAndRotation(shooter.getPosX(), shooter.getPosY() + shooter.getEyeHeight(), shooter.getPosZ(),
				(float) (shooter.rotationYawHead + lookmodYaw), (float) (shooter.rotationPitch + lookmodPitch));

		Vector3d v = this.getLookVec().scale((double) velocity);
		this.setMotion(v);

		float f = MathHelper.sqrt(horizontalMag(v));
		this.rotationYaw = (float) (MathHelper.atan2(v.x, v.z) * (double) (180F / (float) Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(v.y, (double) f) * (double) (180F / (float) Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	// constructor that gets called client side to set everything
	public AbstractGunEntity(EntityType<? extends ProjectileItemEntity> type, int range, float damage, float knockback, Vector3d position,
			float yaw, float pitch, float pyaw, float ppitch) {
		super(type, Minecraft.getInstance().player.world);
		this.shooter = Minecraft.getInstance().player;
		this.range = range;
		this.damage = damage;
		this.knockback =knockback;
		this.setPosition(position.x, position.y, position.z);
		this.rotationYaw = yaw;
		this.rotationPitch = pitch;
		this.prevRotationYaw = pyaw;
		this.prevRotationPitch = ppitch;
	}
	
	public AbstractGunEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public void tick() {

		//ObfuscationReflectionHelper.setPrivateValue(ThrowableEntity.class, this, 0, "ignoreTime");
		//ObfuscationReflectionHelper.setPrivateValue(ThrowableEntity.class, this, null, "ignoreEntity");
		this.setShooter(this.shooter);

		if (this.ticksExisted < 5) {
			this.ticksExisted = 5;
		}

		super.tick();

		if (this.ticksExisted > duration + 5) {
			remove();
		}
		
		spawnParticles();
	}

	public void spawnParticles() {
		if (!this.world.isRemote) {
			ServerWorld w = (ServerWorld) world;
			w.spawnParticle(getParticle(), this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(),
					this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(),
					this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 8, 0, 0, 0, 0);
		}
	}

	@Override
	protected abstract void onImpact(RayTraceResult result);
	protected abstract IParticleData getParticle();

}
