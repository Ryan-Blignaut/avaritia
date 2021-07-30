package thesilverecho.avaritia.common.container.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ExtremeCraftingTableInventory implements IInventory
{
	private final Container container;
	private final ItemStackHandler inventory;

	public ExtremeCraftingTableInventory(Container container, ItemStackHandler inventory)
	{
		this.container = container;
		this.inventory = inventory;
	}

	@Override
	public int getContainerSize()
	{
		return this.inventory.getSlots();
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < this.getContainerSize(); i++)
		{
			if (!this.inventory.getStackInSlot(i).isEmpty())
				return false;
		}

		return true;
	}

	@Override
	public ItemStack getItem(int slot)
	{
		return this.inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount)
	{
		ItemStack stack = this.inventory.extractItem(slot, amount, false);
		this.container.slotsChanged(this);
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot)
	{
		ItemStack stack = this.inventory.getStackInSlot(slot);
		this.inventory.setStackInSlot(slot, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setItem(int slot, ItemStack stack)
	{
		this.inventory.setStackInSlot(slot, stack);
		this.container.slotsChanged(this);
	}

	@Override
	public void setChanged()
	{
	}

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return true;
	}

	@Override
	public void clearContent()
	{
		for (int i = 0; i < this.getContainerSize(); i++)
		{
			this.inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}
}
