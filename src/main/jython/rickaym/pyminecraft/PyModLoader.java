package main.jython.rickaym.pyminecraft;


import org.python.core.*;
import org.python.jline.internal.Nullable;
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
    private static final String ROOT = "../src";
    private static final String INIT_FILE_NAME = "__init__.py";
    private static final String MOD_GETTER_FUNCTION = "getModClass";
    private static final String IMPORT_FUNCTION = "__import__";

    private IPyModClass modInstance;
    private final PySystemState sys = new PySystemState();
    private final PyObject importer = sys.getBuiltins()
            .__getitem__(Py.newString(IMPORT_FUNCTION));
    private static final String SEP = System.getProperty("file.separator");
    public static List<Path> entryFiles;
    public static String modEntryFilePath;

    public PyModLoader findEntryFiles(String rootDir) {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            files = paths
                    .filter(Files::isRegularFile) // Filter out directories, we only want files
                    .filter(path -> path.getFileName().toString().equals(INIT_FILE_NAME)) // Check if file name is __init__.py
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
                BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile().getPath()));
                String line = reader.readLine();

                while (line != null && entryFilePath == null) {
                    if (line.contains(MOD_GETTER_FUNCTION)) {
                        entryFilePath = filePath.toFile().getPath();
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

    /**
     * Initializes the module by importing the module and calling the mod getter function.
     */
    private IPyModClass initializeMod() {
        String[] descPath = modEntryFilePath.replace("\\", "/")
                .split("/");
        System.out.format("Finding the mod class supplier function in %s.\n", modEntryFilePath);
        try {
            PyString moduleName = Py.newString(descPath[descPath.length - 2]);
            PyObject modEntry = importer.__call__(moduleName);
            System.out.format("Finished importing the top level module %s.\n", moduleName);
            PyList attrs = (PyList) modEntry.__dir__();

            if (attrs.__contains__(Py.newString(MOD_GETTER_FUNCTION))) {
                System.out.println("Fetched the getModClass supplier from the top level entry module.");
                // calls the supplier to get the mod class
                PyObject modClass = modEntry.__getattr__(MOD_GETTER_FUNCTION);
                // instantiates the mod class
                PyObject pureModClass = modClass.__call__();
                modInstance = (IPyModClass) pureModClass.__tojava__(IPyModClass.class);
                System.out.format("Instantiated the mod class '%s'.\n", modInstance);
                System.out.format("Mod metadata is '%s'\n", modInstance.__mod_meta__()
                        .toString());
            }
        } catch (PyException e) {
            e.printStackTrace();
            System.err.format("%s is a bad mod entry.\n", modEntryFilePath);
        }
        return modInstance;
    }

    /**
     * @return IPyModClass of Mod Class implementation
     */
    public static IPyModClass loadMod(String moduleName) {
        PyModLoader instance = new PyModLoader();
        String root = PyModLoader.ROOT + SEP + moduleName;
        System.out.format("Gathering entry files recursively from the root directory '%s'.\n", root);
        return instance.findEntryFiles(root).findModEntryFile().initializeMod();
    }
}
