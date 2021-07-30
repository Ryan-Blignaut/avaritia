package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;
import thesilverecho.avaritia.common.init.ModRegistry;
import thesilverecho.avaritia.common.item.ResourceItem;

import java.util.Objects;

public class ItemModelGenerator extends ItemModelProvider
{
	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, Avaritia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		singleTexture(Objects.requireNonNull(ModItems.WRENCH.get().getRegistryName()).getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/wrench"));
		singleTexture(Objects.requireNonNull(ModItems.GUIDE.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/guide"));
		singleTexture(Objects.requireNonNull(ModItems.BLACK_PAPER.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/black_paper"));

		singleTexture(Objects.requireNonNull(ModItems.MATTER.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/matter_empty"));
		singleTexture(Objects.requireNonNull(ModItems.MATTER.get().getRegistryName()).getPath() + "_overlay", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/matter_empty_overlay"));

		singleTexture(Objects.requireNonNull(ModItems.TIME_IN_A_BOTTLE.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/time_in_a_bottle"));
		singleTexture(Objects.requireNonNull(ModItems.SUPER_TIME_IN_A_BOTTLE.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/super_time_in_a_bottle"));
//		singleTexture(Objects.requireNonNull(ModItems.RES.get().getRegistryName()).getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/base"));
//		singleTexture(Objects.requireNonNull(ModItems.EXTREME_INGOT.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/extreme_ingot"));
		ModRegistry.getRegItems(item -> item instanceof ResourceItem).forEach(itemRegistryObject ->
				singleTexture(Objects.requireNonNull(itemRegistryObject.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Avaritia.MOD_ID, "item/" + itemRegistryObject.getId().getPath())));


//		withExistingParent(ModItems.UPGRADE_BENCH_ITEM.get().getRegistryName().getPath(), modLoc("block/bench"));
//		withExistingParent(Objects.requireNonNull(ModItems.XP_STORAGE_BLOCK_ITEM.get().getRegistryName()).getPath(), modLoc("block/xp"));
//		withExistingParent(Objects.requireNonNull(ModItems.MODIFIER_BLOCK_ITEM.get().getRegistryName()).getPath(), modLoc("block/modifier"));
//		withExistingParent(Objects.requireNonNull(ModItems.MODIFIER_BLOCK_ITEM.get().getRegistryName()).getPath(), modLoc("block/capacitor"));

//		singleTexture(ModItems.UPGRADE_BENCH_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base"));

		singleTexture(Objects.requireNonNull(ModItems.TAG_BLACKLIST_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/blacklist"));
		singleTexture(Objects.requireNonNull(ModItems.TAG_STICKY_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/slime"));
		singleTexture(Objects.requireNonNull(ModItems.TAG_XP_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/xp"));
		singleTexture(Objects.requireNonNull(ModItems.ITEM_ENDER_MODE_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/eye"));
		singleTexture(Objects.requireNonNull(ModItems.XP_ENDER_MODE_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/eyeg"));
		singleTexture(Objects.requireNonNull(ModItems.VISUALS_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/eyen"));
		singleTexture(Objects.requireNonNull(ModItems.NBT_FILTER_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/n"));
		singleTexture(Objects.requireNonNull(ModItems.META_FILTER_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/m"));
		singleTexture(Objects.requireNonNull(ModItems.TAG_FILTER_ITEM.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/base")).texture("layer1", modLoc("item/upgrades/t"));


		ModItems.FILTERS_ITEMS.forEach((filterType, itemRegistryObject) ->
				singleTexture(Objects.requireNonNull(itemRegistryObject.get().getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", modLoc("item/filter_" + filterType.getName())));


	}
}
