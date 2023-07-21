package test;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import rickaym.pyminecraft.PyModLoader;

import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        String moduleName = "examplemod";
        String sourceDir = Paths.get("src")
                .toAbsolutePath()
                .normalize()
                .toString();
        PyModLoader.loadMod(sourceDir, moduleName);
    }
    private static void testLoadInterpreter(String sourceDir, String moduleName) {
        PythonInterpreter jyInterpreter = new PythonInterpreter();
        jyInterpreter.exec("import sys");
        jyInterpreter.exec(String.format("sys.path.append(\"%s\")", sourceDir));  // replace with actual path
        jyInterpreter.exec(String.format("import %s", moduleName));  // replace with actual module name
        PyObject module = jyInterpreter.get(moduleName);
        System.out.format("Interpreter loaded in module '%s'.\n", module);
    }

    private static void testLoadSysState(String sourceDir, String moduleName) {
        PySystemState sys = new PySystemState();
        sys.path.append(Py.newString(sourceDir));
        PyObject importer = sys.getBuiltins()
                .__getitem__(Py.newString("__import__"));
        System.out.format("%s importer calling the top level module '%s'.\n", importer, moduleName);
        PyObject module = importer.__call__(Py.newString(moduleName));
        System.out.format("System state loaded in module '%s'.\n", module);
    }
}
