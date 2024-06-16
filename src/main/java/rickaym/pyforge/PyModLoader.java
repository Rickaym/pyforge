package rickaym.pyforge;

import org.python.core.*;
import org.python.jline.internal.Nullable;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.Logging.LOADING;

/**
 * Intermediary module loader for mod and extension classes. The PyModLoader is responsible for the heavy lifting
 * required between Java-Python (Jython) conversion, as well as for scanning mod files and initializing modules.
 *
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">Examples</a>
 */
public class PyModLoader {
    private static final String INIT_FILE_NAME = "__init__.py";
    private static final String MOD_GETTER_FUNCTION = "get_mod_instance";
    private static final String IMPORT_FUNCTION = "__import__";
    private static List<Path> entryFiles;
    private static String modEntryFilePath;

    // Ideally, it would be best to load the mod class without instantiating the
    // interpreter, using PySystemState alone, but only the interpreter approach has worked so far.
    // private static PySystemState sys = new PySystemState();
    private static final PythonInterpreter jyInterpreter = new PythonInterpreter();
    private PyInstance modInstance;
    private static final Logger LOGGER = LogManager.getLogger();

    public PyModLoader findEntryFiles(String rootDir) {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            files = paths.filter(Files::isRegularFile) // Filter out directories, we only want files
                    .filter(path -> path.getFileName()
                            .toString()
                            .equals(INIT_FILE_NAME)) // Check if file name is __init__.py
                    .collect(Collectors.toList()); // Collect results to a list
        } catch (IOException e) {
            e.printStackTrace();
        }
        entryFiles = files;
        return this;
    }

    /**
     * Finds the mod entry file and returns the path to it.
     */
    @Nullable
    public PyModLoader findModEntryFile() {
        String entryFilePath = null;
        for (Path filePath : entryFiles) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()
                        .getPath()));
                String line = reader.readLine();

                while (line != null && entryFilePath == null) {
                    if (line.contains(MOD_GETTER_FUNCTION)) {
                        entryFilePath = filePath.toFile()
                                .getPath();
                        LOGGER.debug(LOADING, "Found the mod entry file '{}'.\n", entryFilePath);
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (entryFilePath != null) {
                break;
            }
        }
        modEntryFilePath = entryFilePath;
        return this;
    }

    private PyObject getModModule(String sourceDir) {
        PyObject module;
        String[] descPath = modEntryFilePath.replace("\\", "/")
                .split("/");
        LOGGER.debug(LOADING, "Finding the mod class supplier function in {}.\n", modEntryFilePath);
        String moduleName = descPath[descPath.length - 2];

//        sys.path.append(Py.newString(sourceDir));
//        PyObject importer = sys.getBuiltins()
//                .__getitem__(Py.newString(IMPORT_FUNCTION));
//        System.out.format("%s importer calling the top level module '%s'.\n", importer, moduleName);
//        module = importer.__call__(Py.newString(moduleName));
        jyInterpreter.exec("import sys");
        jyInterpreter.exec(String.format("sys.path.append(\"%s\")", sourceDir));
        jyInterpreter.exec(String.format("import %s", moduleName));
        module = jyInterpreter.get(moduleName);
        LOGGER.debug(LOADING, "Loaded in module '{}'", module);
        return module;
    }

    /**
     * Initializes the module by importing the module and calling the mod getter function.
     */
    private void initializeMod(String sourceDir) {
        PyObject modModule = getModModule(sourceDir);
        PyList attrs = (PyList) modModule.__dir__();

        if (attrs.__contains__(Py.newString(MOD_GETTER_FUNCTION))) {
            LOGGER.debug(LOADING, "Fetched the get_mod_instance supplier from the top level entry module.");
            PyObject modSupplier = modModule.__getattr__(MOD_GETTER_FUNCTION);
            // calls on the mod supplier function to create the instance
            modInstance = (PyInstance) modSupplier.__call__();
            LOGGER.debug(LOADING, "Instantiated the mod instance '{}' with metadata {}.\n", modInstance,
                    modInstance.invoke("__mod_meta__"));
        }
    }

    /**
     * @return IPyModClass of Mod Class implementation
     */
    public static WrappedModInstance loadMod(String sourceDir, String moduleName) {
        PyModLoader loader = new PyModLoader();
        String absModuleRoot = Paths.get(sourceDir, moduleName)
                .toAbsolutePath()
                .toString();
        LOGGER.debug(LOADING, "Gathering entry files recursively from the module root directory '{}'.\n",
                absModuleRoot);
        loader.findEntryFiles(absModuleRoot)
                .findModEntryFile()
                .initializeMod(sourceDir);
        return new WrappedModInstance(loader.modInstance);
    }
}
