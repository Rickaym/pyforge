package rickaym.pyminecraft;

import net.minecraftforge.eventbus.EventBusErrorMessage;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.IEventListener;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingException;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.Logging.LOADING;

import java.nio.file.Paths;

/**
 * <strong>Currently holds no true implementation.</strong>
 * <br><br>
 * Extending the abstract class ModContainer to make a mod wrapper that comforts interactions with
 * the mod loading service and the overall system contact and management.
 * <br><br>
 * <p>
 * Check the {@link ModContainer} class to understand more about what this class implements.
 * <br><br>
 *
 * @see IModInfo
 * @see ModFileScanData
 */
public class PyModContainer extends ModContainer {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Integrated @Mod Java instance
     **/
    private WrappedModInstance modInstance;
    private String entryClass;
    private IModInfo modInfo;
    public IEventBus eventBus;

    /**
     * Puts the appropriate mod instantiation method `constructMod` on the activity
     * map. It is called somewhere deep in the fml accordingly.
     *
     * @param info IModInfo is an interface with getters and setters that fetches corresponding mod data, this surmises a mod
     */
    public PyModContainer(IModInfo info, String entryClass) {
        // Calls the ModContainer constructor, this will do the job of registering the modId,
        // reserve a name space and instantiate the mod loading stage
        super(info);
        this.modInfo = info;
        this.entryClass = entryClass;
        PyModLoadingContext ctx = new PyModLoadingContext(this);
        this.contextExtension = () -> ctx;
        LOGGER.debug(LOADING, "Creating PyModContainer instance.");
        eventBus = BusBuilder.builder()
                .setExceptionHandler(this::onEventFailed)
                .setTrackPhases(false)
                .build();
        activityMap.put(ModLoadingStage.CONSTRUCT, this::constructMod);
    }

    private void onEventFailed(IEventBus eventBus, Event event, IEventListener[] listeners, int busId, Throwable throwable) {
        LOGGER.error(new EventBusErrorMessage(event, busId, listeners, throwable));
    }

    private void constructMod() {
        LOGGER.debug(LOADING, "Constructing mod from entry class {}", entryClass);
        modInstance = PyModLoader.loadMod(Paths.get("../src")
                .toAbsolutePath()
                .normalize()
                .toString(), entryClass);
        LOGGER.debug(LOADING, "Mod constructed {}", modInstance);
    }

    /**
     * Does this mod match the supplied mod?
     *
     * @param mod to compare
     * @return if the mod matches
     */
    @Override
    public boolean matches(Object mod) {
        return mod == modInstance;
    }

    /**
     * @return the mod object instance
     */
    @Override
    public Object getMod() {
        return modInstance;
    }

    @Override
    public <T extends Event & IModBusEvent> void acceptEvent(T e) {
        try {
            LOGGER.trace("Firing event for modid $modId : $e");
            eventBus.post(e);
            LOGGER.trace("Fired event for modid $modId : $e");
        } catch (Throwable t) {
            LOGGER.error("Caught exception during event $e dispatch for modid $modId", t);
            throw new ModLoadingException(modInfo, modLoadingStage, "fml.modloading.errorduringevent", t);
        }
    }
}
