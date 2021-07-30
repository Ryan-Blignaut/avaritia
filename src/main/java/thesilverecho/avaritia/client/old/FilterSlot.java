package thesilverecho.avaritia.client.old;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FilterSlot extends SlotItemHandler
{
	public FilterSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}


	@Override
	public void set(@Nonnull ItemStack stack)
	{
		if (!stack.isEmpty())
			stack.setCount(1);
		super.set(stack);
	}

	@Override
	public boolean mayPlace(@Nonnull ItemStack stack)
	{
		//			do additional checks
		return super.mayPlace(stack);
	}

	@Override
	public boolean mayPickup(PlayerEntity playerIn)
	{
		return false;
	}
}
