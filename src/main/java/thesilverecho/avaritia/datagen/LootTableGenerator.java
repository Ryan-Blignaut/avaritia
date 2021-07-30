package thesilverecho.avaritia.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thesilverecho.avaritia.common.init.ModBlocks;
import thesilverecho.avaritia.common.init.ModRegistry;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LootTableGenerator extends LootTableProvider
{

	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
	private final DataGenerator generator;

	public LootTableGenerator(DataGenerator dataGeneratorIn)
	{
		super(dataGeneratorIn);
		this.generator = dataGeneratorIn;
	}

	protected void register(String name, Block block)
	{
		final LootPool.Builder builder = LootPool.lootPool().name(name).setRolls(ConstantRange.exactly(1))
				.add(ItemLootEntry.lootTableItem(block)
						.apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))
						.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
								.copy("storage", "BlockEntityTag.storage", CopyNbt.Action.REPLACE)
								.copy("work", "BlockEntityTag.work", CopyNbt.Action.REPLACE)
								.copy("colour", "BlockEntityTag.colour", CopyNbt.Action.REPLACE)
						)
						.apply(SetContents.setContents()
								.withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
				);
		lootTables.put(block, LootTable.lootTable().withPool(builder));
	}

	@Override
	public void run(@Nonnull DirectoryCache cache)
	{
		addTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet())
		{
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
		}
		writeTables(cache, tables);
	}

	private void addTables()
	{
		register("test", ModBlocks.UPGRADE_BENCH_BLOCK.get());
		register(ModBlocks.MODIFIER_BLOCK.get().getDescriptionId(), ModBlocks.MODIFIER_BLOCK.get());
		ModRegistry.getItems(item -> item instanceof BlockItem).stream().map(item -> ((BlockItem) item)).forEach(blockItem ->
		{
			final TileEntity tileEntity = blockItem.getBlock().createTileEntity(null, null);
			if (tileEntity != null)
			{

				final LootPool.Builder builder = LootPool.lootPool().name(blockItem.getDescriptionId()).setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(blockItem)
								.apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))
								.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
										.copy("storage", "BlockEntityTag.storage", CopyNbt.Action.REPLACE)
										.copy("work", "BlockEntityTag.work", CopyNbt.Action.REPLACE)
										.copy("colour", "BlockEntityTag.colour", CopyNbt.Action.REPLACE)
								)
								.apply(SetContents.setContents()
										.withEntry(DynamicLootEntry.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
						);
				lootTables.put(blockItem.getBlock(), LootTable.lootTable().withPool(builder));

//				tileEntity.getCapability(CapabilityEnergy.ENERGY);
//				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			}
		});
	}

	private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables)
	{
		Path outputFolder = this.generator.getOutputFolder();
		tables.forEach((key, lootTable) ->
		{
			Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try
			{
				IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
			} catch (IOException e)
			{
				LOGGER.error("Couldn't write loot table {}", path, e);
			}
		});
	}


}
