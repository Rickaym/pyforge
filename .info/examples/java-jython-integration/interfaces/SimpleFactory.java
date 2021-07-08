package interfaces;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class SimpleFactory {
    private final PyObject maleClass;
    private final PyObject lizziesClass;

    /**
     *  HumanFactory constructor (similar to cls.__init__ method in Python)
     */
    SimpleFactory() {

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from Creatures import Male, Lizzies");

        maleClass = interpreter.get("Male");
        lizziesClass = interpreter.get("Lizzies");
    }

    public Human createMale(String name, int age) {
        // Calls the dunder method __call__ with the arguments needed to
        // instantiate a PyObject or an instance of the Python class.
        PyObject maleObject = maleClass.__call__(new PyString(name), new PyInteger(age));

        //Casts its return value of __tojava__ to the shadow interface
        // Read more about __tojava__ here -> ()
        return (Human) maleObject.__tojava__(Human.class);
    }
    public Alien createLizzie(String name, int age, String race) {
        PyObject lizzieObject = lizziesClass.__call__(new PyString(name), new PyInteger(age), new PyString(race));
        return (Alien) lizzieObject.__tojava__(Alien.class);
    }

    public static void interpretHuman(Human person) {
        System.out.format("NAME: %s\nAGE: %o\nSEX: %s", person.getHumanName(), person.getHumanAge(), person.getHumanSex());
    }
    public static void interpretAlien(Alien species) {
        System.out.format("NAME: %s\nAGE: %o\nRACE: %s", species.getAlienName(), species.getAlienAge(), species.getAlienRace());
    }
}