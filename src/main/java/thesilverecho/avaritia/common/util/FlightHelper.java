package thesilverecho.avaritia.common.util;

import net.minecraft.entity.player.PlayerEntity;

public class FlightHelper
{
	public static void startFlying(PlayerEntity player)
	{
		if (!player.isCreative() && !player.isSpectator())
		{
			player.abilities.mayfly = true;
			player.onUpdateAbilities();
		}
	}

	public static void stopFlying(PlayerEntity player)
	{
		if (!player.isCreative() && !player.isSpectator())
		{
			player.abilities.flying = false;
			player.abilities.mayfly = false;
			player.onUpdateAbilities();
		}
	}
}
