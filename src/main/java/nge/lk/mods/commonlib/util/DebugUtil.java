package nge.lk.mods.commonlib.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Contains debugging utilities.
 */
public class DebugUtil {

    /**
     * The logger where debug messages are logged to.
     */
    private static Logger log;

    /**
     * The ID of the mod using the library.
     *
     * @param modId The ID of the mod.
     */
    public static void initializeLogger(final String modId) {
        log = LogManager.getLogger(modId);
    }

    /**
     * Dumps the fields of the given classes.
     *
     * @param classes The classes to dump the fields of.
     */
    public static void dumpFields(final Class<?>... classes) {
        for (final Class<?> clazz : classes) {
            warn("-----------------------");
            warn("-----[CLASS DUMP of " + clazz.getSimpleName() + "]------");
            warn("-----------------------");

            final Field[] fields = clazz.getDeclaredFields();
            int slot = 0;
            for (final Field field : fields) {
                warn("[" + (slot++) + "] " + field);
            }
        }

        // Kill the JVM.
        FMLCommonHandler.instance().exitJava(0, true);
    }

    /**
     * Log a recoverable error.
     *
     * @param error The exception that caused the error.
     */
    public static void recoverableError(final Throwable error) {
        log.error(error.toString(), error);
    }

    /**
     * Logs a warning.
     *
     * @param msg The message to log.
     */
    private static void warn(final String msg) {
        log.log(Level.WARN, msg);
    }

    /**
     * Private constructor to prevent instance creation.
     */
    private DebugUtil() {
    }
}
