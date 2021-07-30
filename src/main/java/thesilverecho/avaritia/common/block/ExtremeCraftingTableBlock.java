package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.tile.ExtremeCraftingTableTile;

public class ExtremeCraftingTableBlock extends Block
{
	public ExtremeCraftingTableBlock()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f));
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new ExtremeCraftingTableTile();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace)
	{
		if (!world.isClientSide())
		{
			TileEntity tile = world.getBlockEntity(pos);

			if (tile instanceof ExtremeCraftingTableTile)
				player.openMenu((ExtremeCraftingTableTile) tile);
		}

		return ActionResultType.SUCCESS;
	}
}
