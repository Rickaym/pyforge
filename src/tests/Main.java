package tests;

import main.jython.rickaym.minecraftpy.IPyModClass;
import main.jython.rickaym.minecraftpy.PyClassLoader;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import javax.management.AttributeNotFoundException;
import java.io.File;

public class Main {
    static String package_path = "C:/Users/User/Documents/Programming/Java/far_away_package";

    public static void main(String[] args) throws AttributeNotFoundException {
        IPyModClass my_class = PyClassLoader.loads();
        my_class.register()
    }

    public static void test_terpreter() {
         PythonInterpreter interpreter = new PythonInterpreter();

         interpreter.exec(String.format("import sys, os; sys.path.append('%s');sys_path = sys.path", package_path));
         System.out.format("SYSPATH IS: %s\n", interpreter.get("sys_path").toString());
         System.out.format("IN SPECIFIED PATH IS: %s\n", String.join(", ", new File(package_path).list()));

        try {
            interpreter.exec(String.format("import sys;sys.path.append('%s');import MyModule;", package_path));
        } catch (PyException e) {
            System.out.println("ImportError: No module named MyModule (BY INTERPRETER)");
        }
        System.out.println(interpreter.get("MyModule"));
    }

    public static void test_systate() {
        PySystemState sys = new PySystemState();
        sys.path.append(Py.newString(package_path));

        System.out.format("SYSPATH IS: %s\n", sys.path.toString());
        System.out.format("IN SPECIFIED PATH IS: %s\n", String.join(", ", new File(package_path).list()));
        PyObject importer = sys.getBuiltins().__getitem__(Py.newString("__import__"));
        try {
            PyObject module = importer.__call__(Py.newString("MyModule"));
            System.out.println(module);

        } catch (PyException e) {
            e.printStackTrace();
            System.out.println("ImportError: No module named MyModule (BY INTERPRETER)");
        }
    }
}
