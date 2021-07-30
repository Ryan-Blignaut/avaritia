package thesilverecho.avaritia.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.config.CommonManager;

public class AvaritiaLogger
{
	private static final Logger LOGGER = LogManager.getLogger(Avaritia.MOD_ID);

	public static void logError(String error)
	{
		LOGGER.error(error);
	}

	public static void logError(String error, Object o)
	{
		LOGGER.error(error, o);
	}

	public static void logInfo(String error, Object... objects)
	{
		if (CommonManager.CLIENT.logInfo.get())
			LOGGER.info(error, objects);
	}

}
