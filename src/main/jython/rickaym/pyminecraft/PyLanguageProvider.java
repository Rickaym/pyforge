package rickaym.pyminecraft;

import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.Logging.LOADING;


public class PyLanguageProvider implements IModLanguageProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String name() {
        return "pyminecraft";
    }

    public static class PyModTarget implements IModLanguageProvider.IModLanguageLoader {
        private final String className;
        private final String modId;
        private final PyModLoader loader = new PyModLoader();

        private PyModTarget(String entryClass, String modId) {
            this.className = entryClass;
            this.modId = modId;
        }

        public String getModId() {
            return modId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T loadMod(final IModInfo info, final ClassLoader modClassLoader, final ModFileScanData modFileScanResults) {
            try {
                LOGGER.debug(LOADING, "Loading pyminecraft language mod {}", info.getModId());
                final Class<?> pyContainer = Class.forName("rickaym.pyminecraft.PyModContainer", true,
                        Thread.currentThread()
                                .getContextClassLoader());
                final Constructor<?> constructor;
                constructor = pyContainer.getConstructor(IModInfo.class, String.class, ModFileScanData.class,
                        ModuleLayer.class);

                return (T) constructor.newInstance(info, className, modFileScanResults, ModuleLayer.boot());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Consumer<ModFileScanData> getFileVisitor() {
        // probably the class which fetches the @Mod class and adds it to the scanResult
        return scanResult -> {
            Map<String, PyModTarget> modTargetMap = scanResult.getIModInfoData()
                    .stream()
                    .map((IModFileInfo infoData) -> ((ModInfo) infoData.getMods()
                            .get(0)))
                    .map(ad -> new PyModTarget((String) ad.getConfigElement("entryClass").orElse(null), ad.getModId()))
                    .collect(Collectors.toMap(PyModTarget::getModId, Function.identity()));
            scanResult.addLanguageLoader(modTargetMap);
        };
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {
    }
}
