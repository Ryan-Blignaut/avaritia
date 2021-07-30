/*
package thesilverecho.avaritia.client.model.baked;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BakedWrapper implements IBakedModel
{
	private final TextureAtlasSprite particleTexture;
	private final ItemOverrideList overrideList;

	@SuppressWarnings("ConstructorWithTooManyParameters")
	public BakedWrapper(LayeredModel<?> model, IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation, ItemOverrideList overrideList)
	{
		this.particleTexture = spriteGetter.apply(owner.resolveTexture("particle"));
		this.overrideList = overrideList;
	}


	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return false;
	}

	@Override
	public boolean isSideLit()
	{
		return false;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return particleTexture;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return overrideList;
	}
}
*/
