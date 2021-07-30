package thesilverecho.avaritia.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import thesilverecho.avaritia.common.init.ModEntities;

public class Ticker extends Entity
{

	private static final DataParameter<Integer> TIME_RATE = EntityDataManager.defineId(Ticker.class, DataSerializers.INT);
	private int timeLeft;
	private BlockPos target;

	public Ticker(EntityType<?> entityTypeIn, World worldIn)
	{
		super(entityTypeIn, worldIn);
	}

	public Ticker(World worldIn)
	{
		this(ModEntities.TICKER.get(), worldIn);
		this.noPhysics = true;
	}


	public Ticker(World worldIn, BlockPos target, double posX, double posY, double posZ)
	{
		this(worldIn);
		this.target = target;
		this.setPos(posX, posY, posZ);
	}

	public Ticker(World worldIn, BlockPos target)
	{
		this(worldIn);
		this.target = target;
		this.setPos(target.getX(), target.getY(), target.getZ());
	}

	public Ticker(FMLPlayMessages.SpawnEntity spawnEntity, World world)
	{
//		spawnEntity.
		this(ModEntities.TICKER.get(), world);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (this.level.isClientSide)
			return;

		TileEntity tileEntity = this.level.getBlockEntity(target);
		for (int i = 0; i < entityData.get(TIME_RATE); i++)
			if (tileEntity instanceof ITickableTileEntity)
				((ITickableTileEntity) tileEntity).tick();

		timeLeft -= 1;
		if (timeLeft == 0)
			remove();
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance)
	{
		return super.shouldRenderAtSqrDistance(distance);
	}


	@Override
	protected void defineSynchedData()
	{
		entityData.define(TIME_RATE, 1);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT tag)
	{
		target = BlockPos.CODEC.parse(NBTDynamicOps.INSTANCE, tag.get("target")).get().orThrow();
		timeLeft = tag.getInt("time_left");
		setTimeRate(tag.getInt("time_rate"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT tag)
	{
		INBT nbt = BlockPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, target).get().orThrow();
		tag.put("target", nbt);
		tag.putInt("time_left", timeLeft);
		tag.putInt("time_rate", getTimeRate());
	}

	public int getTimeLeft()
	{
		return timeLeft;
	}

	public Ticker setTimeLeft(int timeLeft)
	{
		this.timeLeft = timeLeft;
		return this;
	}

	public int getTimeRate()
	{
		return entityData.get(TIME_RATE);
	}

	public Ticker setTimeRate(int in)
	{
		entityData.set(TIME_RATE, in);
		return this;
	}

	@Override
	public IPacket<?> getAddEntityPacket()
	{

		return NetworkHooks.getEntitySpawningPacket(this);
	}

//	@Override
//	public void writeSpawnData(PacketBuffer buffer)
//	{
//
//	}
//
//	@Override
//	public void readSpawnData(PacketBuffer additionalData)
//	{
//
//	}
}
