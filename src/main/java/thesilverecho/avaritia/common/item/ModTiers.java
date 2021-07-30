package thesilverecho.avaritia.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModTiers implements IItemTier
{

	INFINITY_SWORD(32, 9997, 9999F, -3.0F, 200),
	INFINITY_PICKAXE(32, 9999, 9999F, 6.0F, 200),
	INFINITY_AXE(32, 9999, 9999F, 20, 200);

	private final int durability;
	private final int harvestLevel;
	private final int enchantability;
	private final float efficiency;
	private final float attackDamage;

	ModTiers(int harvestLevel, int durability, float efficiency, float attackDamage, int enchantability)
	{
		this.durability = durability;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
	}

	@Override
	public float getAttackDamageBonus()
	{
		return this.attackDamage;
	}

	@Override
	public float getSpeed()
	{
		return this.efficiency;
	}

	@Override
	public int getEnchantmentValue()
	{
		return this.enchantability;
	}

	@Override
	public int getLevel()
	{
		return this.harvestLevel;
	}

	@Override
	public int getUses()
	{
		return this.durability;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.EMPTY;
	}
}
