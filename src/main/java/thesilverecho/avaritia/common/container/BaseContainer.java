package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class BaseContainer extends Container
{
	protected Function<PlayerEntity, Boolean> isUsableByPlayer;


	public BaseContainer(@Nullable ContainerType<?> type, int id, Function<PlayerEntity, Boolean> isUsableByPlayer)
	{
		super(type, id);
		this.isUsableByPlayer = isUsableByPlayer;
	}

	protected BaseContainer(@Nullable ContainerType<?> type, int id)
	{
		this(type, id, p -> false);
	}


	@Override
	public boolean stillValid(PlayerEntity playerIn)
	{
		return isUsableByPlayer.apply(playerIn);
	}


	protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
	{
		for (int i = 0; i < amount; i++)
		{
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	protected void addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy)
	{
		for (int j = 0; j < verAmount; j++)
		{
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
	}

	protected void layoutPlayerInventorySlots(IItemHandler handler, int leftCol, int topRow)
	{
		addSlotBox(handler, 9, leftCol, topRow, 9, 18, 3, 18);
		topRow += 58;
		addSlotRange(handler, 0, leftCol, topRow, 9, 18);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
	{
		return ItemStack.EMPTY;
	}
}
