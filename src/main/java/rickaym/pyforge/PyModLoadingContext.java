package rickaym.pyforge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

public class PyModLoadingContext {
    private final PyModContainer container;

    public PyModLoadingContext(PyModContainer container) {
        this.container = container;
    }

    public IEventBus getModEventBus() {
        return container.eventBus;
    }

    public static PyModLoadingContext get() {
        return ModLoadingContext.get().extension();
    }

}
