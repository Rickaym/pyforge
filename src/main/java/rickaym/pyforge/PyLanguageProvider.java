package rickaym.pyforge;

import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
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
        return "pyforge";
    }

    public static class PyModTarget implements IModLanguageProvider.IModLanguageLoader {
        private final String entryClass;
        private final String modId;

        private PyModTarget(String entryClass, String modId) {
            this.entryClass = entryClass;
            this.modId = modId;
        }

        public String getModId() {
            return modId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T loadMod(final IModInfo info, final ClassLoader modClassLoader, final ModFileScanData modFileScanResults) {
            try {
                LOGGER.debug(LOADING, "Loading mod '{}' at entry class '{}'", info.getModId(), entryClass);
                final Class<?> pyContainer = Class.forName("rickaym.pyforge.PyModContainer", true,
                        Thread.currentThread()
                                .getContextClassLoader());
                final Constructor<?> constructor;
                constructor = pyContainer.getConstructor(IModInfo.class, String.class);
                T modContainer = (T) constructor.newInstance(info, entryClass);
                LOGGER.debug(LOADING, "Loaded mod container {}", modContainer);
                return modContainer;
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Consumer<ModFileScanData> getFileVisitor() {
        return scanResult -> {
            Map<String, PyModTarget> modTargetMap = scanResult.getIModInfoData()
                    .stream()
                    .map((IModFileInfo infoData) -> ((ModInfo) infoData.getMods()
                            .get(0)))
                    .map(ad -> new PyModTarget((String) ad.getConfigElement("entryClass")
                            .orElse(null), ad.getModId()))
                    .collect(Collectors.toMap(PyModTarget::getModId, Function.identity()));
            LOGGER.debug(LOADING, "Adding into language loader mod target map {}", modTargetMap);
            scanResult.addLanguageLoader(modTargetMap);
        };
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {
    }
}
