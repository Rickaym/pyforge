package main.jython.rickaym.minecraftpy;

import net.minecraftforge.fml.ModLoadingContext;

public class PyModLoadingContext {
    private final PyModContainer container;

    PyModLoadingContext(PyModContainer container) { this.container = container; }

    /***
     * @return The pymod event bus to allow subscription to Mod specific events.
     */
    public IEvenBus getModEventBus() {
        return container.getEventBus();
    }

    /**
     * @return The right mod loading context for the mod
     */
    public static PyModLoadingContext get() { return ModLoadingContext.get().extension(); }
}
