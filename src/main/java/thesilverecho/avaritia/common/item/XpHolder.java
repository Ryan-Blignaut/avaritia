package thesilverecho.avaritia.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class XpHolder extends Item
{
	private static final String XP_COUNTER_TAG = "xp_count";
	private double cachedPoints = -1;
	private double cachedLevel = -1;
	public XpHolder()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		if (!stack.hasTag() || !Objects.requireNonNull(stack.getTag()).contains(XP_COUNTER_TAG))
			return;
		final double actualPoints = stack.getTag().getDouble(XP_COUNTER_TAG);

		if (cachedPoints != actualPoints)
		{
			cachedLevel = calculateXPLevel(actualPoints);
			cachedPoints = actualPoints;
		}

		tooltip.add(new TranslationTextComponent("tooltip.avaritia.xp_holder", cachedLevel));
		super.appendHoverText(stack, world, tooltip, flag);
	}

	private double calculateXPLevel(double level)
	{
//		these value were used from the wiki and calculated manually
		if (level <= 352)
		{
//			method 1 --> 1 x level^2 + 6 × level (at levels 0–16)
			return solveQuadratic(1, 6, -level);

		} else if (level <= 1507)
		{
//			method 2 --> 2.5 × level^2 - 40.5 × level + 360 (at levels 17–31)
			return solveQuadratic(2.5, -40.5, 360 - level);
		} else
		{
//			method 3 --> 4.5 × level^2 - 162.5 × level + 2220 (at levels 32+)
			return solveQuadratic(4.5, -162.5, 2220 - level);
		}
	}

	/*
	This method is will solve a quadratic equation(x = (-b+-root(b^2-4ac))/2a, derived from ax^2+bx+c=0)
	We dont need the - root as it is not applicable in this scenario(level will never be 0)
	*/
	private double solveQuadratic(double a, double b, double c)
	{
		return (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
	}


}
