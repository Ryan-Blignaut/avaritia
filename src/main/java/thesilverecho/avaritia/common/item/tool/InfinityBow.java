package thesilverecho.avaritia.common.item.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.item.ModGroup;

public class InfinityBow extends BowItem
{
	public InfinityBow()
	{
		super(new Properties().stacksTo(1).tab(ModGroup.AVARITIA));
	}

	public boolean isFoil(ItemStack stack)
	{
		return false;
	}

	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft)
	{
		if (!(entityLiving instanceof PlayerEntity))
			return;
		final PlayerEntity playerEntity = (PlayerEntity) entityLiving;
		int force = this.getUseDuration(stack) - timeLeft;
		float f = getPowerForTime(force);
		if (!world.isClientSide)
		{
//			for (int i = 0; i < 3; i++)
			{
				ArrowEntity arrowEntity = new ArrowEntity(world, playerEntity);
				arrowEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, f * 3.0F, 1.0F);
				if (f == 1.0F)
					arrowEntity.setCritArrow(true);
				world.addFreshEntity(arrowEntity);
			}

		}
		world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + force * 0.5F);
	}

	@Override
	public int getUseDuration(ItemStack p_77626_1_)
	{
		return 1200;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		playerEntity.startUsingItem(hand);
		return ActionResult.consume(itemstack);
	}

}
