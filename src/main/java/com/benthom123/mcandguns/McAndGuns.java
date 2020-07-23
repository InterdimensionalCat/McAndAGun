package com.benthom123.mcandguns;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.benthom123.mcandguns.capability.GunInfo;
import com.benthom123.mcandguns.capability.GunInfoProvider;
import com.benthom123.mcandguns.capability.GunInfoStorage;
import com.benthom123.mcandguns.client.StaticClientEventHandler;
import com.benthom123.mcandguns.common.BooleanMsg;
import com.benthom123.mcandguns.common.GunInfoSyncMsg;
import com.benthom123.mcandguns.common.PacketHandler;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(McAndGuns.MODID)

public class McAndGuns
{
	
    public static final String MODID = "mcandguns";
	
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
    
    
	@CapabilityInject(GunInfo.class)
	public static Capability<GunInfo> guninfo = null;
    

    @SuppressWarnings("deprecation")
	public McAndGuns() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
      //  FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        DistExecutor.runWhenOn(Dist.CLIENT, 
        		() -> () -> {FMLJavaModLoadingContext.get().getModEventBus()
        		.addListener(StaticClientEventHandler::RegisterCustomEntityRender);});
        
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(StaticClientEventHandler::RegisterCustomEntityRender);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        
        //register packet messages
        
        PacketHandler.INSTANCE.registerMessage(0, BooleanMsg.class, BooleanMsg::encode, BooleanMsg::new, BooleanMsg::handle);
        PacketHandler.INSTANCE.registerMessage(2, GunInfoSyncMsg.class, GunInfoSyncMsg::encode, GunInfoSyncMsg::new, GunInfoSyncMsg::handle);
      //  CapabilityManager.INSTANCE.register(GunInfo.class, new GunInfoStorage(), () -> new GunInfoProvider(new ItemStack(RegisterItems.gun)));
        CapabilityManager.INSTANCE.register(GunInfo.class, new GunInfoStorage(), () -> new GunInfoProvider());
        
        
        ObfuscationReflectionHelper.setPrivateValue(Enchantment.class, Enchantments.LOOTING, com.benthom123.mcandguns.enchantment.FMJ.LOOTMOD, "field_77351_y");
    }

//    private void doClientStuff(final FMLClientSetupEvent event) {
//        // do something that can only be done on the client
//        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
//        
//    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
