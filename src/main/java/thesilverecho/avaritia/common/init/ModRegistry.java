package thesilverecho.avaritia.common.init;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import thesilverecho.avaritia.common.Avaritia;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Avaritia.MOD_ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Avaritia.MOD_ID);
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Avaritia.MOD_ID);
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Avaritia.MOD_ID);
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Avaritia.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Avaritia.MOD_ID);


	public static void init()
	{
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


		ITEMS.register(modEventBus);
		BLOCKS.register(modEventBus);
		TILES.register(modEventBus);
		CONTAINERS.register(modEventBus);
		ENTITIES.register(modEventBus);
//		RECIPE_SERIALIZERS.register(modEventBus);
		ModRecipes.init();


		ModItems.register();
		ModContainers.register();
		ModBlocks.register();
		ModRecipes.register();
		ModTiles.register();
		ModEntities.register();

	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> getItems(Class<T> clazz)
	{
		return ITEMS.getEntries().stream()
				.map(RegistryObject::get)
				.filter(clazz::isInstance)
				.map(item -> (T) item)
				.collect(Collectors.toList());
	}

	public static Collection<Item> getItems(Predicate<Item> predicate)
	{
		return ITEMS.getEntries().stream()
				.map(RegistryObject::get)
				.filter(predicate)
				.collect(Collectors.toList());
	}


	public static Collection<RegistryObject<Item>> getRegItems(Predicate<Item> predicate)
	{
		return ITEMS.getEntries().stream()
				.filter(itemRegistryObject -> predicate.test(itemRegistryObject.get()))
				.collect(Collectors.toList());
	}
}
