package thesilverecho.avaritia.common.init;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import thesilverecho.avaritia.common.container.*;
import thesilverecho.avaritia.common.container.item.FilterContainer;
import thesilverecho.avaritia.common.item.bagofhloding.BagOfHoldingContainer;
import thesilverecho.avaritia.common.item.magnet.MagnetContainer;
import thesilverecho.avaritia.common.item.module.filter.FilterType;

import java.util.HashMap;

public class ModContainers
{

	public static final RegistryObject<ContainerType<XpStorageContainer>> XP_STORAGE_CONTAINER = register("xp", XpStorageContainer::new);
	public static final RegistryObject<ContainerType<UpgradeBenchContainer>> UPGRADE_BENCH_CONTAINER = register("upgrade_bench", UpgradeBenchContainer::new);
	public static final RegistryObject<ContainerType<CapacitorContainer>> CAPACITOR_CONTAINER = register("capacitor", CapacitorContainer::new);
	public static final RegistryObject<ContainerType<ExtremeCraftingTableContainer>> EXTREME_CRAFTING_TABLE_CONTAINER = register("extreme_crafting_table", ExtremeCraftingTableContainer::new);
	public static final RegistryObject<ContainerType<CompactorContainer>> COMPACTOR_CONTAINER = register("compactor", CompactorContainer::new);
	public static final RegistryObject<ContainerType<AutomatedUserContainer>> AUTOMATED_USER_CONTAINER = register("automated_user", AutomatedUserContainer::new);


	public static final RegistryObject<ContainerType<MagnetContainer>> MAGNET_FILTER = register("magnet", MagnetContainer::new);
	public static final RegistryObject<ContainerType<BagOfHoldingContainer>> BAG_OF_HOLDING_CONTAINER = register("bag", BagOfHoldingContainer::new);
	public static HashMap<FilterType, RegistryObject<ContainerType<FilterContainer>>> FILTERS = new HashMap<>();

	static
	{
		for (FilterType value : FilterType.values())
		{
			final RegistryObject<ContainerType<FilterContainer>> register = register("filter." + value.getName(), (windowId, inv) -> new FilterContainer(value, windowId, inv));
			FILTERS.put(value, register);
		}
	}

	public static <T extends Container> RegistryObject<ContainerType<T>> register(String name, ContainerType.IFactory<T> supplier)
	{
		return ModRegistry.CONTAINERS.register(name, () -> new ContainerType<>(supplier));
	}

	static void register()
	{
	}
}
