package thesilverecho.avaritia.common.item.module.filter;

public enum FilterType
{
	SMALL(1), MEDIUM(9), LARGE(18), GIGANTIC(27);

	int size;

	FilterType(int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return size;
	}

	public String getName()
	{
		return this.name().toLowerCase();
	}
}
