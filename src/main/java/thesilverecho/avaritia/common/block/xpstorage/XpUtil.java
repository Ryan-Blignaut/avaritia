package thesilverecho.avaritia.common.block.xpstorage;

import net.minecraft.entity.player.PlayerEntity;

public class XpUtil
{
	public static int getExperienceForNextLevel(int currentLevel)
	{
		if (currentLevel < 16)
		{
			return 2 * currentLevel + 7;
		} else if (currentLevel < 31)
		{
			return 5 * currentLevel - 38;
		} else
		{
			return 9 * currentLevel - 158;
		}
	}

	public static int getLevelForExperience(double exp)
	{
		if (exp <= 352)
		{
			return (int) Math.floor(solveParabola(1, 6, -exp));
		} else if (exp <= 1507)
		{
			return (int) Math.floor(solveParabola(2.5, -40.5, 360 - exp));
		} else
		{
			return (int) Math.floor(solveParabola(4.5, -162.5, 2220 - exp));
		}
	}

	public static double solveParabola(double a, double b, double c)
	{
		return (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
	}

	public static void addPlayerXP(PlayerEntity player, int amount)
	{
		int experience = Math.max(0, getPlayerXP(player) + amount);
		player.totalExperience = experience;
		player.experienceLevel = getLevelForExperience(experience);
		int expForLevel = getExperienceForLevel(player.experienceLevel);
		player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
	}


	public static int getExperienceForLevel(int level)
	{
		if (level >= 21863)
		{
			return Integer.MAX_VALUE;
		}
		if (level == 0)
		{
			return 0;
		}
		int res = 0;
		for (int i = 0; i < level; i++)
		{
			res += getExperienceForNextLevel(i);
		}
		return res;
	}

	public static int getPlayerXP(PlayerEntity player)
	{
		return (int) (getExperienceForLevel(player.experienceLevel) + (player.experienceProgress * player.getXpNeededForNextLevel()));
	}
}
