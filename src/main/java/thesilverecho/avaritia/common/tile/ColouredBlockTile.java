package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class ColouredBlockTile extends TileEntity
{

	private int colour;

	public ColouredBlockTile(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	@Override
	public void load(@Nonnull BlockState state, CompoundNBT nbt)
	{
		if (nbt.contains("colour"))
			colour = nbt.getInt("colour");
		super.load(state, nbt);

	}

	@Override
	@Nonnull
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.putInt("colour", colour);
		return super.save(compound);
	}

	public int getColour()
	{
		return colour;
	}

	public void setColour(/*int index,*/ int color)
	{
		this.colour = color;
		/*this.colours.set(index, color);*/
	}

	@Override
	@Nonnull
	public CompoundNBT getUpdateTag()
	{
		return save(new CompoundNBT());
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet)
	{
		if (level != null)
		{
			BlockState blockState = level.getBlockState(getBlockPos());
			load(blockState, packet.getTag());
			level.sendBlockUpdated(getBlockPos(), blockState, blockState, 0);
		}
	}
}
