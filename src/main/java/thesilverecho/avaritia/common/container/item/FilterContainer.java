package thesilverecho.avaritia.common.container.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.client.old.FilterSlot;
import thesilverecho.avaritia.common.container.BaseContainer;
import thesilverecho.avaritia.common.init.ModContainers;
import thesilverecho.avaritia.common.item.module.filter.FilterType;

import javax.annotation.Nonnull;

public class FilterContainer extends BaseContainer
{
	public static int slotStorage;
	public static ItemStack stackStorage;
	public final ItemStack stack;
	public final int slot;

	public FilterContainer(FilterType type, int id, ItemStack stack, int slot, PlayerInventory playerInventory, IItemHandler handler)
	{
		super(ModContainers.FILTERS.get(type).get(), id);
		this.isUsableByPlayer = p -> true;
		this.stack = stack;
		this.slot = slot;

		if (slot != -1)
			slotStorage = slot;
		if (!stack.isEmpty())
			stackStorage = stack;

//		add slot logic
		this.accept(handler);
//		this.accept(inv);
		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 8, 84);
	}

	public FilterContainer(FilterType type, int id, PlayerInventory playerInventory)
	{
		this(type, id, ItemStack.EMPTY, -1, playerInventory, new ItemStackHandler(type.getSize()));
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn)
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
	protected boolean moveItemStackTo(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
	{

		return super.moveItemStackTo(p_75135_1_, p_75135_2_, p_75135_3_, p_75135_4_);
	}

	@Override
	public boolean canDragTo(Slot slot)
	{
		if (slot instanceof FilterSlot)
			return false;
		return super.canDragTo(slot);
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

	private void accept(IItemHandler iItemHandler)
	{
		int x = 0;
		int y = 0;
		for (int i = 0; i < iItemHandler.getSlots(); i++)
		{
			x += 18;

			if (i % 9 == 0)
			{
				x = 0;
				y += 18;
			}
			addSlot(new FilterSlot(iItemHandler, i, 8 + x, -6 + y));
		}
	}

}
