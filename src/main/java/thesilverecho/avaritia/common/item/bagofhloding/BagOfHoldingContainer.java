package thesilverecho.avaritia.common.item.bagofhloding;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.client.old.FilterSlot;
import thesilverecho.avaritia.common.init.ModContainers;

public class BagOfHoldingContainer extends Container
{
	public final ItemStack stack;
	public final int slot;
	private final IItemHandler playerInventory;

	public BagOfHoldingContainer(int windowId, PlayerInventory playerInventory)
	{
		super(ModContainers.BAG_OF_HOLDING_CONTAINER.get(), windowId);
		this.playerInventory = new InvWrapper(playerInventory);
		PlayerEntity player = playerInventory.player;
		if (player.getMainHandItem().getItem() instanceof BagOfHoldingItem)
		{
			stack = player.getMainHandItem();
			slot = player.inventory.selected;
		} else
		{
			this.stack = player.getOffhandItem();
			slot = 40;
		}

		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::accept);
		layoutPlayerInventorySlots(8, 85);
	}


	@Override
	public boolean stillValid(PlayerEntity playerIn)
	{
		return true;
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem())
		{
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();

//			mergeItemStack(stack, 0, 1, false);

			/*if (index == 0)
			{
				if (!this.mergeItemStack(stack, 1, 37, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(stack, itemstack);
			} else
			{
				if (stack.getItem() == Items.DIAMOND)
				{
					if (!this.mergeItemStack(stack, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				} else if (index < 28)
				{
					if (!this.mergeItemStack(stack, 28, 37, false))
					{
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false))
				{
					return ItemStack.EMPTY;
				}
			}*/

			if (stack.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			} else
			{
				slot.setChanged();
			}

			if (stack.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}

		return itemstack;
	}


	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	private int addSlotRangeCustom(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new FilterSlot(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}


	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
	{
		for (int j = 0; j < verAmount; j++)
		{
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	private void layoutPlayerInventorySlots(int leftCol, int topRow)
	{
		// Player inventory
		addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
	}

	private void accept(IItemHandler iItemHandler)
	{
		addSlotRangeCustom(iItemHandler, 0, 8, 20, 9, 18);
		addSlotRangeCustom(iItemHandler, 9, 8, 38, 9, 18);

	}
}
