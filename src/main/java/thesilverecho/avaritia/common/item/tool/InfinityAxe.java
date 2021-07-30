package thesilverecho.avaritia.common.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.entity.ImmortalItemEntity;
import thesilverecho.avaritia.common.item.ModGroup;
import thesilverecho.avaritia.common.item.ModTiers;

import javax.annotation.Nonnull;

public class InfinityAxe extends AxeItem
{


	public InfinityAxe()
	{
		super(ModTiers.INFINITY_AXE, 10001, 1.4f, new Properties().tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC));
	}


	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity entity, ItemStack stack)
	{
		return new ImmortalItemEntity(world, entity, stack);
	}

	@Override
	public boolean isFoil(@Nonnull ItemStack stack)
	{
		return false;
	}
}
