package thesilverecho.avaritia.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;

public interface IToggleable
{
	String active = "active";

	default void toggle(PlayerEntity player, ItemStack stack)
	{
		CompoundNBT tag = stack.getOrCreateTag();

		if (!tag.contains(active))
			tag.putBoolean(active, false);

		boolean active = tag.getBoolean(IToggleable.active);
		tag.putBoolean(IToggleable.active, !active);

		player.playSound(!active ? SoundEvents.EXPERIENCE_ORB_PICKUP : SoundEvents.STONE_BUTTON_CLICK_OFF, 0.8f, 1);
//		if (!active) player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, 1);
//		else player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, 0.8f, 1);
	}

	default boolean isActive(ItemStack stack)
	{
		return stack.getOrCreateTag().getBoolean(active);
	}

}
