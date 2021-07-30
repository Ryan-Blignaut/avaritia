package thesilverecho.avaritia.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import thesilverecho.avaritia.common.container.inventory.ExtremeCraftingTableInventory;
import thesilverecho.avaritia.common.container.slot.ExtremeCraftingTableSlot;
import thesilverecho.avaritia.common.init.ModContainers;
import thesilverecho.avaritia.common.init.ModRecipes;
import thesilverecho.avaritia.common.recipe.ExtremeRecipe;

import java.util.Optional;
import java.util.function.Function;

public class ExtremeCraftingTableContainer extends BaseContainer
{
	private final ItemStackHandler result;
	private final World world;

	public ExtremeCraftingTableContainer(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> usable, ItemStackHandler inv)
	{
		super(ModContainers.EXTREME_CRAFTING_TABLE_CONTAINER.get(), windowId);
		this.isUsableByPlayer = usable;
		this.result = new ItemStackHandler();
		this.world = playerInventory.player.level;
		IInventory matrix = new ExtremeCraftingTableInventory(this, inv);
		addSlot(new ExtremeCraftingTableSlot(result, 0, 206, 89, this, matrix));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				this.addSlot(new Slot(matrix, j + i * 9, 8 + j * 18, 18 + i * 18));
		layoutPlayerInventorySlots(new InvWrapper(playerInventory), 39, 196);
		this.slotsChanged(matrix);
	}

	public ExtremeCraftingTableContainer(int windowId, PlayerInventory inv)
	{
		this(windowId, inv, p -> false, new ItemStackHandler(81));
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			itemstack = slotStack.copy();

//			output slot
			if (index == 0)
			{
				if (!moveItemStackTo(itemstack, 82/*output slot + 81 crafting slots*/, 118/*82 + inventory slots*/, true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(slotStack, itemstack);
			} else if (index >= 82 && index < 118)
			{
				if (!this.moveItemStackTo(slotStack, 1, 82, false))
					if (index < 109)
					{
						if (!this.moveItemStackTo(slotStack, 109, 118, false))
							return ItemStack.EMPTY;
					} else if (!this.moveItemStackTo(slotStack, 82, 109, false))
						return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(slotStack, 82, 118, false))
			{
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) slot.set(ItemStack.EMPTY);
			else slot.setChanged();

			if (slotStack.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

			slot.onTake(playerIn, slotStack);
		}

		return itemstack;
	}


	@Override
	public void slotsChanged(IInventory matrix)
	{
		Optional<ExtremeRecipe> recipe = this.world.getRecipeManager().getRecipeFor(ModRecipes.EXTREME_TYPE, matrix, this.world);
		if (recipe.isPresent())
		{
			ItemStack result = recipe.get().assemble(matrix);
			this.result.setStackInSlot(0, result);
		} else
			this.result.setStackInSlot(0, ItemStack.EMPTY);

		super.slotsChanged(matrix);
	}
}
