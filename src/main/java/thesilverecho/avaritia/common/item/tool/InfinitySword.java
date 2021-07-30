package thesilverecho.avaritia.common.item.tool;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.NonNullList;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.item.ModGroup;
import thesilverecho.avaritia.common.item.ModTiers;
import thesilverecho.avaritia.common.util.InfinitySwordDamageSource;

import javax.annotation.Nonnull;

public class InfinitySword extends SwordItem /*implements ICosmicRender*/
{


	public InfinitySword()
	{
		super(ModTiers.INFINITY_SWORD, 10001, 1.4f, new Properties().tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC)/*.setISTER(CosmicItemRender.SUPPLIER)*/);
	}

	@Override
	public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, LivingEntity attacker)
	{
		if (attacker.level.isClientSide)
			return true;
		target.hurtTime = 60;
		target.getCombatTracker().recordDamage(new InfinitySwordDamageSource(attacker), target.getHealth(), target.getHealth());
		target.setHealth(0);
		target.die(new EntityDamageSource("infinity", attacker));
		return super.hurtEnemy(stack, target, attacker);
	}


	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (group == this.category)
		{
			ItemStack pick = new ItemStack(this);
			pick.enchant(Enchantments.MOB_LOOTING, 10);
			items.add(pick);
		}
		super.fillItemCategory(group, items);
	}
//	@Override
//	public boolean hasCustomEntity(ItemStack stack)
//	{
//		return true;
//	}
//
//	@Override
//	public Entity createEntity(World world, Entity entity, ItemStack stack)
//	{
//		return new ImmortalItemEntity(world, entity, stack);
//	}

	@Override
	public boolean isFoil(@Nonnull ItemStack stack)
	{
		return false;
	}
//
//	@Override
//	public ResourceLocation getOverlayTexture()
//	{
//		return new ResourceLocation(Avaritia.MOD_ID, "item/infinity_sword_overlay");
//	}
}
