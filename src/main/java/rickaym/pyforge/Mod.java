package rickaym.pyforge;

import org.python.core.*;

/**
 * `@Mod` decorator to mark a Python class as a mod.
 */
public class Mod extends PyObject {
    public static String mod_id;

    @Override
    public PyObject __call__(PyObject[] args, String[] keywords) {
        PyObject mod_class = args[0];
        {
            if (!(mod_class instanceof PyClass)) {
                throw Py.TypeError("Expected class type, got " + mod_class.getType()
                        .fastGetName());
            }
            class ModMetaFunction extends PyObject {
                @Override
                public PyObject __call__() {
                    return new PyTuple(
                            new PyTuple(new PyString("mod_id"), new PyString(mod_id)),
                            new PyTuple(new PyString("class_name"), new PyString(mod_class.getType()
                                    .fastGetName()))
                    );
                }
            }
            mod_class.__setattr__("__mod_meta__", new ModMetaFunction());
            return mod_class;
        }
    }
}

