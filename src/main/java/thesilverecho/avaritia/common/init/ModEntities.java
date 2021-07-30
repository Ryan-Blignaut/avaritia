package thesilverecho.avaritia.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import thesilverecho.avaritia.common.entity.Ticker;

public class ModEntities
{

	public static final RegistryObject<EntityType<Ticker>> TICKER = register("ticker", Ticker::new);


	static void register()
	{
	}

	public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.IFactory<T> factory)
	{
		return ModRegistry.ENTITIES.register(name, () -> EntityType.Builder.of(factory, EntityClassification.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> (T) new Ticker(spawnEntity, world)).clientTrackingRange(4).updateInterval(20).build(name));
	}
}
