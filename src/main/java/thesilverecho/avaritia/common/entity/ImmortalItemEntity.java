package thesilverecho.avaritia.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ImmortalItemEntity extends ItemEntity
{

	public ImmortalItemEntity(World worldIn, Entity entity, ItemStack stack)
	{

		super(worldIn, entity.getX(), entity.getY(), entity.getZ(), stack);
	}

	@Override
	public boolean hurt(DamageSource source, float damage)
	{
		return source.getMsgId().equals("outOfWorld");
	}

}
