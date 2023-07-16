package main.jython.rickaym.pyminecraft;


import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

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
    private Class<?> modClass;

    /**
     * Puts the appropriate mod instantiation method `constructMod` on the activity
     * map. It is called somewhere deep in the fml accordingly.
     *
     * @param info      IModInfo is an interface with getters and setters that fetches corresponding mod data, this surmises a mod
     */
    PyModContainer (final IModInfo info, String className, final ModFileScanData scanResults, final ModuleLayer gameLayer) {
        // Calls the ModContainer constructor, this will do the job of registering the modId,
        // reserve a name space and instantiate the mod loading stage
        super(info);

        activityMap.put(ModLoadingStage.CONSTRUCT, this::constructMod);
        //this.eventBus = BusBuilder.builder().setExceptionHandler(this::onEventFailed).setTrackPahses(false).markerType(IModBusEvent.class).build();
        PyModLoader.loadMod();
    }

    private void constructMod() {
        this.modInstance = PyModLoader.loadMod("");
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
