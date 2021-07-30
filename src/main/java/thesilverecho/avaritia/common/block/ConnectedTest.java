package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;

public class ConnectedTest extends Block
{

	public static final BooleanProperty CONNECTED_DOWN = BooleanProperty.create("down");
	public static final BooleanProperty CONNECTED_UP = BooleanProperty.create("up");
	public static final BooleanProperty CONNECTED_NORTH = BooleanProperty.create("north");
	public static final BooleanProperty CONNECTED_SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty CONNECTED_WEST = BooleanProperty.create("west");
	public static final BooleanProperty CONNECTED_EAST = BooleanProperty.create("east");

	public ConnectedTest()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f));
/*		this.setDefaultState(this.stateContainer.getBaseState()
				.with(EnumMergingBlockRenderMode.RENDER, EnumMergingBlockRenderMode.AUTO));*/

		this.registerDefaultState(this.stateDefinition.any()
				.setValue(CONNECTED_DOWN, Boolean.FALSE)
				.setValue(CONNECTED_EAST, Boolean.FALSE)
				.setValue(CONNECTED_NORTH, Boolean.FALSE)
				.setValue(CONNECTED_SOUTH, Boolean.FALSE)
				.setValue(CONNECTED_UP, Boolean.FALSE)
				.setValue(CONNECTED_WEST, Boolean.FALSE));

	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
//		final EnumMergingBlockRenderMode mode = EnumMergingBlockRenderMode.get(Direction.UP, Direction.NORTH, Direction.WEST);
//		return stateIn.with(EnumMergingBlockRenderMode.RENDER, mode);
		return stateIn.setValue(CONNECTED_DOWN, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.DOWN))
				.setValue(CONNECTED_EAST, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.EAST))
				.setValue(CONNECTED_NORTH, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.NORTH))
				.setValue(CONNECTED_SOUTH, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.SOUTH))
				.setValue(CONNECTED_UP, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.UP))
				.setValue(CONNECTED_WEST, this.isSideConnectable(stateIn, worldIn, currentPos, Direction.WEST));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
//		builder.add(EnumMergingBlockRenderMode.RENDER);
		builder.add(CONNECTED_DOWN, CONNECTED_UP, CONNECTED_NORTH, CONNECTED_SOUTH, CONNECTED_WEST, CONNECTED_EAST);

	}

	private boolean isSideConnectable(BlockState state, IWorld world, BlockPos pos, Direction side)
	{
		final BlockState connected = world.getBlockState(pos.relative(side));
		return this.canConnect(state, connected);
	}

	protected boolean canConnect(@Nonnull BlockState original, @Nonnull BlockState connected)
	{
		return original.getBlock() == connected.getBlock();
	}

}
