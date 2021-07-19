package tests;

import main.jython.rickaym.minecraftpy.PyClassLoader;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import javax.management.AttributeNotFoundException;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("\\\\ Interpreter \\\\");
        test_terpreter();
        System.out.println("\\\\ SystemState \\\\");
        test_systate();
    }

    public static void test_terpreter() {
         String package_path = "C:\\Users\\User\\Documents\\Programming\\Java\\far_away_package";
         PythonInterpreter interpreter = new PythonInterpreter();

         interpreter.exec(String.format("import sys, os; sys.path.append('%s');sys_path = sys.path;os.current_wkd = os.curdir", package_path));
         System.out.format("SYSPATH IS: %s | CURRENT WORKING DIR IS: %s\n", interpreter.get("sys_path").toString(), interpreter.get("current_wkd").toString());
         System.out.format("IN SPECIFIED PATH IS: %s\n", String.join(", ", new File(package_path).list()));
        try {
            interpreter.exec(String.format("import sys;sys.path.append('%s');import MyModule;", package_path));
        } catch (PyException e) {
            System.out.println("ImportError: No module named MyModule (BY INTERPRETER)");
        }
    }

    public static void test_systate() {
        String package_path = "C:\\Users\\User\\Documents\\Programming\\Java\\far_away_package";

        PySystemState sys = new PySystemState();
        sys.path.append(Py.newString(package_path));
        System.out.format("SYSPATH IS: %s\n", sys.path.toString());
        System.out.format("IN SPECIFIED PATH IS: %s\n", String.join(", ", new File(package_path).list()));

        PyObject importer = sys.getBuiltins().__getitem__(Py.newString("__import__"));
        try {
        PyObject module = importer.__call__(Py.newString("MyModule"));
        } catch (PyException e) {
        System.out.println("ImportError: No module named MyModule (BY SYSTATE)");
        }
    }
}
