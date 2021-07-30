package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import thesilverecho.avaritia.common.config.CommonManager;
import thesilverecho.avaritia.common.util.EntityUtil;

import javax.annotation.Nonnull;
import java.util.Random;

public class MagnetBlockerBlock extends Block implements IQuickBreak
{
	public MagnetBlockerBlock()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f));
	}


	@Override
	public void onPlace(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving)
	{
		world.getBlockTicks().scheduleTick(pos, this, 1);
		super.onPlace(state, world, pos, oldState, isMoving);
	}

	@Override
	public void tick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
	{
		super.tick(state, worldIn, pos, rand);
		worldIn.getBlockTicks().scheduleTick(pos, this, 1);
		EntityUtil.getEntitiesInRangeOfPos(ItemEntity.class, pos, worldIn, CommonManager.CLIENT.horizontalRange.get(), CommonManager.CLIENT.verticalRange.get()).forEach(itemEntity ->
				itemEntity.getTags().add("cant_move"));
	}

/*@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}


	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new MagnetBlockerTile();
	}*/
}
