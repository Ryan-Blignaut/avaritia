package thesilverecho.avaritia.common.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import thesilverecho.avaritia.common.init.ModRecipes;

import java.util.Map;
import java.util.Set;

public class ExtremeRecipe implements IRecipe<IInventory>
{
	private final ResourceLocation id;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final int width;
	private final int height;

	public ExtremeRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, int width, int height)
	{
		this.id = id;
		this.inputs = inputs;
		this.output = output;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		for (int i = 0; i <= 9 - width; ++i)
			for (int j = 0; j <= 9 - height; ++j)
			{
				if (this.checkMatch(inv, i, j, true))
					return true;
				if (this.checkMatch(inv, i, j, false))
					return true;
			}

		return false;
	}

	private boolean checkMatch(IInventory inv, int startX, int startY, boolean mirror)
	{
		for (int i = 0; i < 9; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				int k = i - startX;
				int l = j - startY;
				Ingredient ingredient = Ingredient.EMPTY;
				if (k >= 0 && l >= 0 && k < width && l < height)
					if (mirror)
						ingredient = inputs.get(this.width - k - 1 + l * this.width);
					else
						ingredient = this.inputs.get(k + l * this.width);

				if (!ingredient.test(inv.getItem(i + j * 9)))
					return false;
			}
		}

		return true;


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
		return width >= 9 && height >= 9;
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
		return ModRecipes.EXTREME.get();
	}

	@Override
	public IRecipeType<?> getType()
	{
		return ModRecipes.EXTREME_TYPE;
	}

	@Override
	public boolean isSpecial()
	{
		return true;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtremeRecipe>
	{
		private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

		private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight)
		{
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
			Set<String> set = Sets.newHashSet(keys.keySet());
			set.remove(" ");

			for (int i = 0; i < pattern.length; ++i)
			{
				for (int j = 0; j < pattern[i].length(); ++j)
				{
					String s = pattern[i].substring(j, j + 1);
					Ingredient ingredient = keys.get(s);
					if (ingredient == null)
					{
						throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
					}

					set.remove(s);
					nonnulllist.set(j + patternWidth * i, ingredient);
				}
			}

			if (!set.isEmpty())
			{
				throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
			} else
			{
				return nonnulllist;
			}
		}

		public static ItemStack deserializeItemStack(JsonObject object)
		{
			String name = JSONUtils.getAsString(object, "item");
			final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
			final int count = JSONUtils.getAsInt(object, "count", 1);
			try
			{
				if (object.has("nbt"))
				{
					CompoundNBT baseNbt = new CompoundNBT();
					CompoundNBT nbt;

					JsonElement element = object.get("nbt");
					if (element.isJsonObject())
						nbt = JsonToNBT.parseTag(GSON.toJson(element));
					else
						nbt = JsonToNBT.parseTag(JSONUtils.convertToString(element, "nbt"));

					baseNbt.put("tag", nbt);
					baseNbt.putString("id", name);
					baseNbt.putInt("Count", count);
					return ItemStack.of(baseNbt);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return new ItemStack(item, count);
		}

		@Override
		public ExtremeRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			Map<String, Ingredient> map = Maps.newHashMap();
			for (Map.Entry<String, JsonElement> entry : JSONUtils.getAsJsonObject(json, "key").entrySet())
			{
				if (entry.getKey().length() != 1)
					throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
				if (" ".equals(entry.getKey()))
					throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
				map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
			}
			map.put(" ", Ingredient.EMPTY);


			final JsonArray pattern1 = JSONUtils.getAsJsonArray(json, "pattern");


			String[] pattern = new String[pattern1.size()];
			if (pattern.length > 9)
			{
				throw new JsonSyntaxException("Invalid pattern: too many rows, " + 9 + " is maximum");
			} else
			{
				for (int i = 0; i < pattern.length; ++i)
				{
					String s = JSONUtils.convertToString(pattern1.get(i), "pattern[" + i + "]");
					if (s.length() > 9)
					{
						throw new JsonSyntaxException("Invalid pattern: too many columns, " + 9 + " is maximum");
					}

					if (i > 0 && pattern[0].length() != s.length())
					{
						throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
					}

					pattern[i] = s;
				}

			}
			int width = pattern[0].length();
			int height = pattern.length;

			NonNullList<Ingredient> inputs = deserializeIngredients(pattern, map, width, height);

			final JsonObject result = JSONUtils.getAsJsonObject(json, "result");
			ItemStack output = deserializeItemStack(result);


			return new ExtremeRecipe(recipeId, inputs, output, width, height);
		}

		@Override
		public ExtremeRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer)
		{
			int width = buffer.readVarInt();
			int height = buffer.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(width * height, Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++)
				inputs.set(i, Ingredient.fromNetwork(buffer));
			ItemStack output = buffer.readItem();
			return new ExtremeRecipe(recipeId, inputs, output, width, height);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExtremeRecipe recipe)
		{
			buffer.writeVarInt(recipe.width);
			buffer.writeVarInt(recipe.height);
			for (Ingredient ingredient : recipe.inputs) ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.output);
		}

	}

}
