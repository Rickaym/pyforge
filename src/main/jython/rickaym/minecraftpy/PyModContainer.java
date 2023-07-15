package main.jython.rickaym.minecraftpy;


import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.python.core.PyClass;
import org.python.core.PyObject;

/**
 * <strong>Currently holds no true implementation.</strong>
 * <br><br>
 * Extending the abstract class ModContainer to make a mod wrapper that comforts interactions with
 * the mod loading service and the overall system contact and management.
 * <br><br>
 *
 * Check the {@link ModContainer} class to understand more about what this class implements.
 *<br><br>
 * @see IModInfo
 * @see ModFileScanData
 */
public class PyModContainer extends ModContainer {

    /** Integrated @Mod Java instance **/
    private IPyModClass modInstance;

    /**
     * Puts the appropriate mod instantiation method `constructMod` on the activity
     * map. It is called somewhere deep in the fml accordingly.
     *
     * @param info      IModInfo is an interface with getters and setters that fetches corresponding mod data, this surmises a mod
     */
    PyModContainer (final IModInfo info, String className, final ClassLoader loader, final ModFileScanData scandata) {
        // Calls the ModContainer constructor, this will do the job of registering the modId,
        // reserve a name space and instantiate the mod loading stage
        super(info);
        activityMap.put(ModLoadingStage.CONSTRUCT, this::constructMod);
        //this.eventBus = BusBuilder.builder().setExceptionHandler(this::onEventFailed).setTrackPahses(false).markerType(IModBusEvent.class).build();
    }

    private void constructMod() throws ClassNotFoundException {
        this.modInstance = PyClassLoader.loads();
        if (this.modInstance == null) {
            throw new ClassNotFoundException("jython.rickaym.minecraftpy could not find the class");
        }
        System.out.format("Loaded mod instance with name %s", this.modInstance.toString());
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
}
