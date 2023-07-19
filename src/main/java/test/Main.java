package test;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;


public class Main {
    public static void main(String[] args) {
        String modEntryFilePath = "";
        PyObject module;
        String[] descPath = modEntryFilePath.replace("\\", "/")
                .split("/");

        String moduleName = descPath[descPath.length - 2];

    }
    private void testLoadInterpreter(String sourceDir, String moduleName) {
        PythonInterpreter jyInterpreter = new PythonInterpreter();
        jyInterpreter.exec("import sys");
        jyInterpreter.exec(String.format("sys.path.append(\"%s\")", sourceDir));  // replace with actual path
        jyInterpreter.exec(String.format("import %s", moduleName));  // replace with actual module name
        PyObject module = jyInterpreter.get(moduleName);
        System.out.format("Loaded in module '%s'.\n", module);
    }

    private void testLoadSysState(String sourceDir, String moduleName) {
        PySystemState sys = new PySystemState();
        sys.path.append(Py.newString(sourceDir.replace("\\", "\\\\")));
        // Print the Python path after adding the directory
        PyObject importer = sys.getBuiltins()
                .__getitem__(Py.newString("__import__"));
        System.out.format("%s importer calling the top level module '%s'.\n", importer, moduleName);
        PyObject module = importer.__call__(Py.newString(moduleName));

    }
}
