package thesilverecho.avaritia.client.old;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class FilterIconItemHandler extends ItemStackHandler
{
	public FilterIconItemHandler(ItemStack stack)
	{
		super(1);
		setStackInSlot(0, stack);
	}

}
