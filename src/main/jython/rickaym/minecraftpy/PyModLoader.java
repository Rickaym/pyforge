package main.jython.rickaym.minecraftpy;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;

import java.io.File;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Intermediary mod loader for Python-ically implemented mod classes and extension classes.
 * After which the FML Java mod loader is used traditionally.
 *
 * Takes the heavy lifting aspects of Java-Python (Jython) integrations of Python objects.
 * This is further more explained in the examples.
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">Examples</a>
 */
public class PyModLoader {
    /** Arbitrary source root for a Python package **/
    private static final File root = new File("../src");

    /** Future **/
    private final ArrayList<String> loadedModules = new ArrayList<>();

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
    private PyModLoader gatherPyModules(File root) {
        String[] subDirs = root.list();
        if (subDirs != null) {
            for (String subDir : subDirs) {
                if (subDir.endsWith(".py") &! this.loadedModules.contains(subDir)) {
                    this.loadedModules.add(PyModLoader.getFullPath.apply(root, subDir));
                } else if (!new File(PyModLoader.getFullPath.apply(root, subDir)).isFile() &! subDir.equals("jython")) {
                    this.gatherPyModules(new File(PyModLoader.getFullPath.apply(root, subDir)));
                }
            }
        }
        return this;
    }

    private void initializePyModules() {
        PySystemState sys = new PySystemState();
        PyObject importer = sys.getBuiltins().__getitem__(Py.newString("__import__"));
        // TODO: Properly find modules from filepath and initialize them
        String validPath = this.loadedModules.get(0).replace('\\', '.').substring(1, this.loadedModules.get(0).length() - 3);
        PyObject module = importer.__call__(Py.newString("moder"));
        System.out.println(module.__dir__());
        System.out.println(module.__getattr__(Py.newString("examplemod")));
    }

    /**
     * Exposes the recursive method getModules.
     *
     * @return ArrayList of full-paths leading to each file
     */
    public static ArrayList<String> loads() {
        ArrayList<String> modules = new ArrayList<>();
        PyModLoader instance = new PyModLoader();
        instance.gatherPyModules(PyModLoader.root).initializePyModules();

        return instance.loadedModules;
    }

}
