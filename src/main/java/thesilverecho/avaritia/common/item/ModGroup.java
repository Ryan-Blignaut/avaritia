package thesilverecho.avaritia.common.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import thesilverecho.avaritia.common.init.ModItems;

import javax.annotation.Nonnull;

public class ModGroup extends ItemGroup
{
	public static final ModGroup AVARITIA = new ModGroup();

	private ModGroup()
	{
		super("avaritia");
	}

	@Nonnull
	@Override
	public ItemStack makeIcon()
	{
		return new ItemStack(ModItems.INFINITY_SWORD.get());
	}
}
