package rickaym.pyminecraft;

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
        private final PyModLoader loader = new PyModLoader();

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
//            PyModLoader loader = new PyModLoader();
//            loader.findEntryFiles("examplemod").findModEntryFile();
//            Map<String, PyLanguageProvider.PyModTarget> modTargetMap = new HashMap<>();
//            modTargetMap.put("examplemod", new PyLanguageProvider.PyModTarget("examplemod", "examplemod"));
//            scanResult.addLanguageLoader(modTargetMap);
        };
//
//        return (scanResult) -> {
//            Map<String, FMLJavaModLanguageProvider.FMLModTarget> modTargetMap = (Map)scanResult.getAnnotations().stream().filter((ad) -> {
//                return ad.getAnnotationType().equals(MODANNOTATION);
//            }).peek((ad) -> {
//                LOGGER.debug(Logging.SCAN, "Found @Mod class {} with id {}", ad.getClassType().getClassName(), ad.getAnnotationData().get("value"));
//            }).map((ad) -> {
//                return new FMLJavaModLanguageProvider.FMLModTarget(ad.getClassType().getClassName(), (String)ad.getAnnotationData().get("value"));
//            }).collect(Collectors.toMap(FMLJavaModLanguageProvider.FMLModTarget::getModId, Function.identity(), (a, b) -> {
//                return a;
//            }));
//            scanResult.addLanguageLoader(modTargetMap);
//        };
    }

    @Override
    public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> supplier) {
    }
}
