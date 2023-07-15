package main.jython.rickaym.minecraftpy;


import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import javax.management.AttributeNotFoundException;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PyLanguageProvider implements IModLanguageProvider {
    @Override
    public String name() { return "minecraft.py"; }

    public static class PyModTarget implements IModLanguageProvider.IModLanguageLoader {
        private final String className;
        private final String modId;

        private PyModTarget(String className, String modId) {
            this.className = className;
            this.modId = modId;
        }

        public String getModId() { return modId; }

        @Override
        public <T> T loadMod(final IModInfo info, final ClassLoader modClassLoader, final ModFileScanData modFileScanResults) throws AttributeNotFoundException {
            final Class<?> pyContainer = Class.forName("main.jython.rickaym.minecraftpy.PyModContainer", true, Thread.currentThread().getContextClassLoader());
            final Constructor<?> constructor = pyContainer.getConstructor(IModInfo.class, String.class, ClassLoader.class, ModFileScanData.class);
            return (T)constructor.newInstance(info, className, modClassLoader, modFileScanResults);
        }
    }

    @Override
    public Consumer<ModFileScanData> getFileVisitor() {
        // probably the class which fetches the @Mod class and adds it to the scanResult
        return scanData -> {};
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {
    }
}
