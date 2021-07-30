package thesilverecho.avaritia.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class CapacitorModelLoader implements IModelLoader<CapacitorModelGeometry>
{

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{

	}

	@Override
	public CapacitorModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
	{
		return new CapacitorModelGeometry();
	}
}
