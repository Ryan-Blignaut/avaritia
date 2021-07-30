package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import thesilverecho.avaritia.common.tile.ColouredBlockTile;
import thesilverecho.avaritia.common.tile.UpgradeBenchTile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class UpgradeBenchBlock extends Block implements IQuickBreak
{
	public UpgradeBenchBlock()
	{
		super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(2.0f));
	}


	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}


	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new UpgradeBenchTile();
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecated")
	public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit)
	{
		if (!world.isClientSide)

			if (player.isShiftKeyDown())
			{
				TileEntity tileEntity = world.getBlockEntity(pos);
				if (tileEntity instanceof ColouredBlockTile)
				{
					final Random random = new Random();
					((ColouredBlockTile) tileEntity).setColour(Math.abs(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB()));
					world.sendBlockUpdated(pos, state, state, 3);
					return ActionResultType.SUCCESS;
				}
			}


		if (!world.isClientSide)
		{
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof UpgradeBenchTile)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
			} else
			{
				throw new IllegalStateException("Our named container provider is missing!");
			}
		}


		return ActionResultType.SUCCESS;
	}

}
