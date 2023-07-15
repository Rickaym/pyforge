package main.jython.rickaym.pyminecraft;

import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PyLanguageProvider implements IModLanguageProvider {
    @Override
    public String name() {
        return "pyminecraft";
    }

    public static class PyModTarget implements IModLanguageProvider.IModLanguageLoader {
        private final String className;
        private final String modId;

        private PyModTarget(String className, String modId) {
            this.className = className;
            this.modId = modId;
        }

        public String getModId() {
            return modId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T loadMod(final IModInfo info, final ClassLoader modClassLoader, final ModFileScanData modFileScanResults) {
            try {

                final Class<?> pyContainer = Class.forName("main.jython.rickaym.pyminecraft.PyModContainer", true,
                        Thread.currentThread()
                                .getContextClassLoader());
                final Constructor<?> constructor;
                constructor = pyContainer.getConstructor(IModInfo.class, String.class, ModFileScanData.class,
                        ModFileScanData.class);

                return (T) constructor.newInstance(info, className, modFileScanResults, modFileScanResults);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Consumer<ModFileScanData> getFileVisitor() {
        // probably the class which fetches the @Mod class and adds it to the scanResult
        return scanData -> {
        };
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {
    }
}
