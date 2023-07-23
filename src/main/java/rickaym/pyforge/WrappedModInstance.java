package rickaym.pyforge;

import org.python.core.PyInstance;
import org.python.core.PyTuple;

import java.util.Map;

public class WrappedModInstance {
    private PyInstance modInstance;
    WrappedModInstance(PyInstance instance) {
        modInstance = instance;
    }

    public void register() {
        modInstance.invoke("register");
    }

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
