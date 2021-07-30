package thesilverecho.avaritia.common.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import thesilverecho.avaritia.common.init.ModRecipes;

import javax.annotation.Nonnull;

public class ExtremeCraftingTableSlot extends SlotItemHandler
{
	private final Container container;
	private final IInventory matrix;

	public ExtremeCraftingTableSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Container container, IInventory matrix)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.container = container;
		this.matrix = matrix;
	}

	@Override
	public boolean mayPlace(@Nonnull ItemStack stack)
	{
		return false;
	}

	@Override
	public ItemStack onTake(PlayerEntity player, ItemStack stack)
	{
		NonNullList<ItemStack> remaining = player.level.getRecipeManager().getRemainingItemsFor(ModRecipes.EXTREME_TYPE, this.matrix, player.level);

		for (int i = 0; i < remaining.size(); i++)
		{
			ItemStack slotStack = this.matrix.getItem(i);
			ItemStack remainingStack = remaining.get(i);

			if (!slotStack.isEmpty())
			{
				this.matrix.removeItem(i, 1);
				slotStack = this.matrix.getItem(i);
			}

			if (!remainingStack.isEmpty())
			{
				if (slotStack.isEmpty())
				{
					this.matrix.setItem(i, remainingStack);
				} else if (ItemStack.isSame(slotStack, remainingStack) && ItemStack.tagMatches(slotStack, remainingStack))
				{
					remainingStack.grow(slotStack.getCount());
					this.matrix.setItem(i, remainingStack);
				}
			}
		}

		this.container.slotsChanged(this.matrix);

		return stack;
	}

}
