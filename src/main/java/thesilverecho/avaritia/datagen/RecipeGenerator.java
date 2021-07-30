package thesilverecho.avaritia.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;
import thesilverecho.avaritia.common.recipe.MagnetUpgradingRecipe;
import thesilverecho.avaritia.datagen.builder.CompactorRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider
{
	public RecipeGenerator(DataGenerator generatorIn)
	{
		super(generatorIn);
	}

	@Override
	@ParametersAreNonnullByDefault
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
	{
		CompactorRecipeBuilder.builder(Items.IRON_BLOCK, ModItems.INFINITY_SWORD.get(), 10000).build(consumer, new ResourceLocation(Avaritia.MOD_ID, "compactor/" + ModItems.INFINITY_SWORD.get().getRegistryName().getPath()));

		ShapedRecipeBuilder.shaped(ModItems.MAGNET.get())
				.pattern("l r")
				.pattern("idi")
				.pattern("iii")
				.define('l', Tags.Items.GEMS_LAPIS)
				.define('r', Tags.Items.DUSTS_REDSTONE)
				.define('d', Tags.Items.GEMS_DIAMOND)
				.define('i', Tags.Items.INGOTS_IRON)
				.group("magnet")
				.unlockedBy("hasItems", InventoryChangeTrigger.Instance.hasItems(Items.REDSTONE, Items.LAPIS_LAZULI))
				.save(consumer);


		ShapedRecipeBuilder.shaped(ModItems.TORCHER.get())
				.pattern("ddd")
				.pattern("dtd")
				.pattern("   ")
				.define('t', Items.TORCH)
				.define('d', Tags.Items.GEMS_DIAMOND)
				.group("torcher")
				.unlockedBy("torcher", InventoryChangeTrigger.Instance.hasItems(Items.DIAMOND, Items.TORCH))
				.save(consumer);

		specialRecipe(consumer, MagnetUpgradingRecipe.SERIALIZER);
//		Registry.RECIPE_SERIALIZER.getKey(ModRecipeSerializers.COMPACTOR)
	}

	@SuppressWarnings("Deprecated")
	private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer)
	{
		ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);
	}

}
