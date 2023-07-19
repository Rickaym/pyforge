package rickaym.pyminecraft;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

public class PyModLoadingContext {
    private PyModContainer container;

    public PyModLoadingContext(PyModContainer container) {
        this.container = container;
    }

    public IEventBus getKEventBus() {
        return container.eventBus;
    }

    public static PyModLoadingContext get() {
        return ModLoadingContext.get().extension();
    }

}
