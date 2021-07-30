package thesilverecho.avaritia.common.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public interface IItemRightClickable
{
	void onClick(ServerPlayerEntity player, ItemStack stack, int slot);
}
