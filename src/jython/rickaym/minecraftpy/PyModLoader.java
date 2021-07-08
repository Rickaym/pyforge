package jython.rickaym.minecraftpy;

import java.io.File;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Intermediary mod loader for Python-ically implemented mod classes and extension classes.
 * After which the FML Java mod loader is used traditionally.
 *
 * Takes the heavy lifting aspects of Java-Python (Jython) integrations of Python objects.
 * This is further more explained in the examples.
 * @see <a href="https://github.com/Rickaym/minecraft.py/tree/main/.info/examples/java-jython-integration">
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
    private void getModules(File root) {

        String[] subDirs = root.list();
        if (subDirs != null) {
            for (String subDir : subDirs) {
                if (subDir.endsWith(".py") &! this.loadedModules.contains(subDir)) {
                    this.loadedModules.add(PyModLoader.getFullPath.apply(root, subDir));
                } else if (!new File(PyModLoader.getFullPath.apply(root, subDir)).isFile() &! subDir.equals("jython")) {
                    this.getModules(new File(PyModLoader.getFullPath.apply(root, subDir)));
                }
            }
        }
    }

    /**
     * Exposes the recursive method getModules.
     *
     * @return ArrayList of full-paths leading to each file
     */
    public static ArrayList<String> loads() {
        ArrayList<String> modules = new ArrayList<>();
        PyModLoader instance = new PyModLoader();
        instance.getModules(PyModLoader.root);

        return instance.loadedModules;
    }

}
