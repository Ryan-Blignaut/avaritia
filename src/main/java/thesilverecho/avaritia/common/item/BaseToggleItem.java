package thesilverecho.avaritia.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.util.IToggleable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseToggleItem extends Item implements IToggleable
{
	public BaseToggleItem(Properties properties)
	{
		super(properties);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> use(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn)
	{

		toggle(playerIn, playerIn.getItemInHand(handIn));
		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public boolean isFoil(@Nonnull ItemStack stack)
	{
		return isActive(stack);
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent(isActive(stack) ? "tooltip.avaritia.active" : "tooltip.avaritia.off"));
	}

}
