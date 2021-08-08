package main.jython.rickaym.minecraftpy;


import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import javax.management.AttributeNotFoundException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PyLanguageProvider implements IModLanguageProvider {
    private String className;
    private String modId;

    private void modTarget(String className, String modId) {
        this.className = className;
        this.modId = modId;
    }

    public String getModId() { return modId; }

    /**
     * This language class is loaded in the system level classloader - before the game even starts
     * So we must treat container construction as an arms length operation, and load the container
     * in the classloader of the game - the context classloader is appropriate here.
     *
     * @param info
     * @param modClassLoader
     * @param modFileScanResults
     * @param <T>
     * @return a {@link PyModContainer} instance
     */
    public <T> T loadMod(final IModInfo info, final ClassLoader modClassLoader, final ModFileScanData modFileScanResults) throws AttributeNotFoundException {
        return (T) new PyModContainer(info, className, modClassLoader, modFileScanResults);
    }

    @Override
    public String name() {
        return "minecraft.py";
    }

    @Override
    public Consumer<ModFileScanData> getFileVisitor() {
        // probably the class which fetches the @Mod class and adds it to the scanResult
        return null;
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {

    }
}
