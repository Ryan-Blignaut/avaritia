package thesilverecho.avaritia.common.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.container.ExtremeCraftingTableContainer;
import thesilverecho.avaritia.common.init.ModTiles;
import thesilverecho.avaritia.common.recipe.CompactorRecipe;

public class ExtremeCraftingTableTile extends BasicItemStorageTile implements INamedContainerProvider
{
	private final ItemStackHandler handler = new ItemStackHandler(81)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			setChanged();
		}
	};
	private CompactorRecipe recipe;


	public ExtremeCraftingTableTile()
	{
		super(ModTiles.EXTREME_CRAFTING_TABLE_TILE.get());
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("extreme");
	}

	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerEntity)
	{
		return new ExtremeCraftingTableContainer(id, inventory, this::isWithinUsableDistance, getInventory());
	}

	@Override
	public ItemStackHandler getInventory()
	{
		return handler;
	}
}
