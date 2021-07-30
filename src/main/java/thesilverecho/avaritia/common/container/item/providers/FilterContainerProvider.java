package thesilverecho.avaritia.common.container.item.providers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import thesilverecho.avaritia.common.container.item.FilterContainer;
import thesilverecho.avaritia.common.item.module.filter.FilterType;

import javax.annotation.Nullable;
import java.util.Optional;

public class FilterContainerProvider implements INamedContainerProvider
{

	private final FilterType filterType;
	private final ItemStack stack;
	private final int slot;

	public FilterContainerProvider(FilterType filterType, ItemStack stack, int slot)
	{
		this.filterType = filterType;
		this.stack = stack;
		this.slot = slot;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("avaritia.container." + filterType.getName());
	}

	@Nullable
	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
	{
		final Optional<IItemHandler> handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve();
		return handler.map(iItemHandler -> new FilterContainer(filterType, i, stack, slot, playerInventory, iItemHandler)).orElse(null);
	}
}
