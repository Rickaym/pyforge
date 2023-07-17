package rickaym.pyminecraft;

import org.python.core.*;
import org.python.jline.internal.Nullable;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Intermediary module loader for the mod classes and extension classes.
 * <p>
 * Takes the heavy lifting aspects in Java-Python (Jython) integration including the
 * scanning for mod files and initializing said modules. This is furthermore explained in the examples and code.
 *
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">Examples</a>
 */
public class PyModLoader {
    private static final String INIT_FILE_NAME = "__init__.py";
    private static final String MOD_GETTER_FUNCTION = "getModClass";
    private static final String IMPORT_FUNCTION = "__import__";
    private static List<Path> entryFiles;
    private static String modEntryFilePath;
    private static final PythonInterpreter jyInterpreter = new PythonInterpreter();
    private PyInstance modInstance;

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
                        System.out.format("Found the mod entry file '%s'.\n", entryFilePath);
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
        System.out.format("Finding the mod class supplier function in %s.\n", modEntryFilePath);
        String moduleName = descPath[descPath.length - 2];

//  PYSYSTEMSTATE APPROACH - NOT WORKING
//        PySystemState sys = new PySystemState();
//        sys.path.append(Py.newString(sourceDir.replace("\\", "\\\\")));
//        // Print the Python path after adding the directory
//        PyObject importer = sys.getBuiltins()
//                .__getitem__(Py.newString(IMPORT_FUNCTION));
//        System.out.format("%s importer calling the top level module '%s'.\n", importer, moduleName);
//        module = importer.__call__(new PyString(moduleName));

        jyInterpreter.exec("import sys");
        jyInterpreter.exec(String.format("sys.path.append(\"%s\")", sourceDir));  // replace with actual path
        jyInterpreter.exec(String.format("import %s", moduleName));  // replace with actual module name
        module = jyInterpreter.get(moduleName);
        System.out.println("Loaded module " + module);
        return module;
    }

    /**
     * Initializes the module by importing the module and calling the mod getter function.
     */
    private void initializeMod(String sourceDir) {
        try {
            PyObject modModule = getModModule(sourceDir);
            PyList attrs = (PyList) modModule.__dir__();

            if (attrs.__contains__(Py.newString(MOD_GETTER_FUNCTION))) {
                System.out.println("Fetched the getModClass supplier from the top level entry module.");
                PyObject modSupplier = modModule.__getattr__(MOD_GETTER_FUNCTION);
                // calls on the mod supplier function to create the instance
                modInstance = (PyInstance) modSupplier.__call__();
                System.out.format("Instantiated the mod instance '%s'.\n", modInstance);
                System.out.format("Mod metadata is '%s'\n", modInstance.invoke("__mod_meta__"));
            }
        } catch (PyException e) {
            e.printStackTrace();
            System.err.format("%s is a bad mod entry.\n", modEntryFilePath);
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
        System.out.format("Gathering entry files recursively from the module root directory '%s'.\n", absModuleRoot);
        loader.findEntryFiles(absModuleRoot)
                .findModEntryFile()
                .initializeMod(sourceDir);
        return new WrappedModInstance(loader.modInstance);
    }
}
