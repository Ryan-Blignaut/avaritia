package thesilverecho.avaritia.common.item.module.filter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import thesilverecho.avaritia.common.container.item.providers.FilterContainerProvider;
import thesilverecho.avaritia.common.item.ModGroup;
import thesilverecho.avaritia.common.util.IItemRightClickable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterItem extends Item implements IItemRightClickable
{

	public static final String NBT_FILTER_KEY = "nbt_filter";
	public static final String META_FILTER_KEY = "meta_filter";
	public static final String TAG_FILTER_KEY = "tag_filter";

	public static final String MAIN_FILTER_TAG = "filter_options";


	private final FilterType filterType;

	public FilterItem(FilterType filterType)
	{
		super(new Properties().stacksTo(1).tab(ModGroup.AVARITIA));
		this.filterType = filterType;
	}


	public static List<ItemStack> getContentsInFilter(ItemStack stack, Predicate<ItemStack> stackPredicate)
	{
		ArrayList<ItemStack> stacks = new ArrayList<>();
		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->
		{
			for (int i = 0; i < h.getSlots(); i++)
				if (!h.getStackInSlot(i).isEmpty())
				{
					final ItemStack stackInSlot = h.getStackInSlot(i);
					if (stackPredicate.test(stackInSlot))
						stacks.add(stackInSlot);
				}
		});
		return stacks;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		openMenu(worldIn, playerIn, playerIn.getItemInHand(handIn), handIn == Hand.MAIN_HAND ? playerIn.inventory.selected : 40);
		return super.use(worldIn, playerIn, handIn);
	}

	public void openMenu(World worldIn, PlayerEntity playerIn, ItemStack stack, int slot)
	{
		if (!worldIn.isClientSide)
			NetworkHooks.openGui((ServerPlayerEntity) playerIn, new FilterContainerProvider(filterType, stack, slot), playerIn.blockPosition());
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
	{
		return new FilterCapabilityProvider(filterType.size);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return false;
	}

	@Override
	public void onClick(ServerPlayerEntity player, ItemStack stack, int slot)
	{
		openMenu(player.level, player, stack, slot);
	}
}
