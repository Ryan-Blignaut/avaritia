package thesilverecho.avaritia.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class EntityUtil
{
	public static <T extends Entity> List<T> getEntitiesInRangeOfPlayer(Class<? extends T> type, PlayerEntity player, World world, int horizontalRange, int verticalRange)
	{
		return world.getEntitiesOfClass(type, new AxisAlignedBB(player.getX() - horizontalRange, player.getY() - verticalRange, player.getZ() - horizontalRange, player.getX() + horizontalRange, player.getY() + verticalRange, player.getZ() + horizontalRange));
	}

	public static <T extends Entity> List<T> getEntitiesInRangeOfPos(Class<? extends T> type, BlockPos pos, World world, int horizontalRange, int verticalRange)
	{
		return world.getEntitiesOfClass(type, new AxisAlignedBB(pos.getX() - horizontalRange, pos.getY() - verticalRange, pos.getZ() - horizontalRange, pos.getX() + horizontalRange, pos.getY() + verticalRange, pos.getZ() + horizontalRange));
	}

	public static List<BlockPos> getBlocksInArea(PlayerEntity player, int horizontalRange, int verticalRange)
	{
		ArrayList<BlockPos> blocks = new ArrayList<>();
//		BlockPos position = player.getPosition();
		BlockPos.Mutable position = player.blockPosition().mutable();

		for (int x = -horizontalRange; x <= horizontalRange; x++)
			for (int z = -horizontalRange; z <= horizontalRange; z++)
				for (int y = -verticalRange; y <= verticalRange; y++)
					blocks.add(position.offset(x, y, z));

		return blocks;
	}

	public static ItemStack getItemInInventory(PlayerEntity player, Predicate<Item> itemPredicate)
	{
		for (int i = 0; i < player.inventory.getContainerSize(); ++i)
		{
			ItemStack itemstack = player.inventory.getItem(i);
			if (itemPredicate.test(itemstack.getItem()))
				return itemstack;
		}
		return ItemStack.EMPTY;
	}


	public static int adsorb(PlayerEntity player, Predicate<Item> itemPredicate)
	{
		ItemStack stack = getItemInInventory(player, itemPredicate);
		return !stack.isEmpty() ? stack.getCount() : 0;
	}

	public static void adsorbFast(PlayerEntity player, Predicate<Item> itemPredicate, Function<Integer, Boolean> function)
	{
		ItemStack stack = getItemInInventory(player, itemPredicate);
		if (!stack.isEmpty())
		{
			int count = stack.getCount();
			if (function.apply(count))
				stack.shrink(count);

		}

	}
}
