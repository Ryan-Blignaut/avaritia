package thesilverecho.avaritia.common.intergration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import thesilverecho.avaritia.client.screen.CompactorScreen;
import thesilverecho.avaritia.client.screen.ExtremeCraftingTableScreen;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.CompactorContainer;
import thesilverecho.avaritia.common.container.ExtremeCraftingTableContainer;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.init.ModRecipes;

@JeiPlugin
public class Plugin implements IModPlugin
{
	public static final ResourceLocation UID = new ResourceLocation(Avaritia.MOD_ID, "jei_plugin");

	@Override
	public ResourceLocation getPluginUid()
	{
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new ExtremeRecipeCategory(helper));
		registration.addRecipeCategories(new CompactorRecipeCategory(helper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		ClientWorld world = Minecraft.getInstance().level;
		assert world != null;
		RecipeManager manager = world.getRecipeManager();
		registration.addRecipes(manager.getAllRecipesFor(ModRecipes.EXTREME_TYPE), ExtremeRecipeCategory.ID);
		registration.addRecipes(manager.getAllRecipesFor(ModRecipes.COMPACTOR_TYPE), CompactorRecipeCategory.ID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get()), ExtremeRecipeCategory.ID);
		registration.addRecipeCatalyst(new ItemStack(ModBlocks.COMPACTOR_BLOCK.get()), CompactorRecipeCategory.ID);

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{
		registration.addRecipeTransferHandler(ExtremeCraftingTableContainer.class, ExtremeRecipeCategory.ID, 1, 81, 82, 36);
		registration.addRecipeTransferHandler(CompactorContainer.class, CompactorRecipeCategory.ID, 1, 81, 82, 36);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		registration.addRecipeClickArea(ExtremeCraftingTableScreen.class, 120, 18, 21, 14, ExtremeRecipeCategory.ID);
		registration.addRecipeClickArea(CompactorScreen.class, 120, 120, 21, 14, CompactorRecipeCategory.ID);

	}

}
