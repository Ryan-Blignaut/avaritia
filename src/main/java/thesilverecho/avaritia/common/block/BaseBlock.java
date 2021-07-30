package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseBlock extends Block implements IQuickBreak
{
	public BaseBlock()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f).isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false).isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false));
	}

//	@Override
//	public boolean hasTileEntity(BlockState state)
//	{
//		return true;
//	}
//
//	@Nullable
//	@Override
//	public TileEntity createTileEntity(BlockState state, IBlockReader world)
//	{
//		return new BaseTile();
//	}


}
