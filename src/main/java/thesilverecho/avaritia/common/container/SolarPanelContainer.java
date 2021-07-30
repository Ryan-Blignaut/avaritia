package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.init.ModContainers;
import thesilverecho.avaritia.common.item.magnet.MagnetItem;
import thesilverecho.avaritia.common.item.magnet.UpgradeItem;

import javax.annotation.Nonnull;

public class SolarPanelContainer extends BaseContainer
{
	public final PlayerEntity playerEntity;

	public SolarPanelContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player)
	{
		super(ModContainers.UPGRADE_BENCH_CONTAINER.get(), windowId);
		TileEntity tileEntity = world.getBlockEntity(pos);
		this.playerEntity = player;
		if (tileEntity != null)
			(tileEntity).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> addSlotRange(iItemHandler, 0, 20, 20, 3, 30));

		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 10, 70);

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
			if (index == 2)
			{
				if (!this.moveItemStackTo(slotStack, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(slotStack, itemstack);
			} else if (index != 1 && index != 0)
			{
				if (slotStack.getItem() instanceof MagnetItem)
				{
					if (!this.moveItemStackTo(slotStack, 0, 1, false))
						return ItemStack.EMPTY;
				} else if (slotStack.getItem() instanceof UpgradeItem)
				{
					if (!this.moveItemStackTo(slotStack, 1, 2, false))
						return ItemStack.EMPTY;
				} else if (index < 30)
				{
					if (!this.moveItemStackTo(slotStack, 30, 39, false))
						return ItemStack.EMPTY;
				} else if (index < 39 && !this.moveItemStackTo(slotStack, 3, 30, false))
					return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(slotStack, 3, 39, false))
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
