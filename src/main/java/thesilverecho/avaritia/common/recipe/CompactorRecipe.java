package thesilverecho.avaritia.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;
import thesilverecho.avaritia.common.init.ModRecipes;

public class CompactorRecipe implements IRecipe<IInventory>
{
	private final ResourceLocation id;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final int inputCount;

	public CompactorRecipe(ResourceLocation id, Ingredient input, ItemStack output, int inputCount)
	{
		this.id = id;
		this.inputs = NonNullList.of(Ingredient.EMPTY, input);
		this.output = output;
		this.inputCount = inputCount;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{

		ItemStack input = inv.getItem(0);
		return this.inputs.get(0).test(input);
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return inputs;
	}

	@Override
	public ItemStack assemble(IInventory inv)
	{
		return this.output.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	}

	@Override
	public ItemStack getResultItem()
	{
		return this.output;
	}

	@Override
	public ResourceLocation getId()
	{
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModRecipes.COMPACTOR_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return ModRecipes.COMPACTOR_TYPE;
	}

	public boolean matches(IItemHandler inventory)
	{
		return this.matches(inventory, 0, inventory.getSlots());
	}

	public boolean matches(IItemHandler inventory, int startIndex, int endIndex)
	{
		NonNullList<ItemStack> inputs = NonNullList.create();
		for (int i = startIndex; i < endIndex; i++)
		{
			inputs.add(inventory.getStackInSlot(i));
		}

		return RecipeMatcher.findMatches(inputs, this.getIngredients()) != null;
	}

	public int getInputCount()
	{
		return inputCount;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CompactorRecipe>
	{
		@Override
		public CompactorRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			Ingredient input = Ingredient.fromJson(json.getAsJsonObject("ingredient"));
			ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
			int inputCount = JSONUtils.getAsInt(json, "inputCount", 10000);
			return new CompactorRecipe(recipeId, input, output, inputCount);
		}

		@Override
		public CompactorRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer)
		{
			Ingredient input = Ingredient.fromNetwork(buffer);
			ItemStack output = buffer.readItem();
			int inputCount = buffer.readInt();

			return new CompactorRecipe(recipeId, input, output, inputCount);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, CompactorRecipe recipe)
		{
			recipe.inputs.get(0).toNetwork(buffer);
			buffer.writeItem(recipe.output);
			buffer.writeInt(recipe.inputCount);
		}
	}

}
