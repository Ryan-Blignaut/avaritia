package thesilverecho.avaritia.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.event.ItemClickEvent;
import thesilverecho.avaritia.common.init.ModItems;

public class InfinityPickaxe extends PickaxeItem
{
	private static final String HAMMER_NBT = "hammer";
	private boolean recCall;

	public InfinityPickaxe()
	{
		super(ModTiers.INFINITY_PICKAXE, 8, 1.4f, new Properties().tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC));
	}

	public static Boolean isHammer(ItemStack stack)
	{
		if (stack.hasTag() && stack.getTag() != null)
			return stack.getTag().getBoolean(HAMMER_NBT);
		else return false;
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if (stack.getTag() != null && stack.getTag().getBoolean("hammer"))
		{
			return 5.0F;
		}
		for (ToolType type : getToolTypes(stack))
			if (state.isToolEffective(type))
				return speed;

		return Math.max(super.getDestroySpeed(stack, state), 6.0F);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (group == this.category)
		{
			ItemStack pick = new ItemStack(this);
			pick.enchant(Enchantments.BLOCK_FORTUNE, 10);
			items.add(pick);
		}
		super.fillItemCategory(group, items);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		final ItemStack heldItem = playerIn.getItemInHand(handIn);
		if (playerIn.isShiftKeyDown())
		{
			final CompoundNBT tag = heldItem.getOrCreateTag();
			tag.putBoolean("hammer", !tag.getBoolean("hammer"));
			playerIn.swing(handIn);
			return ActionResult.success(heldItem);
		}
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player)
	{
		final int range = 7;

		final RayTraceResult result = Minecraft.getInstance().hitResult;
		if (isHammer(itemstack))
			if (result instanceof BlockRayTraceResult && result.getType() == RayTraceResult.Type.BLOCK)
			{
				final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) result;
				final Direction face = rayTraceResult.getDirection();
				boolean doX = face.getStepX() == 0;
				boolean doY = face.getStepY() == 0;
				boolean doZ = face.getStepZ() == 0;
				Vector3i beginDiff = new Vector3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
				Vector3i endDiff = new Vector3i(doX ? range : 0, doY ? range * 2 - 1 : 0, doZ ? range : 0);
				if (recCall)
					return false;
				ItemClickEvent.doItemCapture = recCall = true;
				try
				{
					BlockPos.betweenClosed(pos.offset(beginDiff), pos.offset(endDiff)).forEach(pos1 ->
					{
						if (!player.level.isClientSide && player.level.getBlockState(pos1).getBlock() != Blocks.AIR)
						{
							ItemStack save = player.getMainHandItem();
							player.setItemInHand(Hand.MAIN_HAND, player.getMainHandItem());
							((ServerPlayerEntity) player).gameMode.destroyBlock(pos1);
							player.setItemInHand(Hand.MAIN_HAND, save);
						}
					});
				} finally
				{
					ItemClickEvent.doItemCapture = recCall = false;
					if (!player.level.isClientSide)
					{

						final MatterClusterItem matterClusterItem = new MatterClusterItem();
						final ItemStack stack = new ItemStack(ModItems.MATTER.get());
						MatterClusterItem.setStuff(stack, matterClusterItem.collect(ItemClickEvent.getCapturedDrops()), ItemClickEvent.getCapturedDrops().size());
						player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack));
						ItemClickEvent.getCapturedDrops().clear();
					}
				}
			}


		return false;
	}

	@Override
	public boolean isFoil(ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
	{
//		return super.canPlayerBreakBlockWhileHolding(state, worldIn, pos, player);
		return true;
	}
}
