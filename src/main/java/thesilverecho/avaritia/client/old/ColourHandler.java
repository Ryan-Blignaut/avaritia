package thesilverecho.avaritia.client.old;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import thesilverecho.avaritia.common.tile.ColouredBlockTile;

import java.util.LinkedList;
import java.util.List;

public class ColourHandler
{
	private static final List<Block> blocks = new LinkedList<>();
	private static final List<Item> items = new LinkedList<>();

	public static void addBlock(Block block)
	{
		blocks.add(block);
	}

	public static void addItem(Item item)
	{
		items.add(item);
	}

	public static void init()
	{
		final BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		final ItemColors itemColors = Minecraft.getInstance().getItemColors();

		blocks.forEach(block -> blockColors.register((state, world, pos, tintIndex) ->
		{
			if (world != null && pos != null)
			{
				final TileEntity tileEntity = world.getBlockEntity(pos);

				if (tileEntity instanceof ColouredBlockTile)
					return ((ColouredBlockTile) tileEntity).getColour();

			}
			return -1;
		}, block));

		blocks.stream().map(Block::asItem).forEach(item -> itemColors.register((stack, tintIndex) ->
		{
			if (stack.getOrCreateTagElement("BlockEntityTag").contains("colour"))
				return stack.getOrCreateTagElement("BlockEntityTag").getInt("colour");

			return -1;
		}, item));

	}
}
