package thesilverecho.avaritia.common.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HammerUtil
{
	public static ImmutableList<BlockPos> getBreakableBlocksRadius(ItemStack stack, BlockPos pos, PlayerEntity player, int radius)
	{
		final World world = player.level;
		final Item tool = stack.getItem();
		final RayTraceResult ray = Minecraft.getInstance().hitResult;
		if (ray instanceof BlockRayTraceResult)
		{
			final BlockRayTraceResult traceResult = (BlockRayTraceResult) ray;
			if (traceResult.getType() != RayTraceResult.Type.MISS && !player.isShiftKeyDown() && canToolAffect(tool, stack, world, pos) && radius > 0)
			{
				int yMin = -1;
				int yMax = 2 * radius - 1;
				final Stream<BlockPos> area;
				switch (traceResult.getDirection().ordinal())
				{
					case 1:
					case 2:
						area = BlockPos.betweenClosedStream(pos.offset(-radius, 0, -radius), pos.offset(radius, 0, radius));
						break;
					case 3:
					case 4:
						area = BlockPos.betweenClosedStream(pos.offset(-radius, yMin, 0), pos.offset(radius, yMax, 0));
						break;
					default:
						area = BlockPos.betweenClosedStream(pos.offset(0, yMin, -radius), pos.offset(0, yMax, radius));
				}

				final List<BlockPos> collect = area.filter((blockPos) -> canToolAffect(tool, stack, world, blockPos)).map(BlockPos::immutable).collect(Collectors.toList());
				collect.remove(pos);
				return ImmutableList.copyOf(collect);
			}

		}
		return ImmutableList.of();
	}

	private static boolean canToolAffect(Item tool, ItemStack stack, World world, BlockPos pos)
	{
		return true;
	}

}
