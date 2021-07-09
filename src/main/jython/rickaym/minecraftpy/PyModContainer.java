package main.jython.rickaym.minecraftpy;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

/**
 * <strong>Currently holds no true implementation.</strong>
 * <br><br>
 * Extending the abstract class ModContainer to make a mod wrapper that comforts interactions with
 * the mod loading service and the overall system contact and management.
 * <br><br>
 * Inherits a lot of useful stuff from the ModContainer. Out of each given abstract methods and fields,
 * these methods are to be implemented.
 * <table border="1">
 *   <tr>
 *     <td> 1. {@link ModContainer#matches}</td>
 *   </tr>
 *   <tr>
 *     <td> 2. {@link ModContainer#getMod}</td>
 *   </tr>
 * </table>
 *
 * Check the {@link ModContainer} class to understand more about what this class implements.
 *<br><br>
 * @see IModInfo
 * @see ModFileScanData
 */
public class PyModContainer extends ModContainer{

    /**
     * PyModContainer to.. presumably contain mods :)
     * More seriously.. it acts as a wrapper for mod files.
     *
     * @param info      IModInfo is an interface with getters and setters that fetches corresponding mod data, this surmises a mod
     */
    PyModContainer (final IModInfo info, String className, final ClassLoader loader, final ModFileScanData scandata) {
        // Calls the ModContainer constructor, this will do the job of registering the modId,
        // reserve a name space and instantiate the mod loading stage
        super(info);
    }

    /**
     * Does this mod match the supplied mod?
     *
     * @param mod to compare
     * @return if the mod matches
     */
    @Override
    public boolean matches(Object mod) {return mod.getModId().equals(this.getModId());}

    /**
     * @return the mod object instance
     */
    @Override
    public Object getMod() {return new Object();}

}
