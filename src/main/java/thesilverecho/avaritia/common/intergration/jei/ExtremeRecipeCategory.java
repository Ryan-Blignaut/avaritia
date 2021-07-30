package thesilverecho.avaritia.common.intergration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.recipe.ExtremeRecipe;

import java.util.List;

public class ExtremeRecipeCategory implements IRecipeCategory<ExtremeRecipe>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/extreme_crafting_table1.png");
	public static ResourceLocation ID = new ResourceLocation(Avaritia.MOD_ID, "extreme_recipe");
	private final IDrawable icon;
	private final IDrawable bg;

	public ExtremeRecipeCategory(IGuiHelper helper)
	{
		this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get()));
		this.bg = helper.createDrawable(TEXTURE, 0, 0, 128, 128);
	}

	@Override
	public ResourceLocation getUid()
	{
		return ID;
	}

	@Override
	public Class<? extends ExtremeRecipe> getRecipeClass()
	{
		return ExtremeRecipe.class;
	}

	@Override
	public String getTitle()
	{
		return "Test123";
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
	public void setIngredients(ExtremeRecipe extremeRecipe, IIngredients ingredients)
	{
		ingredients.setOutput(VanillaTypes.ITEM, extremeRecipe.getResultItem());
		ingredients.setInputIngredients(extremeRecipe.getIngredients());
	}

	@Override
	public void setRecipe(IRecipeLayout iRecipeLayout, ExtremeRecipe extremeRecipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<ItemStack> outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0);

		stacks.init(0, false, 72, 180);
		stacks.set(0, outputs);

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				int index = 1 + j + (i * 9);
				stacks.init(index, true, j * 18, i * 18);
			}
		}

		int stackIndex = 0;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				int index = 1 + (i * 9) + j;
				stacks.set(index, inputs.get(stackIndex));

				stackIndex++;
			}
		}
		iRecipeLayout.moveRecipeTransferButton(150, 180);
	}

}
