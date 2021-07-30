package thesilverecho.avaritia.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.tile.CapacitorTile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CapacitorBakedModel implements IDynamicBakedModel
{
	//TODO this really looks like it will be extremely difficult to implement,but look at ender io capacitor as it uses Properties and a custom forge model
	public static final FaceBakery COOKER = new FaceBakery();
	public static final ResourceLocation TEXTURE = new ResourceLocation(Avaritia.MOD_ID, "block/capacitor");
	private static final IModelTransform ZERO_SPRITE = new IModelTransform()
	{
		@Override
		public boolean isUvLocked()
		{
			return false;
		}
	};


	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction sideIn, @Nonnull Random rand, @Nonnull IModelData extraData)
	{
		List<BakedQuad> quads = new ArrayList<>();
		Direction[] sides = sideIn == null ? Direction.values() : new Direction[]{sideIn};
		for (Direction side : sides)
			if (side != null)
			{
				World world = extraData.getData(CapacitorTile.WORLD_PROP);
				BlockPos pos = extraData.getData(CapacitorTile.POS_PROP);
				TextureAtlasSprite base = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(TEXTURE);

				if (world == null || pos == null)
					return quads;

				final Block block = world.getBlockState(pos).getBlock();
				boolean west = false, east = false, north = false, south = false, down = false, up = false;
//				if (world.getBlockState(pos.offset(side)).getBlock() == block)
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{2F,0F,14F,2F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(14.5F,14.5F,0), new Vector3f(16F,16F,16), face, base, side, ZERO_SPRITE, /*new BlockPartRotation(new Vector3f(0.5f, 0.5f, 0.5f), side.getAxis(), 90, false)*/null, true, modelName);
//					quads.add(bakedQuad);
//				} else
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{2F,0F,14F,2F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(14.5F,14.5F,1.5F), new Vector3f(16F,16F,14.5F), face, base, side, ZERO_SPRITE, /*new BlockPartRotation(new Vector3f(0.5f, 0.5f, 0.5f), side.getAxis(), 90, false)*/null, true, modelName);
//					quads.add(bakedQuad);
//				}
//				final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{2F,0F,14F,2F}, 4));
//				final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//				final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(14.5F,14.5F,0), new Vector3f(16F,16F,16), face, base, side, ZERO_SPRITE, /*new BlockPartRotation(new Vector3f(0.5f, 0.5f, 0.5f), side.getAxis(), 90, false)*/null, true, modelName);
//				quads.add(bakedQuad);

				if (world.getBlockState(pos.above()).getBlock() != block)
				{
					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(0, 1.5f, 0), new Vector3f(1.5f, 14.5f, 1.5f), face, base, side, ZERO_SPRITE, null, true, modelName);
					quads.add(bakedQuad);
				}

//				for (int i = 0; i < Direction.values().length; i++)
//				{
//					if (world.getBlockState(pos.offset(Direction.byIndex(i))).getBlock() != block)
//					{
//						System.out.println(pos + "\tshould be by " + Direction.byIndex(i));
//						final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
//						final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//						final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(14.5f, 1.5f, 0), new Vector3f(16, 14.5f, 1.5f), face, base, side, ZERO_SPRITE, new BlockPartRotation(new Vector3f(0.5f, 0.5f, 0.5f), Direction.values()[i].getAxis(), 90, false), true, modelName);
//						quads.add(bakedQuad);
//					}
//
//				}
//				if (east = world.getBlockState(pos.east()).getBlock() != block&&world.getBlockState(pos.north()).getBlock() != block)
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(14.5f, 1.5f, 0), new Vector3f(16, 14.5f, 1.5f), face, base, side, ZERO_SPRITE, null, true, modelName);
//					quads.add(bakedQuad);
////
////					final BlockPartFace face1 = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0F, 2F, 2F, 14F}, 4));
////					final BakedQuad bakedQuad1 = COOKER.bakeQuad(new Vector3f(14.5F, 1.5F, 14.5F), new Vector3f(16F, 14.5F, 16F), face1, base, side, ZERO_SPRITE, null, true, modelName);
////					quads.add(bakedQuad1);
//				}
//				if (west = world.getBlockState(pos.west()).getBlock() != block)
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(0, 1.5f, 0), new Vector3f(1.5f, 14.5f, 1.5f), face, base, side, ZERO_SPRITE, null, true, modelName);
//					quads.add(bakedQuad);
//				}
//				if (down = world.getBlockState(pos.down()).getBlock() != block)
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(1.5f, 0, 0), new Vector3f(14.5f, 1.5f, 1.5f), face, base, side, ZERO_SPRITE, null, true, modelName);
//					quads.add(bakedQuad);
//				}
//				if (up = world.getBlockState(pos.up()).getBlock() != block)
//				{
//					final BlockPartFace face = new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 1.0F}, 4));
//					final ResourceLocation modelName = new ModelResourceLocation(Avaritia.MOD_ID, "solar_panel");
//					final BakedQuad bakedQuad = COOKER.bakeQuad(new Vector3f(1.5f, 14.5f, 0), new Vector3f(14.5f, 16f, 1.5f), face, base, side, ZERO_SPRITE, null, true, modelName);
//					quads.add(bakedQuad);
//				}
//				if (north = world.getBlockState(pos.north()).getBlock() != block)
//				{}




			/*	if(west = world.getBlockState(pos.west()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(0, h, 1), new Vector3f(1, h + 0.25F, 15), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(side != Direction.UP ? new float[] { 0, 0, 16, 1 } : new float[] { 0, 0, 1, 16 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(east = world.getBlockState(pos.east()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(15, h, 1), new Vector3f(16, h + 0.25F, 15), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(side != Direction.UP ? new float[] { 0, 0, 16, 1 } : new float[] { 15, 0, 16, 16 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(north = world.getBlockState(pos.north()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(1, h, 0), new Vector3f(15, h + 0.25F, 1), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 0, 0, 16, 1 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(south = world.getBlockState(pos.south()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(1, h, 15), new Vector3f(15, h + 0.25F, 16), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 0, 0, 16, 1 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(west || north || world.getBlockState(pos.west().north()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(0, h, 0), new Vector3f(1, h + 0.25F, 1), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 0, 0, 1, 1 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(east || north || world.getBlockState(pos.east().north()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(15, h, 0), new Vector3f(16, h + 0.25F, 1), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 15, 0, 16, 1 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(south || east || world.getBlockState(pos.south().east()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(15, h, 15), new Vector3f(16, h + 0.25F, 16), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 15, 15, 16, 16 }, 4)), //
							base, side, ZERO_SPRITE, null, true));

				if(west || south || world.getBlockState(pos.west().south()).getBlock() != block)
					quads.add(COOKER.makeBakedQuad( //
							new Vector3f(0, h, 15), new Vector3f(1, h + 0.25F, 16), //
							new BlockPartFace(null, 0, "#0", new BlockFaceUV(new float[] { 0, 15, 1, 16 }, 4)), //
							base, side, ZERO_SPRITE, null, true));*/
			}
		return quads;
	}

	private BakedQuad createQuad(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, TextureAtlasSprite sprite)
	{
		Vector3d normal = v3.subtract(v2).cross(v1.subtract(v2)).normalize();

		int tw = sprite.getWidth();
		int th = sprite.getHeight();

		BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
		builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));
		putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
		putVertex(builder, normal, v2.x, v2.y, v2.z, 0, th, sprite, 1.0f, 1.0f, 1.0f);
		putVertex(builder, normal, v3.x, v3.y, v3.z, tw, th, sprite, 1.0f, 1.0f, 1.0f);
		putVertex(builder, normal, v4.x, v4.y, v4.z, tw, 0, sprite, 1.0f, 1.0f, 1.0f);
		return builder.build();
	}

	private void putVertex(BakedQuadBuilder builder, Vector3d normal,
	                       double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b)
	{

		ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
		for (int j = 0; j < elements.size(); j++)
		{
			VertexFormatElement e = elements.get(j);
			switch (e.getUsage())
			{
				case POSITION:
					builder.put(j, (float) x, (float) y, (float) z, 1.0f);
					break;
				case COLOR:
					builder.put(j, r, g, b, 1.0f);
					break;
				case UV:
					switch (e.getIndex())
					{
						case 0:
							float iu = sprite.getU(u);
							float iv = sprite.getV(v);
							builder.put(j, iu, iv);
							break;
						case 2:
							builder.put(j, (short) 0, (short) 0);
							break;
						default:
							builder.put(j);
							break;
					}
					break;
				case NORMAL:
					builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
					break;
				default:
					builder.put(j);
					break;
			}
		}
	}

	@Override
	public boolean useAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean usesBlockLight()
	{
		return false;
	}

	@Override
	public boolean isCustomRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleIcon()
	{
		return getTexture();
	}

	private TextureAtlasSprite getTexture()
	{
		return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(TEXTURE);
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.EMPTY;
	}

	@Override
	public ItemCameraTransforms getTransforms()
	{
		return ItemCameraTransforms.NO_TRANSFORMS;
	}
}
