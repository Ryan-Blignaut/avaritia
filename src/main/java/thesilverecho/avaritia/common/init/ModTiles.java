package thesilverecho.avaritia.common.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import thesilverecho.avaritia.common.tile.*;

import java.util.function.Supplier;

public class ModTiles
{

	public static final RegistryObject<TileEntityType<UpgradeBenchTile>> UPGRADE_BENCH_TILE = register("upgrade_bench", UpgradeBenchTile::new, ModBlocks.UPGRADE_BENCH_BLOCK);
	public static final RegistryObject<TileEntityType<CapacitorTile>> CAPACITOR_TILE = register("capacitor", CapacitorTile::new, ModBlocks.CAPACITOR_BLOCK);
	public static final RegistryObject<TileEntityType<ExtremeCraftingTableTile>> EXTREME_CRAFTING_TABLE_TILE = register("extreme_crafting_table", ExtremeCraftingTableTile::new, ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK);
	public static final RegistryObject<TileEntityType<CompactorTile>> COMPACTOR_TILE = register("compactor_tile", CompactorTile::new, ModBlocks.COMPACTOR_BLOCK);
	public static final RegistryObject<TileEntityType<XpStorageTile>> XP_STORAGE_TILE = register("xp", XpStorageTile::new, ModBlocks.XP_STORAGE_BLOCK);
	public static final RegistryObject<TileEntityType<AutomatedUserTile>> AUTOMATED_USER_TILE = register("automated_user_tile", AutomatedUserTile::new, ModBlocks.AUTOMATED_USER_Block);

	public static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<Block> block)
	{
		return ModRegistry.TILES.register(name, () -> TileEntityType.Builder.of(factory, block.get()).build(null));
	}

	static void register()
	{
	}

}
