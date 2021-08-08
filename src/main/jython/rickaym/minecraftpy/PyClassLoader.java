package main.jython.rickaym.minecraftpy;


import org.python.core.*;

import javax.management.AttributeNotFoundException;
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
    private static final String absoluteRootPath = root.getAbsolutePath();

    private final ArrayList<String> loadedModules = new ArrayList<>();

    private PyObject modPackage;
    private PyObject modPointer;
    private IPyModClass modClass;

    private static final BiFunction<File, String, String> getFullPath = (root, dir) -> String.format("%s\\%s", root.getPath(), dir);

    /**
     * Iterates through the source root and their subsequent sub directories to extract
     * entry points.
     *
     * This method is recursive.
     */
    private PyClassLoader gatherPyModules(File root) {
        String[] subDirs = root.list();
        if (subDirs != null) {
            for (String subDir : subDirs) {
                if (subDir.equals("__init__.py") &! this.loadedModules.contains(subDir)) {
                    this.loadedModules.add(PyClassLoader.getFullPath.apply(root, subDir));
                } else if (!new File(PyClassLoader.getFullPath.apply(root, subDir)).isFile() &! subDir.equals("jython")) {
                    this.gatherPyModules(new File(PyClassLoader.getFullPath.apply(root, subDir)));
                }
            }
        }
        // for method chaining
        return this;
    }

    private void initializePyModules() throws AttributeNotFoundException {
        PySystemState sys = new PySystemState();
        PyObject importer = sys.getBuiltins().__getitem__(Py.newString("__import__"));

        String topLevelPackage = loadedModules
                .stream().min((e1, e2) -> e1.length() < e2.length() ? -1 : 1).get();
        String[] descPath = topLevelPackage.replace("\\", "/").split("/");

        modPackage = importer.__call__(Py.newString(descPath[descPath.length-2]));
        PyList attrs = (PyList) modPackage.__dir__();

        if (!attrs.__contains__(Py.newString("getModClass"))) {
            throw new AttributeNotFoundException("You need a lambda expression in your __init__ file that returns the mod class with the name getModClass.");
        } else {
            PyObject modSupplier = modPackage.__getattr__("getModClass");
            System.out.format("Found mod class as %s\n", modSupplier.__call__().__getattr__("__name__").toString());
            PyObject pureModClass = modSupplier.__call__().__call__();
            PyTuple modInfo = (PyTuple) pureModClass.__getattr__("__mod_meta__");
            System.out.format("Modmetadata is given as %s\n", modInfo.toString());
            modClass = (IPyModClass) pureModClass.__tojava__(IPyModClass.class);
        }
    }

    /**
     * @return IPyModClass of Mod Class implementation
     */
    public static IPyModClass loads() throws AttributeNotFoundException {
        ArrayList<String> modules = new ArrayList<>();
        PyClassLoader instance = new PyClassLoader();
        instance.gatherPyModules(PyClassLoader.root).initializePyModules();

        return instance.modClass;
    }
}
