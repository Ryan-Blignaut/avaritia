package thesilverecho.avaritia.common.intergration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.recipe.CompactorRecipe;

import java.util.List;

public class CompactorRecipeCategory implements IRecipeCategory<CompactorRecipe>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/extreme_crafting_table1.png");
	public static ResourceLocation ID = new ResourceLocation(Avaritia.MOD_ID, "compactor_recipe");
	private final IDrawable icon;
	private final IDrawable bg;

	public CompactorRecipeCategory(IGuiHelper helper)
	{
		this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.COMPACTOR_BLOCK.get()));
		this.bg = helper.createDrawable(TEXTURE, 0, 0, 64, 64);
	}

	@Override
	public ResourceLocation getUid()
	{
		return ID;
	}

	@Override
	public Class<? extends CompactorRecipe> getRecipeClass()
	{
		return CompactorRecipe.class;
	}

	@Override
	public String getTitle()
	{
		return "Test";
	}

	@Override
	public IDrawable getBackground()
	{
		return bg;
	}

	@Override
	public IDrawable getIcon()
	{
		return icon;
	}

	@Override
	public void setIngredients(CompactorRecipe compactorRecipe, IIngredients ingredients)
	{
		ingredients.setOutput(VanillaTypes.ITEM, compactorRecipe.getResultItem());
		ingredients.setInputIngredients(compactorRecipe.getIngredients());
	}

	@Override
	public void setRecipe(IRecipeLayout iRecipeLayout, CompactorRecipe compactorRecipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<ItemStack> outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0);

		stacks.init(0, false, 38, 35);
		stacks.set(0, outputs);

		stacks.init(1, true, 122, 35);
		stacks.set(1, inputs.get(0));
		iRecipeLayout.moveRecipeTransferButton(150, 180);
	}

	@Override
	public void draw(CompactorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
	{
		matrixStack.pushPose();
		Minecraft.getInstance().font.draw(matrixStack, String.valueOf(recipe.getInputCount()), 10, 10, -1);
		matrixStack.popPose();
	}
}
