package thesilverecho.avaritia.common.item;

import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.config.CommonManager;
import thesilverecho.avaritia.common.util.ItemStackWrapper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MatterClusterItem extends Item
{
	private static final String MAIN_TAG = "clusteritems";
	private static final String TOTAL_COUNT_TAG = "count";

	private static final String ITEM_TAG = "item";
	private static final String LIST_TAG = "list";
	private static final String COUNT_TAG = "item_count";

	private static final int CAPACITY = 9000;

	public MatterClusterItem()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	public static Map<ItemStackWrapper, Integer> getClusterData(ItemStack stack)
	{
		Map<ItemStackWrapper, Integer> map = new HashMap<>(Collections.emptyMap());
		if (stack.getTag() != null)
		{
			final ListNBT list = stack.getTag().getCompound(MAIN_TAG).getList(LIST_TAG, 10);
			IntStream.range(0, list.size()).mapToObj(list::getCompound).forEach(nbt ->
			{
				final CompoundNBT itemNbt = nbt.getCompound(ITEM_TAG);
				itemNbt.remove("Count");
				map.put(new ItemStackWrapper((ItemStack.of(itemNbt))), nbt.getInt(COUNT_TAG));
			});
		}
		return map;

	}

	public static void setStuff(ItemStack stack, Map<ItemStackWrapper, Integer> items, int counter)
	{
		final CompoundNBT tag = stack.getOrCreateTagElement(MAIN_TAG);
		ListNBT list = new ListNBT();
		AtomicInteger counter2 = new AtomicInteger();
		items.forEach((listStack, count) ->
		{
			CompoundNBT nbt = new CompoundNBT();
			final CompoundNBT write = listStack.stack.save(new CompoundNBT());
			write.remove("Count");
			nbt.put(ITEM_TAG, write);
			nbt.putInt(COUNT_TAG, count);
			counter2.getAndAdd(count);
			list.add(nbt);
		});
		tag.put(LIST_TAG, list);
		tag.putInt(TOTAL_COUNT_TAG, counter2.get());
		stack.getOrCreateTag().put(MAIN_TAG, tag);
	}

	public static int getClusterSize(ItemStack cluster)
	{
		if (!cluster.hasTag() || cluster.getTag() != null && !cluster.getTag().contains(MAIN_TAG))
			return 0;
		return cluster.getTag().getCompound(MAIN_TAG).getInt(TOTAL_COUNT_TAG);
	}

	public static void mergeClusters(ItemStack donor, ItemStack recipient)
	{
		int donorcount = getClusterSize(donor);
		int recipientcount = getClusterSize(recipient);

		if (donorcount == 0 || donorcount == CAPACITY || recipientcount == CAPACITY)
			return;
		Map<ItemStackWrapper, Integer> donordata = getClusterData(donor);
		Map<ItemStackWrapper, Integer> recipientdata = getClusterData(recipient);

		List<Map.Entry<ItemStackWrapper, Integer>> datalist = new ArrayList<>(donordata.entrySet());
		while (recipientcount < CAPACITY && donorcount > 0)
		{
			if (datalist.size() < 1)
			{
				break;
			}

			Map.Entry<ItemStackWrapper, Integer> e = datalist.get(0);
			ItemStackWrapper wrap = e.getKey();
			int wrapcount = e.getValue();

			int count = Math.min(CAPACITY - recipientcount, wrapcount);

			if (!recipientdata.containsKey(wrap))
				recipientdata.put(wrap, count);
			else
				recipientdata.put(wrap, recipientdata.get(wrap) + count);

			donorcount -= count;
			recipientcount += count;

			if (wrapcount - count > 0)
				e.setValue(wrapcount - count);
			else
			{
				donordata.remove(wrap);
				datalist.remove(0);
			}
		}
		setStuff(recipient, recipientdata, recipientcount);
		donor.setCount(0);
		if (donorcount > 0)
		{
			setStuff(donor, donordata, donorcount);
		} else
		{
//			donor.setTagCompound(null);
			donor.setCount(0);
		}

	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (!stack.hasTag() || !Objects.requireNonNull(stack.getTag()).contains(MAIN_TAG))
			return;
		final CompoundNBT nbt = stack.getTag().getCompound(MAIN_TAG);

		tooltip.add(new TranslationTextComponent("tooltip.avaritia.matter_info", nbt.getInt(TOTAL_COUNT_TAG), CommonManager.CLIENT.matterCapacity.get()));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (!worldIn.isClientSide)
		{
//			if (stack.getTag() != null)
//			{
//				final CompoundNBT compound = stack.getTag().getCompound(MAIN_TAG);
//				final ListNBT list = compound.getList(LIST_TAG, 10);
//				IntStream.range(0, list.size()).mapToObj(list::getCompound).forEach(nbt ->
//				{
//					ItemStackWrapper wrap = new ItemStackWrapper(ItemStack.read(nbt.getCompound(ITEM_TAG)));
//					final ItemStack stack1 = wrap.stack;
//					stack1.setCount(nbt.getInt(COUNT_TAG));
//					worldIn.addEntity(new ItemEntity(worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), stack1));
//				});
//			}

			getClusterData(stack).forEach((itemStackWrapper, integer) ->
			{
				final ItemStack stack1 = itemStackWrapper.stack;
				stack1.setCount(integer);
				worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), stack1));
			});

		}

		stack.setCount(0);
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	public Map<ItemStackWrapper, Integer> collect(Set<ItemStack> stacks)
	{
		Map<ItemStackWrapper, Integer> ret = new HashMap<>();
		stacks.forEach(stack ->
		{
			final ItemStackWrapper key = new ItemStackWrapper(stack);
			if (!ret.containsKey(key))
				ret.put(key, 0);
			ret.put(key, ret.get(key) + stack.getCount());
		});
		return ret;
	}

	public List<ItemStack> makeClusters(Set<ItemStack> stacks)
	{
		final Map<ItemStackWrapper, Integer> collect = collect(stacks);


		return Lists.newArrayList(ItemStack.EMPTY);
	}

}
