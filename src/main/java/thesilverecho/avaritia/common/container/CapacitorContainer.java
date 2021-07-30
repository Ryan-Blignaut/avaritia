package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.init.ModContainers;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;

public class CapacitorContainer extends BaseContainer
{

	public CapacitorContainer(int id, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, ItemStackHandler handler)
	{
		super(ModContainers.CAPACITOR_CONTAINER.get(), id);
		this.isUsableByPlayer = isUsableByPlayer;
		for (int i = 0; i < handler.getSlots(); i++)
			addSlot(new SlotItemHandler(handler, i, 153, 15 + i * 19));

		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 8, 84);
/*		if (tileEntity != null)
			trackInt(new IntReferenceHolder()
			{
				@Override
				public int get()
				{
					return getEnergy();
				}

				@Override
				public void set(int value)
				{
					tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h ->
							((CustomEnergyHandler) h).setEnergy((value)));
				}
			});*/

	}

	public CapacitorContainer(int windowId, PlayerInventory playerInventory)
	{
		this(windowId, playerInventory, p -> false, new ItemStackHandler(3));
	}
	//	public int getEnergy()
//	{
//		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
//	}

//	public double getEnergyScaled()
//	{
//		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage -> (((float) iEnergyStorage.getEnergyStored()) / ((float) iEnergyStorage.getMaxEnergyStored()))).orElse(0f);
////		AtomicDouble result = new AtomicDouble();
////		tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage ->
////				result.set(((float) iEnergyStorage.getEnergyStored()) / ((float) iEnergyStorage.getMaxEnergyStored())));
////		return result.get();
//	}


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
			if (index != 0 && index != 1 && index != 2)
			{
				final Optional<IEnergyStorage> storage = slotStack.getCapability(CapabilityEnergy.ENERGY).resolve();
				if (storage.isPresent())
				{
					if (storage.get().getEnergyStored() < storage.get().getMaxEnergyStored())
						if (!this.moveItemStackTo(slotStack, 0, 3, false))
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
