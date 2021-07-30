package thesilverecho.avaritia.common.block.xpstorage;

public interface IXpStorage
{

	int addXp(int amount, boolean simulate);

	int removeXp(int amount, boolean simulate);

	int getXpStored();


}
