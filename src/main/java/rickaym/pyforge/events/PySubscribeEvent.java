package rickaym.pyforge.events;

import org.python.core.*;

public class PySubscribeEvent extends PyObject {
    @Override
    public PyObject __call__(PyObject[] args, String[] keywords) {
        if (!(args[0] instanceof PyFunction)) {
            throw Py.TypeError("Expected function type, got " + args[0].getType()
                    .fastGetName());
        }
        PyFunction function = (PyFunction) args[0];
        function.__setattr__("__event_subscriber__", Py.True);
        return function;
    }
}
