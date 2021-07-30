package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import thesilverecho.avaritia.common.block.xpstorage.CapabilityXp;
import thesilverecho.avaritia.common.block.xpstorage.IXpStorage;
import thesilverecho.avaritia.common.block.xpstorage.ModXpStorage;
import thesilverecho.avaritia.common.container.XpStorageContainer;
import thesilverecho.avaritia.common.init.ModTiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class XpStorageTile extends TileEntity implements INamedContainerProvider
{

	private final ModXpStorage xpStorage = createXpStorage();
	final LazyOptional<IXpStorage> xp = LazyOptional.of(() -> xpStorage);

	public XpStorageTile()
	{
		super(ModTiles.XP_STORAGE_TILE.get());
	}

	@Override
	public void setRemoved()
	{
		super.setRemoved();
		xp.invalidate();
	}

	private ModXpStorage createXpStorage()
	{
		return new ModXpStorage()
		{
			@Override
			protected int onXpChange(int i)
			{
				setChanged();
				return i;
			}
		};
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityXp.XP)
			return xp.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public void load(@Nonnull BlockState state, @Nonnull CompoundNBT tag)
	{
		super.load(state, tag);
		if (tag.contains("storage"))
			xpStorage.deserializeNBT(tag.getCompound("storage"));
	}


	@Nonnull
	@Override
	public CompoundNBT save(@Nonnull CompoundNBT tag)
	{

		super.save(tag);
		tag.put("storage", xpStorage.serializeNBT());
		return tag;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("a");
	}

	@Nullable
	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_)
	{
		return new XpStorageContainer(p_createMenu_1_, p_createMenu_2_, p -> true);
	}
}
