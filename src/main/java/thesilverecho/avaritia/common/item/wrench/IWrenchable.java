package thesilverecho.avaritia.common.item.wrench;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable
{
	void onWrench(PlayerEntity player, World world, BlockPos pos, Direction face);
}
