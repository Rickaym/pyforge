package interfaces;

import org.jython.book.interfaces.BuildingType;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class HumanFactory {
    private PyObject maleClass;
    private PyObject femaleClass;

    HumanFactory() {
        /**
         *  Creates a new python interpreter and imports the raw class into java
         */
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from Humans import Male");
        this.maleClass = Interpreter.get("Male");
    }

    public Human createMale(String name, int age) {
        /**
         *  Call the dunder method __call__ with the arguments needed to make an
         *  integrated python object
         */
        PyObject maleObject = this.maleClass.__call__(new PyString(name), new Pystring(age));

        // return a casted Human object
        return (Human) maleObject.__tojava__(Human.class);
    }
}