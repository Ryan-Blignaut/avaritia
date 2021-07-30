package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.container.CompactorContainer;
import thesilverecho.avaritia.common.init.ModRecipes;
import thesilverecho.avaritia.common.init.ModTiles;
import thesilverecho.avaritia.common.recipe.CompactorRecipe;

import javax.annotation.Nonnull;

public class CompactorTile extends BasicItemStorageTile implements ITickableTileEntity, INamedContainerProvider
{
	private final ItemStackHandler itemStackHandler = new ItemStackHandler(2)
	{
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return true/*slot == 0*/;
		}

		@Override
		protected void onContentsChanged(int slot)
		{
			setChanged();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			return slot == 1 ? stack : super.insertItem(slot, stack, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			final ItemStack stack = this.stacks.get(slot);
			return slot == 0 ? stack : super.extractItem(slot, amount, simulate);
		}
	};
	private CompactorRecipe recipe;
	private int materialCount;
	private int workTime;
	private ItemStack materialStack = ItemStack.EMPTY;

	public CompactorTile()
	{
		super(ModTiles.COMPACTOR_TILE.get());
	}

	@Override
	public ItemStackHandler getInventory()
	{
		return itemStackHandler;
	}

	@Override
	public void tick()
	{
		if (level == null)
			return;
		final ItemStack inputStack = getInventory().getStackInSlot(0);
		if (this.recipe == null || !this.recipe.matches(getInventory()))
		{
			this.recipe = level.getRecipeManager().getRecipeFor(ModRecipes.COMPACTOR_TYPE, new Inventory(inputStack), level).orElse(null);
		}
		if (!level.isClientSide())
		{
			if (!inputStack.isEmpty())
			{
				if (this.materialStack.isEmpty() || this.materialCount <= 0)
				{
					this.materialStack = inputStack.copy();
					setChanged();
				}
				if (this.recipe != null && this.materialCount < this.recipe.getInputCount())
					consume(inputStack);
				if (this.recipe != null && materialCount >= recipe.getInputCount())
					if (workTime < 1000)
						this.workTime++;
					else
					{
						final ItemStack recipeOutput = recipe.getResultItem();
						final ItemStack stackInSlot = getInventory().getStackInSlot(1);
						if (stackInSlot.getCount() < 64)
						{
							if (recipeOutput.sameItem(stackInSlot) && ItemStack.tagMatches(stackInSlot, recipeOutput))
							{
								recipeOutput.grow(1);
							}
							getInventory().setStackInSlot(1, recipeOutput);
							workTime = 0;
							this.materialCount -= recipeOutput.getCount();
						}
					}

			}
		}

	}

	private void consume(ItemStack stack)
	{
		if (stack.getItem() == materialStack.getItem())
		{
			stack.shrink(1);
			materialCount++;
			setChanged();
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		final CompoundNBT nbt = super.save(tag);
		nbt.putInt("material_amount", materialCount);
		nbt.putInt("work", workTime);
		nbt.put("material", materialStack.serializeNBT());
		return nbt;
	}

	@Override
	public void load(@Nonnull BlockState state, CompoundNBT tag)
	{
		super.load(state, tag);
		materialCount = tag.getInt("material_amount");
		workTime = tag.getInt("work");
		materialStack = ItemStack.of(tag.getCompound("material"));
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.avaritia.compactor");
	}

	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerEntity)
	{
		return new CompactorContainer(id, inventory, this::isWithinUsableDistance, getInventory());
	}


}
