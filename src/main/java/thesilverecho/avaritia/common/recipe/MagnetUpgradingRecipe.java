package thesilverecho.avaritia.common.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.item.magnet.MagnetItem;
import thesilverecho.avaritia.common.item.magnet.UpgradeItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class MagnetUpgradingRecipe extends SpecialRecipe
{

	public static final SpecialRecipeSerializer<?> SERIALIZER = new SpecialRecipeSerializer<>(MagnetUpgradingRecipe::new);

	public MagnetUpgradingRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}

	@Override
	public boolean matches(@Nonnull CraftingInventory inv, @Nonnull World worldIn)
	{
		CompoundNBT magnetTag = null;
		ArrayList<String> upgradeList = new ArrayList<>();

		for (int i = 0; i < inv.getContainerSize(); i++)
		{
			ItemStack stack = inv.getItem(i);
			if (!stack.isEmpty())
			{
				final Item item = stack.getItem();
				if (item instanceof MagnetItem)
					magnetTag = stack.copy().getOrCreateTagElement("upgrades");
				else if (item instanceof UpgradeItem)
				{
					final String tag = ((UpgradeItem) item).getTag();
					//not sure if there is a better way to do this(maybe a hash map??)
					if (upgradeList.contains(tag))
						return false;
					else
						upgradeList.add(tag);

				} else
					return false;
			}
		}
		if (magnetTag != null && !upgradeList.isEmpty())
			return upgradeList.stream().noneMatch(magnetTag::contains);

		return false;
	}

	@Nonnull
	@Override
	public ItemStack assemble(@Nonnull CraftingInventory inv)
	{

		ItemStack magnet = ItemStack.EMPTY;
		ArrayList<String> upgradeTagList = new ArrayList<>();

		for (int i = 0; i < inv.getContainerSize(); i++)
		{
			final ItemStack stack = inv.getItem(i);
			final Item item = stack.getItem();
			if (item instanceof MagnetItem)
				magnet = stack;
			else if (item instanceof UpgradeItem)
				upgradeTagList.add(((UpgradeItem) item).getTag());
		}
//      should technically never happen as the matches should catch this but safety first
		if (magnet.isEmpty() || upgradeTagList.isEmpty())
			return ItemStack.EMPTY;
		else
		{
			final ItemStack copy = magnet.copy();
			final CompoundNBT upgrades = new CompoundNBT();
			upgradeTagList.forEach(s -> upgrades.putBoolean(s, true));
			copy.getOrCreateTag().put("upgrades", upgrades);
			return copy;
		}
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return width * height >= 2;
	}

	@Nonnull
	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}
}
