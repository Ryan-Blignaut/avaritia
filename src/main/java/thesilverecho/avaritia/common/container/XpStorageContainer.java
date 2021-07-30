package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.block.xpstorage.CapabilityXp;
import thesilverecho.avaritia.common.block.xpstorage.XpUtil;
import thesilverecho.avaritia.common.init.ModContainers;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class XpStorageContainer extends BaseContainer
{
	private final IItemHandler playerInventory;

	public XpStorageContainer(int windowId, PlayerInventory inv)
	{
		this(windowId, inv, p -> false);
	}

	public XpStorageContainer(int windowId, PlayerInventory inv, Function<PlayerEntity, Boolean> usable)
	{
		super(ModContainers.XP_STORAGE_CONTAINER.get(), windowId);
		this.playerInventory = new InvWrapper(inv);
		this.isUsableByPlayer = usable;
		layoutPlayerInventorySlots(playerInventory, 10, 70);
	}

//	public int getXp()
//	{
//		return tileEntity.getCapability(CapabilityXp.XP).map(IXpStorage::getXpStored).orElse(0);
//	}

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

	public int getExperienceLevel()
	{
		return 0;
	}

	public int getXpBarScaled(TileEntity tileEntity, int scale)
	{
		return (int) (getExperience(tileEntity) * scale);
	}

	public float getExperience(TileEntity tileEntity)
	{
		AtomicLong l = new AtomicLong();
		tileEntity.getCapability(CapabilityXp.XP).ifPresent(iXpStorage ->
				l.set((long) (iXpStorage.getXpStored() - XpUtil.getExperienceForLevel(iXpStorage.getXpStored()) / (float) getXpBarCapacity()))
		);
		return l.get();//(experienceTotal - XpUtil.getExperienceForLevelL(getExperienceLevel())) / (float) getXpBarCapacity();
	}

	private int getXpBarCapacity()
	{
		return 1;//XpUtil.getXpBarCapacity(getExperienceLevel());
	}
}
