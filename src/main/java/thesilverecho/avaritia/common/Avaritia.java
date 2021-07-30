package thesilverecho.avaritia.common;

import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import thesilverecho.avaritia.client.ClientSetup;
import thesilverecho.avaritia.client.old.ColourHandler;
import thesilverecho.avaritia.common.block.xpstorage.CapabilityXp;
import thesilverecho.avaritia.common.config.CommonManager;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.init.ModPackets;
import thesilverecho.avaritia.common.init.ModRegistry;
import thesilverecho.avaritia.common.intergration.curios.CuriosCompat;

import java.nio.FloatBuffer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("avaritia")
public class Avaritia
{

	public static final Rarity COSMIC = Rarity.create("cosmic", TextFormatting.RED);
	public static final String MOD_ID = "avaritia";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static FloatBuffer cosmicUVs = BufferUtils.createFloatBuffer(4 * 10);


	public Avaritia()
	{
		ModRegistry.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::preInit);
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::registerCustomColours);
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CommonManager.CLIENT_SPEC);

	}

	private void setup(final FMLCommonSetupEvent event)
	{
		CuriosCompat.sendImc();
		ModPackets.setup();
	}

	public void preInit(FMLCommonSetupEvent evt)
	{
		CapabilityXp.register();
	}

	public void registerCustomColours(FMLLoadCompleteEvent event)
	{
		ColourHandler.addBlock(ModBlocks.UPGRADE_BENCH_BLOCK.get());
		ColourHandler.addBlock(ModBlocks.MODIFIER_BLOCK.get());
		ColourHandler.init();
	}

}

