package thesilverecho.avaritia.common.item.bagofhloding;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import thesilverecho.avaritia.common.item.ModGroup;

public class BagOfHoldingItem extends Item
{
	public BagOfHoldingItem()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (!worldIn.isClientSide)
			NetworkHooks.openGui((ServerPlayerEntity) playerIn, new BagOfHoldingContainerProvider(), playerIn.blockPosition());
		return super.use(worldIn, playerIn, handIn);
	}
}
