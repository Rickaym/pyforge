package main.jython.rickaym.pyminecraft;

import org.python.core.PyTuple;

public interface IPyModClass {
    public PyTuple __mod_meta__();
    public void register();
}
