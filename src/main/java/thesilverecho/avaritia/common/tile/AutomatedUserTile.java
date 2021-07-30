package thesilverecho.avaritia.common.tile;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.container.AutomatedUserContainer;
import thesilverecho.avaritia.common.init.ModTiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class AutomatedUserTile extends BasicItemStorageTile implements ITickableTileEntity, INamedContainerProvider
{
	public static final GameProfile DEFAULT_CLICKER = new GameProfile(UUID.fromString("36f373ac-29ef-4150-b664-e7e6006efcd8"), "Automated User");
	private final ItemStackHandler itemStackHandler = new ItemStackHandler(9)
	{
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return true/*slot == 0*/;
		}

		@Override
		protected void onContentsChanged(int slot)
		{
			setChanged();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			return slot == 1 ? stack : super.insertItem(slot, stack, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			return ItemStack.EMPTY;
//			final ItemStack stack = this.stacks.get(slot);
//			return slot == 0 ? stack : super.extractItem(slot, amount, simulate);
		}
	};
	private boolean leftClick;
	private boolean sneaking;
	private boolean smart;
	private int slotBehavior;
	private FakePlayer fakePlayer; //= new FakePlayer()

	public AutomatedUserTile()
	{
		super(ModTiles.AUTOMATED_USER_TILE.get());
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("avaritia.automated_user");
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity p_createMenu_3_)
	{
		return new AutomatedUserContainer(id, inventory, this::isWithinUsableDistance, getInventory());
	}

	@Override
	public void tick()
	{
		if (level == null || level.isClientSide)
			return;

		if (fakePlayer == null)
		{
			fakePlayer = new FakePlayer((ServerWorld) level, DEFAULT_CLICKER);
		}


		for (int i = 0; i < getInventory().getSlots(); i++)
			fakePlayer.inventory.setItem(i, getInventory().getStackInSlot(i));

		final BlockState blockState = level.getBlockState(getBlockPos());
		final Direction direction = blockState.getValue(BlockStateProperties.FACING);


		try
		{
			float pitch = direction == Direction.UP ? -90 : direction == Direction.DOWN ? 90 : 0;
			float yaw = direction == Direction.SOUTH ? 0 : direction == Direction.WEST ? 90 : direction == Direction.NORTH ? 180 : -90;
			Vector3i sideVec = direction.getNormal();
			Direction.Axis a = direction.getAxis();
			Direction.AxisDirection ad = direction.getAxisDirection();
			double x = a == Direction.Axis.X && ad == Direction.AxisDirection.NEGATIVE ? -.5 : .5 + sideVec.getX() / 1.9D;
			double y = 0.5 + sideVec.getY() / 1.9D;
			double z = a == Direction.Axis.Z && ad == Direction.AxisDirection.NEGATIVE ? -.5 : .5 + sideVec.getZ() / 1.9D;
			fakePlayer.moveTo(worldPosition.getX() + x, worldPosition.getY() + y, worldPosition.getZ() + z, yaw, pitch);

			fakePlayer.setItemInHand(Hand.MAIN_HAND, itemStackHandler.getStackInSlot(0));

			if (blockState != level.getBlockState(worldPosition.above()))
			{
				fakePlayer.gameMode.destroyBlock(worldPosition.above());
			}
		} catch (Exception exception)
		{
//			exception.printStackTrace();
		}

//		Vector3d base = new Vector3d(fakePlayer.getPosX(), fakePlayer.getPosY(), fakePlayer.getPosZ());
//		Vector3d look = fakePlayer.getLookVec();
//		Vector3d target = base.add(look.x * 5, look.y * 5, look.z * 5);
//		BlockRayTraceResult trace = world.rayTraceBlocks(new RayTraceContext(base, target, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, fakePlayer));
//		if (blockState != world.getBlockState(trace.getPos()))
//		{
//			final boolean b = fakePlayer.interactionManager.tryHarvestBlock(pos.up());
//		}
//			fakePlayer.interactionManager.useItemOn(fakePlayer, world, fakePlayer.getHeldItemMainhand(), Hand.MAIN_HAND, trace);

	}

	@Override
	public ItemStackHandler getInventory()
	{
		return itemStackHandler;
	}
}
