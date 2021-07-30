package thesilverecho.avaritia.common.block.xpstorage;

public class XpStorage implements IXpStorage
{

	protected int xp;

	public XpStorage(int xp)
	{
		this.xp = xp;
	}

	public XpStorage()
	{
		this.xp = 0;
	}


	@Override
	public int addXp(int amount, boolean simulate)
	{
		if (!simulate)
			xp += amount;
		return amount;
	}

	@Override
	public int removeXp(int amount, boolean simulate)
	{
		int xpExtracted = Math.min(xp, amount);
		if (!simulate)
			xp -= xpExtracted;
		return xpExtracted;
	}

	@Override
	public int getXpStored()
	{
//		System.out.println(xp);
		return xp;
	}

}
