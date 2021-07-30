package thesilverecho.avaritia.common.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import thesilverecho.avaritia.common.block.*;

import java.util.function.Supplier;

public class ModBlocks
{


	public static final RegistryObject<Block> XP_STORAGE_BLOCK = register("xp", XpStorageBlock::new);
	public static final RegistryObject<Block> UPGRADE_BENCH_BLOCK = register("upgrade_bench", UpgradeBenchBlock::new);
	public static final RegistryObject<Block> MODIFIER_BLOCK = register("modifier", BaseBlock::new);
	public static final RegistryObject<Block> MAGNET_BLOCKER = register("magnet_blocker", MagnetBlockerBlock::new);
	public static final RegistryObject<Block> CAPACITOR_BLOCK = register("capacitor", CapacitorBlock::new);
	public static final RegistryObject<Block> CONNECTED_TEST_BLOCK = register("connected_test", ConnectedTest::new);
	public static final RegistryObject<Block> RUNE = register("rune", RuneBlock::new);
	public static final RegistryObject<Block> COMPACTOR_BLOCK = register("compactor", CompactorBlock::new);
	public static final RegistryObject<Block> EXTREME_CRAFTING_TABLE_BLOCK = register("extreme_crafting_table", ExtremeCraftingTableBlock::new);
	public static final RegistryObject<Block> AUTOMATED_USER_Block = register("automated_user", AutomatedUserBlock::new);
//	public static final RegistryObject<Block> FACT = register("factory_block", FactoryBlock::new);


	public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block)
	{
//		ModRegistry.ITEMS.register(name, () -> new Item(new Item.Properties().group(ModGroup.AVARITIA)));
		return ModRegistry.BLOCKS.register(name, block);
	}

	static void register()
	{
	}

}
