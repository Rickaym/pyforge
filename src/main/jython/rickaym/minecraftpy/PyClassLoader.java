package main.jython.rickaym.minecraftpy;

import jnr.ffi.Runtime;
import org.python.antlr.ast.Module;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import javax.management.AttributeNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Not intended for stand-alone use anymore.
 *
 * This is not an implementation of the Java type ClassLoader.
 *
 * Intermediary mod loader for Python-ically implemented mod classes and extension classes.
 *
 * Takes the heavy lifting aspects of Java-Python (Jython) integrations of Python objects includes
 * scanning for mod files and initializing said modules. This is further more explained in the examples.
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">Examples</a>
 */
public class PyClassLoader {
    /** Arbitrary source root for a Python package **/
    private static final File root = new File("../src");
    private static final String absoluteRootPath = root.getAbsolutePath();

    /** Future **/
    private final ArrayList<String> loadedModules = new ArrayList<>();

    private PyObject modPackage;
    private PyObject modPointer;
    private Class<?> modClass;

    /** Utility function to construct the full path from root and currently inspected module **/
    private static BiFunction<File, String, String> getFullPath = (root, dir) -> String.format("%s\\%s", root.getPath(), dir);

    /**
     * Iterates through the source root and their subsequent sub directories to extract
     * .py files.
     *
     * It is recommended to construct your mod classes in an orthodox spice of a
     * Python package structure with the __init__ file (although not necessary - yet).
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

        // Top level packages are compared with the absoluteRootPath because it cannot be greater than absoluteRootPath
        String topLevelPackage = absoluteRootPath;
        for (String path : loadedModules) {
            if (topLevelPackage.length() > path.length()) {
                topLevelPackage = path;
            }
        }
        String[] descPath = topLevelPackage.replace("\\", "/").split("/");

        modPackage = importer.__call__(Py.newString(descPath[descPath.length-2]));
        PyList attrs = (PyList) modPackage.__dir__();
        if (!attrs.__contains__(Py.newString("getModClass"))) {
            throw new AttributeNotFoundException("You need a lambda expression in your __init__ file that returns the mod class.");
        } else {
            modPointer = modPackage.__getattr__("getModClass");
            System.out.println(modPointer.__call__());
        }
    }

    /**
     * Exposes the recursive method getModules.
     *
     * @return ArrayList of full-paths leading to each file
     */
    public static Class<?> loads() throws AttributeNotFoundException {
        ArrayList<String> modules = new ArrayList<>();
        PyClassLoader instance = new PyClassLoader();
        instance.gatherPyModules(PyClassLoader.root).initializePyModules();
        return instance.modClass;
    }
}
