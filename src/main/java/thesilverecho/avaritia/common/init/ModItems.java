package thesilverecho.avaritia.common.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.item.*;
import thesilverecho.avaritia.common.item.magnet.MagnetFilterScreen;
import thesilverecho.avaritia.common.item.magnet.MagnetItem;
import thesilverecho.avaritia.common.item.magnet.UpgradeItem;
import thesilverecho.avaritia.common.item.module.filter.FilterItem;
import thesilverecho.avaritia.common.item.module.filter.FilterType;
import thesilverecho.avaritia.common.item.tool.InfinityAxe;
import thesilverecho.avaritia.common.item.tool.InfinityBow;
import thesilverecho.avaritia.common.item.tool.InfinitySword;
import thesilverecho.avaritia.common.item.wrench.WrenchItem;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModItems
{
	//@formatter:off


//	public static void init()
//	{
//		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
//	}

	public static final RegistryObject<Item> INFINITY_SWORD                 = register("infinity_sword", InfinitySword::new);
	public static final RegistryObject<Item> INFINITY_BOW                   = register("infinity_bow", InfinityBow::new);
	public static final RegistryObject<Item> INFINITY_PICKAXE               = register("infinity_pickaxe", InfinityPickaxe::new);
	public static final RegistryObject<Item> INFINITY_AXE                   = register("infinity_axe", InfinityAxe::new);
	public static final RegistryObject<Item> INFINITY_SHOVEL                   = register("infinity_shovel", InfinityAxe::new);

	public static final RegistryObject<Item> ANGLE_RING                     = register("angle_ring", AngleRing::new);
	public static final RegistryObject<Item> TIME_IN_A_BOTTLE               = register("time_in_a_bottle", () -> new TimeInABottle(false));
	public static final RegistryObject<Item> SUPER_TIME_IN_A_BOTTLE         = register("super_time_in_a_bottle", () -> new TimeInABottle(true));

	public static final RegistryObject<Item> MAGNET                         = register("magnet", MagnetItem::new);
	public static final RegistryObject<Item> WRENCH                         = register("wrench", WrenchItem::new);
	public static final RegistryObject<Item> MATTER                         = register("matter", MatterClusterItem::new);
	public static final RegistryObject<Item> GUIDE                          = register("guide", () -> new Item(new Item.Properties().tab(ModGroup.AVARITIA).stacksTo(1)));
	public static final RegistryObject<Item> BLACK_PAPER                    = register("black_paper", () -> new Item(new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> TORCHER                        = register("torcher", SmartTorcher::new);
	public static final RegistryObject<Item> TAG_BLACKLIST_ITEM             = register("tag_blacklist_item", () -> new UpgradeItem(MagnetFilterScreen.TAG_BLACKLIST_KEY));
	public static final RegistryObject<Item> TAG_STICKY_ITEM                = register("tag_sticky_item", () -> new UpgradeItem(MagnetFilterScreen.TAG_STICKY_KEY));
	public static final RegistryObject<Item> TAG_XP_ITEM                    = register("tag_xp_item", () -> new UpgradeItem(MagnetFilterScreen.TAG_XP_KEY));
	public static final RegistryObject<Item> ITEM_ENDER_MODE_ITEM           = register("item_ender_mode_item", () -> new UpgradeItem(MagnetFilterScreen.ITEM_ENDER_MODE_KEY));
	public static final RegistryObject<Item> XP_ENDER_MODE_ITEM             = register("xp_ender_mode_item", () -> new UpgradeItem(MagnetFilterScreen.XP_ENDER_MODE_KEY));
	public static final RegistryObject<Item> VISUALS_ITEM                   = register("visuals_item", () -> new UpgradeItem(MagnetFilterScreen.VISUALS_KEY));
	public static final RegistryObject<Item> NBT_FILTER_ITEM                = register("nbt_filter_item", () -> new UpgradeItem(MagnetFilterScreen.NBT_FILTER_KEY));
	public static final RegistryObject<Item> META_FILTER_ITEM               = register("meta_filter_item", () -> new UpgradeItem(MagnetFilterScreen.META_FILTER_KEY));
	public static final RegistryObject<Item> TAG_FILTER_ITEM                = register("tag_filter_item", () -> new UpgradeItem(MagnetFilterScreen.TAG_FILTER_KEY));
	public static final RegistryObject<Item> XP_STORAGE_BLOCK_ITEM          = register("xp_block", () -> new BlockItem(ModBlocks.XP_STORAGE_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> UPGRADE_BENCH_ITEM             = register("upgrade_bench", () -> new BlockItem(ModBlocks.UPGRADE_BENCH_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> MODIFIER_BLOCK_ITEM            = register("modifier", () -> new BlockItem(ModBlocks.MODIFIER_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> MAGNET_BLOCKER_ITEM            = register("magnet_blocker", () -> new BlockItem(ModBlocks.MAGNET_BLOCKER.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> CAPACITOR_BLOCK_ITEM           = register("capacitor", () -> new BlockItem(ModBlocks.CAPACITOR_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> COMPACTOR_BLOCK_ITEM           = register("compactor", () -> new BlockItem(ModBlocks.COMPACTOR_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> CONNECTED_TEST_BLOCK_ITEM      = register("connected_test", () -> new BlockItem(ModBlocks.CONNECTED_TEST_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> RUNE_BLOCK_ITEM                = register("rune", () -> new BlockItem(ModBlocks.RUNE.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> EXTREME_CRAFTING_TABLE_BLOCK_ITEM = register("extreme_crafting_table", () -> new BlockItem(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> AUTOMATED_USER_BLOCK_ITEM      = register("automated_user", () -> new BlockItem(ModBlocks.AUTOMATED_USER_Block.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
//	public static final RegistryObject<Item> FACT_BLOCK_ITEM                = register("factory_block", () -> new BlockItem(ModBlocks.FACT.get(), new Item.Properties().group(ModGroup.AVARITIA)));

	public static final RegistryObject<Item> DIAMOND_LATTICE                = register("diamond_lattice",()-> new Item(new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> CRYSTAL_MATRIX_INGOT           = register("crystal_matrix_ingot", ()-> new Item(new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> NEUTRON_PILE                   = register("neutron_pile", () -> new ResourceItem().noise().setColor(0x33FFFFFF));
	public static final RegistryObject<Item> NEUTRON_NUGGET                 = register("neutron_nugget",()-> new ResourceItem().noise().setColor(0x4DFFFFFF));
	public static final RegistryObject<Item> NEUTRONIUM_INGOT               = register("neutronium_ingot", ()->new ResourceItem().noise().setRarity(Rarity.RARE).setColor(0x99FFFFFF));
	public static final RegistryObject<Item> INFINITY_CATALYST              = register("infinity_catalyst",()-> new ResourceItem().setRarity(Rarity.EPIC));
	public static final RegistryObject<Item> EXTREME_INGOT                  = register("extreme_ingot", () -> new ResourceItem().pulse().setSize(10).setRarity(Avaritia.COSMIC));
	public static final RegistryObject<Item> RECORD_FRAGMENT                = register("record_fragment", ()-> new Item(new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> XP_HOLDER                      = register("xp_holder", XpHolder::new);

	public static HashMap<FilterType, RegistryObject<Item>> FILTERS_ITEMS = new HashMap<>();
	static
	{
		for (FilterType value : FilterType.values())
		{
			final RegistryObject<Item> f = register("filter." + value.getName(), () -> new FilterItem(value));
			FILTERS_ITEMS.put(value, f);
		}
	}

	static void register()
	{
	}


	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier)
	{
		return ModRegistry.ITEMS.register(name, supplier);
	}
}
