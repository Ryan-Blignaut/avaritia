package thesilverecho.avaritia.common.item.magnet;

import net.minecraft.item.Item;
import thesilverecho.avaritia.common.item.ModGroup;

public class UpgradeItem extends Item
{
	private final String tag;

	public UpgradeItem(String tag)
	{
		super(new Properties().tab(ModGroup.AVARITIA));
		this.tag = tag;
	}

	public String getTag()
	{
		return tag;
	}
}
