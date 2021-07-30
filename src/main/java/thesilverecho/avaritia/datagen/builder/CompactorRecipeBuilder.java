package thesilverecho.avaritia.datagen.builder;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import thesilverecho.avaritia.common.init.ModRecipes;

import java.util.function.Consumer;

public class CompactorRecipeBuilder
{
	private final Ingredient ingredient;
	private final IRecipeSerializer<?> serializer;
	private final Ingredient result;
	private final int amount;

	public CompactorRecipeBuilder(Ingredient ingredient, Ingredient result, int amount, IRecipeSerializer<?> serializer)
	{
		this.ingredient = ingredient;
		this.serializer = serializer;
		this.result = result;
		this.amount = amount;
	}


	public static CompactorRecipeBuilder builder(Ingredient ingredient, Ingredient result, int amount)
	{
		return new CompactorRecipeBuilder(ingredient, result, amount, ModRecipes.COMPACTOR_SERIALIZER.get());
	}

	public static CompactorRecipeBuilder builder(Item ingredient, Item result, int amount)
	{
		return new CompactorRecipeBuilder(Ingredient.of(ingredient), Ingredient.of(result), amount, ModRecipes.COMPACTOR_SERIALIZER.get());
	}

	public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id)
	{
		consumer.accept(new Result(id, this));
	}

	public static class Result implements IFinishedRecipe
	{
		private final ResourceLocation id;
		private final CompactorRecipeBuilder builder;

		public Result(ResourceLocation id, CompactorRecipeBuilder builder)
		{
			this.id = id;
			this.builder = builder;
		}

		@Override
		public void serializeRecipeData(JsonObject json)
		{
			json.add("ingredient", builder.ingredient.toJson());
			json.add("result", builder.result.toJson());
			json.addProperty("inputCount", builder.amount);
		}

		@Override
		public ResourceLocation getId()
		{
			return id;
		}

		@Override
		public IRecipeSerializer<?> getType()
		{
			return builder.serializer;
		}

		@Override
		public JsonObject serializeAdvancement()
		{
			return null;
		}

		@Override
		public ResourceLocation getAdvancementId()
		{
			return null;
		}
	}


}
