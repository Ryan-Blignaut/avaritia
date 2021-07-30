package thesilverecho.avaritia.common.item.magnet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.client.old.FilterSlot;
import thesilverecho.avaritia.common.container.BaseContainer;
import thesilverecho.avaritia.common.init.ModContainers;

import javax.annotation.Nonnull;

public class MagnetContainer extends BaseContainer
{
	public final ItemStack stack;
	public final int slot;

	public MagnetContainer(int windowId, PlayerInventory playerInventory)
	{
		super(ModContainers.MAGNET_FILTER.get(), windowId);
		PlayerEntity player = playerInventory.player;
		if (player.getMainHandItem().getItem() instanceof MagnetItem)
		{
			stack = player.getMainHandItem();
			slot = player.inventory.selected;
		} else if (player.getOffhandItem().getItem() instanceof MagnetItem)
		{
			this.stack = player.getOffhandItem();
			slot = 40;
		} else
		{
			stack = ItemStack.EMPTY;
			slot = -1;
			return;
		}

		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::accept);

		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 8, 85);
	}

	@Override
	public boolean stillValid(@Nonnull PlayerEntity playerIn)
	{
		return true;
	}


	@Nonnull
	@Override
	public ItemStack clicked(int slotId, int dragType, @Nonnull ClickType clickTypeIn, @Nonnull PlayerEntity player)
	{
		Slot slot = slotId >= 0 ? getSlot(slotId) : null;
		if (slot instanceof FilterSlot)
			if (player.inventory.getCarried().isEmpty() || clickTypeIn == ClickType.QUICK_MOVE)
				slot.set(ItemStack.EMPTY);
			else
				slot.set(player.inventory.getCarried().copy());


		return super.clicked(slotId, dragType, clickTypeIn, player);
	}

	@Override
	public boolean canTakeItemForPickAll(@Nonnull ItemStack stack, @Nonnull Slot slotIn)
	{
		if (slotIn instanceof FilterSlot)
			return false;
		return super.canTakeItemForPickAll(stack, slotIn);
	}

	@Nonnull
	@Override
	public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index)
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


	private void addSlotRangeCustom(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new FilterSlot(handler, index, x, y));
			x += dx;
			index++;
		}
	}

	private void accept(IItemHandler iItemHandler)
	{
		addSlot(new FilterSlot(iItemHandler, 0, 10, 10));

//		addSlotRangeCustom(iItemHandler, 1, 8, 20, 9, 18);
//		addSlotRangeCustom(iItemHandler, 10, 8, 38, 9, 18);

	}
}
