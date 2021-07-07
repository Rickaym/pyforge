package interfaces;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class HumanFactory {
    private final PyObject maleClass;
    private final PyObject femaleClass;

    HumanFactory() {
        /**
         *  HumanFactory constructor (similar to cls.__init__ method in Python)
         */
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from Humans import Male, Female");

        maleClass = interpreter.get("Male");
        femaleClass = interpreter.get("Female");
    }

    public Human createMale(String name, int age) {
        /**
         *  Calls the dunder method __call__ with the arguments needed to
         *  instantiate a PyObject or an instance of the Python class.
         */
        PyObject maleObject = maleClass.__call__(new PyString(name), new PyInteger(age));

        /**
         * Casts its return value of __tojava__ to the shadow interface
         * Read more about __tojava__ here -> ()
         */
        return (Human) maleObject.__tojava__(Human.class);
    }

    public Human createFemale(String name, int age) {
        /**
         *  Call the dunder method __call__ with the arguments needed to make an
         *  integrated python object
         */
        PyObject femaleObject = femaleClass.__call__(new PyString(name), new PyInteger(age));

        // return a casted Human object
        return (Human) femaleObject.__tojava__(Human.class);
    }
    public static void interpret(Human person) {
        System.out.format("NAME: %s\nAGE: %o\nSEX: %s", person.getHumanName(), person.getHumanAge(), person.getHumanSex());
    }
}