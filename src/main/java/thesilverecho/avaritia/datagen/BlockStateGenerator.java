package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModBlocks;

public class BlockStateGenerator extends BlockStateProvider
{
	public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, Avaritia.MOD_ID, exFileHelper);
	}


	@Override
	protected void registerStatesAndModels()
	{

//		cubeAll(ModBlocks.XP_STORAGE_BLOCK.get());
		simpleBlock(ModBlocks.XP_STORAGE_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/xp")));
		simpleBlock(ModBlocks.MODIFIER_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/modifier")));
//		horizontalBlock(ModBlocks.Rune.);
//		models().withExistingParent("block/capacitor", modLoc("block/capacitor")).texture("block/capacitor", modLoc("models/capacitor"))
//		simpleBlock(ModBlocks.UPGRADE_BENCH_BLOCK.get(),models().cubeAll("test",modLoc("block/bench")));
//		simpleBlock(ModBlocks.COLOURED_BLOCK.get(),models().cubeAll("col",modLoc("block/bench")));

		//		models().withExistingParent("xp", new ResourceLocation(Avaritia.MOD_ID, "block/xp"));

	}
}
