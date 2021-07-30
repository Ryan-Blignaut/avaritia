package thesilverecho.avaritia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import thesilverecho.avaritia.common.container.SolarPanelContainer;
import thesilverecho.avaritia.common.tile.SolarPanelTile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SolarPanelBlock extends Block implements IQuickBreak
{
	public SolarPanelBlock()
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
		return new SolarPanelTile();
	}

	@Override
	@Nonnull
	public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit)
	{
		if (!world.isClientSide)
		{
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof SolarPanelTile)
			{
				INamedContainerProvider containerProvider = new INamedContainerProvider()
				{
					@Override
					public ITextComponent getDisplayName()
					{
						return new TranslationTextComponent("x");
					}

					@Override
					public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity)
					{
						return new SolarPanelContainer(i, world, pos, playerInventory, playerEntity);
					}
				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
			} else
			{
				throw new IllegalStateException("Our named container provider is missing!");
			}
		}
		return ActionResultType.SUCCESS;
	}

}
