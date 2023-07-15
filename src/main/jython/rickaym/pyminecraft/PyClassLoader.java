package main.jython.rickaym.pyminecraft;


import org.python.core.*;

import java.io.File;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Intermediary module loader for the mod classes and extension classes.
 *
 * Takes the heavy lifting aspects in Java-Python (Jython) integration including the
 * scanning for mod files and initializing said modules. This is furthermore explained in the examples and code.
 *
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">Examples</a>
 */
public class PyClassLoader {
    private static final File root = new File("../src");

    private ArrayList<String> loadedModules = new ArrayList<>();

    private IPyModClass modInstance;
    private final PySystemState sys = new PySystemState();
    private final PyObject importer = sys.getBuiltins().__getitem__(Py.newString("__import__"));

    private static final BiFunction<File, String, String> getFullPath = (root, dir) -> String.format("%s\\%s", root.getPath(), dir);

    /**
     * Iterates through the source root and their subsequent sub directories to extract
     * entry points.
     *
     * This method is recursive.
     */
    private void gatherPyModules(File root) {
        String[] subDirs = root.list();
        if (subDirs != null) {
            for (String subDir : subDirs) {
                if (subDir.equals("__init__.py") &! this.loadedModules.contains(subDir)) {
                    String certainLevelPackage = PyClassLoader.getFullPath.apply(root, subDir);
                    System.out.format("Found %s file to go through.\n", certainLevelPackage);
                    this.initializeModule(certainLevelPackage);
                } else if (!new File(PyClassLoader.getFullPath.apply(root, subDir)).isFile() &! subDir.equals("jython")) {
                    this.gatherPyModules(new File(PyClassLoader.getFullPath.apply(root, subDir)));
                }
            }
        } 
    }

    private void initializeModule(String certainLevelPackage) {
        String[] descPath = certainLevelPackage.replace("\\", "/").split("/");
        System.out.format("Initializing mod classes at %s.\n", certainLevelPackage);
        try {
        PyObject modEntry = importer.__call__(Py.newString(descPath[descPath.length - 2]));
        System.out.println("Finished importing the top level module.");
        PyList attrs = (PyList) modEntry.__dir__();

        System.out.println("Finding the mod class supplier in the module.");
        if (attrs.__contains__(Py.newString("getModClass"))) {
            System.out.println("Fetched the getModClass supplier from the top level entry module.");
            // calls the supplier to get the mod class
            PyObject modClass = modEntry.__getattr__("getModClass");
            // instantiates the mod class
            PyObject pureModClass = modClass.__call__();
            System.out.format("Found mod class %s.\n", pureModClass.__getattr__("__class__"));

            modInstance = (IPyModClass) pureModClass.__tojava__(IPyModClass.class);
            System.out.format("Instantiated the mod class at %s.\n", modInstance);
            System.out.format("Mod-metadata is given as %s\n", modInstance.__mod_meta__().toString());
        }
        }
         catch (PyException e) {
            e.printStackTrace();
            System.err.format("%s is a bad mod entry.\n", certainLevelPackage);
        }
    }
    /**
     * @return IPyModClass of Mod Class implementation
     */
    public static IPyModClass loads() {
        PyClassLoader instance = new PyClassLoader();
        System.out.format("Gathering __init__.py files recursively inside root directory %s.\n", root);
        instance.gatherPyModules(PyClassLoader.root);

        return instance.modInstance;
    }
}
