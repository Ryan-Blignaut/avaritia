package thesilverecho.avaritia.common.util;

import net.minecraft.item.ItemStack;

public class ItemStackWrapper
{
	public final ItemStack stack;

	public ItemStackWrapper(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ItemStackWrapper)
		{
			ItemStackWrapper stack2 = (ItemStackWrapper) o;
			if (stack.getItem().equals(stack2.stack.getItem()) && stack.getDamageValue() == stack2.stack.getDamageValue())
				if (stack.getTag() == null && stack2.stack.getTag() == null)
					return true;
				else if (stack.getTag() == null ^ stack2.stack.getTag() == null)
					return false;
				else return stack.getTag().equals(stack2.stack.getTag());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int h = stack.getItem().hashCode();
		if (stack.getTag() != null)
			h = h ^ stack.getTag().hashCode();
		return h ^ stack.getDamageValue();
	}

}
