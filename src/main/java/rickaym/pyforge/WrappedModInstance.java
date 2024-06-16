package rickaym.pyforge;

import org.python.core.PyInstance;
import org.python.core.PyTuple;

import java.util.Map;

/**
 * Wraps a Python mod instance with a Java interface.
 */
public class WrappedModInstance {
    private final PyInstance modInstance;
    WrappedModInstance(PyInstance instance) {
        modInstance = instance;
    }

    /**
     * Invokes the mod's inner register function.
     */
    public void register() {
        modInstance.invoke("register");
    }

    /**
     * Gets the mod's metadata set through the decorator.
     */
    public Map<String, String> getModMeta() {
        PyTuple ret = (PyTuple) modInstance.invoke("__mod_meta__");
        Map<String, String> modMeta = new java.util.HashMap<>();
        for (int i = 0; i < ret.__len__(); i++) {
            PyTuple tuple = (PyTuple) ret.__getitem__(i);
            modMeta.put(tuple.__getitem__(0).toString(), tuple.__getitem__(1).toString());
        }
        return modMeta;
    }

}
