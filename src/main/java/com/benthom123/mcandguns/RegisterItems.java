package com.benthom123.mcandguns;


import com.benthom123.mcandguns.enchantment.*;
import com.benthom123.mcandguns.entity.EntityBullet;
import com.benthom123.mcandguns.entity.EntityDart;
import com.benthom123.mcandguns.entity.EntityDishonoredBullet;
import com.benthom123.mcandguns.entity.EntityRay;
import com.benthom123.mcandguns.item.*;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

/**
 * This class has the register event handler for all custom items.
 * This class uses @Mod.EventBusSubscriber so the event handler has to be static
 * This class uses @ObjectHolder to get a reference to the items
 */
@Mod.EventBusSubscriber(modid = McAndGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(McAndGuns.MODID)


public class RegisterItems {
	
	public static EntityType<EntityBullet> entitybullet=null;
	public static EntityType<EntityDishonoredBullet> entitydishoneredbullet=null;
	public static EntityType<EntityDart> entitydart = null;
	public static EntityType<EntityRay> entityray = null;
	
	public static ItemGun gun = null;
	public static PoisonDart dart = null;

    /**
     * The actual event handler that registers the custom items.
     *
     * @param event The event this event handler handles
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //In here you pass in all item instances you want to register.
        //Make sure you always set the registry name.
    	
    	
    	Item.Properties gunproperties = new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1);
    	Item.Properties bulletproperties = new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(64);
    	
    	gun = new ItemGun(gunproperties.maxDamage(350));
    	dart = new PoisonDart(bulletproperties);
    	
        event.getRegistry().registerAll(

            gun,
            new ItemBullet(bulletproperties),
            
            new ItemShotgun(gunproperties.maxDamage(300)),
            new ItemShell(bulletproperties),
            
            new BambooGun(gunproperties.maxDamage(150)),
            dart,
            
            //new ItemUzi(gunproperties.maxDamage(300)),
            new ItemVector(gunproperties.maxDamage(500)),
            new UziBullet(bulletproperties),
            
            new ItemTommy(gunproperties.maxDamage(650)),
            new TommyBullet(bulletproperties),
            
            new DishonoredGun(gunproperties.maxDamage(450)),
            new DishonoredBullet(bulletproperties),
            
            new ItemRayGun(gunproperties.defaultMaxDamage(1000))
            
        );
        
        
        
    }
    
	
    
    
    @SuppressWarnings("deprecation")
	@SubscribeEvent
   	public static void RegisterEntities(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
    	

		
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {

			entitybullet = EntityType.Builder.<EntityBullet>create(EntityClassification.MISC)
					.setCustomClientFactory(EntityBullet::new).setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entitybullet");

			entitydart = EntityType.Builder.<EntityDart>create(EntityClassification.MISC)
					.setCustomClientFactory(EntityDart::new).setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entitydart");

			entitydishoneredbullet = EntityType.Builder.<EntityDishonoredBullet>create(EntityClassification.MISC)
					.setCustomClientFactory(EntityDishonoredBullet::new).setShouldReceiveVelocityUpdates(true)
					.size(0.25f, 0.25f).setTrackingRange(128).setUpdateInterval(1).build("entitydbullet");

			entityray = EntityType.Builder.<EntityRay>create(EntityClassification.MISC)
					.setCustomClientFactory(EntityRay::new).setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entityray");
		});
		
		DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> {

			entitybullet = EntityType.Builder.<EntityBullet>create(EntityClassification.MISC)
					.setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entitybullet");

			entitydart = EntityType.Builder.<EntityDart>create(EntityClassification.MISC)
					.setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entitydart");

			entitydishoneredbullet = EntityType.Builder.<EntityDishonoredBullet>create(EntityClassification.MISC)
					.setShouldReceiveVelocityUpdates(true)
					.size(0.25f, 0.25f).setTrackingRange(128).setUpdateInterval(1).build("entitydbullet");

			entityray = EntityType.Builder.<EntityRay>create(EntityClassification.MISC)
					.setShouldReceiveVelocityUpdates(true).size(0.25f, 0.25f)
					.setTrackingRange(128).setUpdateInterval(1).build("entityray");
		});
    	
		entityRegistryEvent.getRegistry().registerAll(entitybullet.setRegistryName(McAndGuns.MODID, "entitybullet"),

				entitydart.setRegistryName(McAndGuns.MODID, "entitydart"),

				entityray.setRegistryName(McAndGuns.MODID, "entityray"),

				entitydishoneredbullet.setRegistryName(McAndGuns.MODID, "entitydbullet")

		);
	}

    public static final FMJ FMJ = new FMJ();
    public static final FastMag FastMag = new FastMag();
    public static final ExtendedMag ExtendedMag = new ExtendedMag();
    public static final Grip Grip = new Grip();
    public static final LongBarrel LongBarrel = new LongBarrel();
    public static final Zoom Zoom = new Zoom();
    //public static final RapidFire RapidFire = new RapidFire();
    //public static final DeconstructionRounds DeconstructionRounds = new DeconstructionRounds();
    
    @SubscribeEvent
    public static void RegisterEnchantments(final RegistryEvent.Register<Enchantment> enchantmentRegistryEvent) {
    	
    	enchantmentRegistryEvent.getRegistry().registerAll(
    			FMJ,
    			FastMag,
    			ExtendedMag,
    			Grip,
    			LongBarrel,
    			Zoom
    			//DeconstructionRounds
    			);
    }
    

}
