package thesilverecho.avaritia.common.item.module.filter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FilterCapabilityProvider implements ICapabilitySerializable<CompoundNBT>
{
	private int slots;
	private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> new ItemStackHandler(slots)
	{
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return !(stack.getItem() instanceof FilterItem) && super.isItemValid(slot, stack);
		}
	});

	public FilterCapabilityProvider(int slots)
	{
		this.slots = slots;
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventory.cast();
		return LazyOptional.empty();

	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT tag = new CompoundNBT();
		inventory.resolve().ifPresent(handler -> tag.put("inv", handler.serializeNBT()));
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		inventory.ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("inv")));
	}

}
