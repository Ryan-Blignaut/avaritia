package thesilverecho.avaritia.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class InfinitySwordDamageSource extends DamageSource
{
	public InfinitySwordDamageSource(LivingEntity attacker)
	{
		super("infinity" + attacker);
	}

	@Override
	public ITextComponent getLocalizedDeathMessage(LivingEntity entityLivingBaseIn)
	{
		ItemStack itemstack = entityLivingBaseIn.getItemInHand(Hand.MAIN_HAND);
		int random = entityLivingBaseIn.getCommandSenderWorld().random.nextInt(4) + 1;
		return new TranslationTextComponent("death.avaritia.infinity." + random, entityLivingBaseIn.getDisplayName(), itemstack.getHoverName());
	}

	@Override
	public boolean scalesWithDifficulty()
	{
		return false;
	}
}
