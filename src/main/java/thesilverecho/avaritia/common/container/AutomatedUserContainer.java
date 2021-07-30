package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.init.ModContainers;

import java.util.function.Function;

public class AutomatedUserContainer extends BaseContainer
{
	public AutomatedUserContainer(int id, PlayerInventory inventory, Function<PlayerEntity, Boolean> isWithinUsableDistance, ItemStackHandler stackHandler)
	{
		super(ModContainers.AUTOMATED_USER_CONTAINER.get(), id);
		this.isUsableByPlayer = isWithinUsableDistance;
		for (int i = 0; i < 9; i++)
			addSlot(new SlotItemHandler(stackHandler, i, 38 + i * 18, 35));
		layoutPlayerInventorySlots(new InvWrapper(inventory), 8, 84);
	}

	public AutomatedUserContainer(int windowId, PlayerInventory inv)
	{
		this(windowId, inv, p -> false, new ItemStackHandler(9));
	}

}
