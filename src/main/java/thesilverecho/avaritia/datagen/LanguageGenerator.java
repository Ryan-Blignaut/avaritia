package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.init.ModContainers;
import thesilverecho.avaritia.common.init.ModItems;

public class LanguageGenerator extends LanguageProvider
{
	public LanguageGenerator(DataGenerator gen)
	{
		super(gen, Avaritia.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
//		ItemGroup
		add("itemGroup.avaritia", "Avaritia");

//		Curio Slot
		add("curios.identifier.magnet", "Magnet Slot");

//		Items
		addItem(ModItems.BLACK_PAPER, "Black Paper");
		addItem(ModItems.MAGNET, "Magnet");
		addItem(ModItems.WRENCH, "Wrench");
		addItem(ModItems.GUIDE, "Guide");

		addItem(ModItems.SUPER_TIME_IN_A_BOTTLE, "Infinity In A Bottle");
		addItem(ModItems.TIME_IN_A_BOTTLE, "Time In A Bottle");

		addItem(ModItems.INFINITY_SWORD, "Sword Of The Cosmos");
		addItem(ModItems.INFINITY_BOW, "Longbow Of The Heavens");
		addItem(ModItems.NEUTRON_PILE, "Neutron Pile");
		addItem(ModItems.NEUTRON_NUGGET, "Neutron Nugget");
		addItem(ModItems.NEUTRONIUM_INGOT, "Neutronium Ingot");
		addItem(ModItems.EXTREME_INGOT, "Extreme Ingot");
		addItem(ModItems.MATTER, "Matter Cluster");
		addItem(ModItems.INFINITY_CATALYST, "Infinity Catalyst");
		addItem(ModItems.DIAMOND_LATTICE, "Diamond Lattice");
		addItem(ModItems.CRYSTAL_MATRIX_INGOT, "Crystal Matrix Ingot");

		addItem(ModItems.ANGLE_RING, "Angle Ring");
//		addItem(ModItems.TIME_IN_A_BOTTLE, "Time In A Bottle");
//		addItem(ModItems.SUPER_TIME_IN_A_BOTTLE, "Super Time In A Bottle");

		addItem(ModItems.TORCHER, "Torch Placer 3000");

		addItem(ModItems.TAG_BLACKLIST_ITEM, "Blacklist Modifier");
		addItem(ModItems.TAG_STICKY_ITEM, "Sticky Modifier");
		addItem(ModItems.TAG_XP_ITEM, "Xp Modifier");
		addItem(ModItems.ITEM_ENDER_MODE_ITEM, "Item Ender Modifier");
		addItem(ModItems.XP_ENDER_MODE_ITEM, "Xp Ender Modifier");
		addItem(ModItems.VISUALS_ITEM, "Visuals Module");
		addItem(ModItems.NBT_FILTER_ITEM, "Nbt Filter Module");
		addItem(ModItems.META_FILTER_ITEM, "Meta Filter Module");
		addItem(ModItems.TAG_FILTER_ITEM, "Tag Filter Module");

		ModItems.FILTERS_ITEMS.forEach((filterType, itemRegistryObject) -> addItem(itemRegistryObject, "Filter " + filterType.getName().substring(0, 1).toUpperCase() + filterType.getName().substring(1)));

//		Blocks
		addBlock(ModBlocks.CAPACITOR_BLOCK, "Capacitor");
		addBlock(ModBlocks.MODIFIER_BLOCK, "Blank Modifier Block");
		addBlock(ModBlocks.UPGRADE_BENCH_BLOCK, "Upgrade Bench Block");
		addBlock(ModBlocks.COMPACTOR_BLOCK, "Neutronium Compactor Block");
		addBlock(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK, "Dire Crafting Bench");

//		Container Names
		ModContainers.FILTERS.forEach((filterType, containerTypeRegistryObject) -> add("container.avaritia." + filterType, "Filter " + filterType.getName().substring(0, 1).toUpperCase() + filterType.getName().substring(1)));

//		death
		add("death.avaritia.infinity.1", "%s was obliterated by %s");
		add("death.avaritia.infinity.2", "%s was sliced to ribbons by %s");
		add("death.avaritia.infinity.3", "%s was excised from existence by %s");
		add("death.avaritia.infinity.4", "%s was overkilled by %s");
		add("death.avaritia.infinity.5", "%s was annihilated by %s");

//		Tips
		add("tooltip.avaritia.angle_ring", "By using the power of the pythagorean theorem you can fly!!!");
		add("tooltip.avaritia.infinite", "Infinity");
		add("tooltip.avaritia.time", "Stored time: ");
		add("tooltip.avaritia.time_format", "%d H : %d M : %d S");

		add("tooltip.avaritia.torcher", "Total torches(%d)");
		add("tooltip.avaritia.active", "Currently active");
		add("tooltip.avaritia.off", "Currently not active");

		add("tooltip.avaritia.itemLoc", "Item tile %s");
		add("tooltip.avaritia.xpLoc", "XP tile %s");

		add("tooltip.avaritia.desc.base", "When enabled, this module will allows the magnet ");
		add("tooltip.avaritia.black_list_active", "%s is currently active");
		add("tooltip.avaritia.desc.black_list_active", "to only pick up items in this filter");
		add("tooltip.avaritia.sticky_active", "Sticky mode is currently %s ");
		add("tooltip.avaritia.desc.sticky_active", "pull items to the players feet if the current inventory(s) are full");
		add("tooltip.avaritia.xp_active", "Xp collection is currently %s ");
		add("tooltip.avaritia.desc.xp_active", "to collect xp");
		add("tooltip.avaritia.item_ender_active", "Remote item storage is currently %s ");
		add("tooltip.avaritia.desc.item_ender_active", "remotely storage items");
		add("tooltip.avaritia.xp_ender_active", "Remote xp storage is currently %s ");
		add("tooltip.avaritia.desc.xp_ender_active", "remotely storage xp(not implemented yet) ;)");
		add("tooltip.avaritia.visuals_active", "Visuals are currently %s ");
		add("tooltip.avaritia.desc.visuals_active", "display visual elements when collecting items/xp(disable to prevent lag) (not implemented yet) ;)");
		add("tooltip.avaritia.nbt_filter", "NBT filter is currently %s ");
		add("tooltip.avaritia.desc.nbt_filter", "filter items by nbt as well");
		add("tooltip.avaritia.meta_filter", "Meta filter is currently %s ");
		add("tooltip.avaritia.desc.meta_filter", "filter items by meta(eg item damage)");
		add("tooltip.avaritia.tag_filter", "Tag filter filter is currently %s ");
		add("tooltip.avaritia.desc.tag_filter", "filter items by tags(not sure how implemented yet) ;)");
	}
}
