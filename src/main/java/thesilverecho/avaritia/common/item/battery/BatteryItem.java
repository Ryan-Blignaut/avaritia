package thesilverecho.avaritia.common.item.battery;

import net.minecraft.item.Item;
import thesilverecho.avaritia.common.item.ModGroup;

public class BatteryItem extends Item
{
	public BatteryItem()
	{
		super(new Properties().stacksTo(1).tab(ModGroup.AVARITIA));
	}


}
