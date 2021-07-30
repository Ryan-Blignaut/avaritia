package thesilverecho.avaritia.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thesilverecho.avaritia.client.model.CapacitorBakedModel;
import thesilverecho.avaritia.client.model.CapacitorModelLoader;
import thesilverecho.avaritia.client.model.baked.ExtremeBakedModel;
import thesilverecho.avaritia.client.model.baked.ResBakedModel;
import thesilverecho.avaritia.client.model.entity.TickerRender2;
import thesilverecho.avaritia.client.model.loader.CosmicModelGeometry;
import thesilverecho.avaritia.client.render.block.CompactorBlockTileRender;
import thesilverecho.avaritia.client.render.item.IHaloRender;
import thesilverecho.avaritia.client.screen.*;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.*;
import thesilverecho.avaritia.common.item.bagofhloding.BagOfHoldingScreen;
import thesilverecho.avaritia.common.item.magnet.MagnetFilterScreen;
import thesilverecho.avaritia.common.item.module.filter.FilterScreen;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Avaritia.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup
{
	public static void init(final FMLClientSetupEvent event)
	{
		DeferredWorkQueue.runLater(() ->
		{
			ScreenManager.register(ModContainers.XP_STORAGE_CONTAINER.get(), XpStorageScreen::new);
			ScreenManager.register(ModContainers.MAGNET_FILTER.get(), MagnetFilterScreen::new);
			ScreenManager.register(ModContainers.UPGRADE_BENCH_CONTAINER.get(), UpgradeBenchScreen::new);
			ScreenManager.register(ModContainers.BAG_OF_HOLDING_CONTAINER.get(), BagOfHoldingScreen::new);
			ScreenManager.register(ModContainers.CAPACITOR_CONTAINER.get(), CapacitorScreen::new);
			ScreenManager.register(ModContainers.COMPACTOR_CONTAINER.get(), CompactorScreen::new);
			ScreenManager.register(ModContainers.EXTREME_CRAFTING_TABLE_CONTAINER.get(), ExtremeCraftingTableScreen::new);
			ScreenManager.register(ModContainers.AUTOMATED_USER_CONTAINER.get(), AutomatedUserScreen::new);
			ModContainers.FILTERS.forEach((filterType, containerTypeRegistryObject) -> ScreenManager.register(containerTypeRegistryObject.get(), FilterScreen::new));
		});
//		RenderingRegistry.registerEntityRenderingHandler(ModEntities.TICKER.get(), TickerRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.TICKER.get(), TickerRender2::new);
		//			e.getRenderer(ModEntities.TICKER.get().);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.TICKER.get(), TickerRender2::new);

//		ModEntities.TICKER.get().

		RenderTypeLookup.setRenderLayer(ModBlocks.CAPACITOR_BLOCK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.COMPACTOR_BLOCK.get(), RenderType.cutoutMipped());

		ClientRegistry.bindTileEntityRenderer(ModTiles.COMPACTOR_TILE.get(), CompactorBlockTileRender::new);

		ItemModelsProperties.register(ModItems.INFINITY_BOW.get(),
				new ResourceLocation("pull"),
				(stack, world, entity) -> entity == null ? 0.0f : entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F);

		ItemModelsProperties.register(ModItems.INFINITY_BOW.get(),
				new ResourceLocation("pulling"),
				(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);


//		for (Map.Entry<String, PlayerRenderer> entry : Minecraft.getInstance().getRenderManager().getSkinMap().entrySet())
//		{
//			PlayerRenderer render = entry.getValue();
////			render.addLayer(new LayerPlague(render));
//		}


	}


	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
		if (!event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS))
			return;
		event.addSprite(AvaritiaTextures.HALO);
		event.addSprite(AvaritiaTextures.HALO_NOISE);
		event.addSprite(CapacitorBakedModel.TEXTURE);
		AvaritiaTextures.LIST.forEach(event::addSprite);

	}

	@SubscribeEvent
	public static void registerCustomModelLoaders(ModelRegistryEvent event)
	{
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "capacitorloader"), new CapacitorModelLoader());
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "super"), CosmicModelGeometry.Loader.INSTANCE);
	}


	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		ModelResourceLocation key = new ModelResourceLocation(new ResourceLocation(Avaritia.MOD_ID, "res"), "inventory");
		ResBakedModel resBakedModel = new ResBakedModel(event.getModelRegistry().get(key));
		event.getModelRegistry().put(key, resBakedModel);
		ModRegistry.getRegItems(item -> item instanceof IHaloRender).forEach(item -> register(event, item, ExtremeBakedModel::new));
	}

	public static <T extends IBakedModel> void register(ModelBakeEvent event, RegistryObject<Item> item, Function<IBakedModel, T> function)
	{
		ModelResourceLocation baseItemModel = new ModelResourceLocation(item.getId(), "inventory");
		IBakedModel bakedModel = function.apply(event.getModelRegistry().get(baseItemModel));
		event.getModelRegistry().put(baseItemModel, bakedModel);
	}

}
