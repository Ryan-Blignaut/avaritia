package thesilverecho.avaritia.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import thesilverecho.avaritia.common.intergration.curios.CuriosCompat;

import javax.annotation.Nullable;
import java.util.List;

public class AngleRing extends Item
{
	public AngleRing()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new TranslationTextComponent("tooltip.avaritia.angle_ring"));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused)
	{
		if (ModList.get().isLoaded("curios"))
			return CuriosCompat.initCapabilities();
		return super.initCapabilities(stack, unused);
	}

}
