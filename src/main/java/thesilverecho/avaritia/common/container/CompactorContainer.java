package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.init.ModContainers;
import thesilverecho.avaritia.common.init.ModRecipes;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class CompactorContainer extends BaseContainer
{
	private final World world;

	public CompactorContainer(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> usable, ItemStackHandler inv)
	{
		super(ModContainers.COMPACTOR_CONTAINER.get(), windowId);
		this.isUsableByPlayer = usable;
		this.world = playerInventory.player.level;
		addSlot(new SlotItemHandler(inv, 0, 38, 35));
		addSlot(new SlotItemHandler(inv, 1, 122, 35)
		{
			@Override
			public boolean mayPlace(@Nonnull ItemStack stack)
			{
				return false;
			}
		});

		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 8, 84);
	}

	public CompactorContainer(int windowId, PlayerInventory inv)
	{
		this(windowId, inv, p -> false, new ItemStackHandler(2));
	}

	@Override
	@Nonnull
	public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			itemstack = slotStack.copy();
			if (index != 0 && index != 1)
			{
				if (world.getRecipeManager().getRecipeFor(ModRecipes.COMPACTOR_TYPE, new Inventory(itemstack), world).isPresent())
				{
					if (!this.moveItemStackTo(slotStack, 0, 2, false))
						return ItemStack.EMPTY;
				} else if (index < 29)
				{
					if (!this.moveItemStackTo(slotStack, 29, 38, false))
						return ItemStack.EMPTY;
				} else if (index < 38 && !this.moveItemStackTo(slotStack, 2, 29, false))
					return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(slotStack, 2, 36, false))
				return ItemStack.EMPTY;

			if (slotStack.isEmpty())
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();

			if (slotStack.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(playerIn, slotStack);
		}

		return itemstack;
	}
}
