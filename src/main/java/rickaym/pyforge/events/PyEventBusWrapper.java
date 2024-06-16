package rickaym.pyforge.events;

import net.minecraftforge.eventbus.api.*;
import org.python.core.*;

import java.util.function.Consumer;


public class PyEventBusWrapper {
    final IEventBus eventBus;

    public static PyEventBusWrapper from(IEventBus eventBus) {
        return new PyEventBusWrapper(eventBus);
    }

    public PyEventBusWrapper(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(PyObject target) {
        PyType type = (PyType) target.getType();
        for (PyObject name : type.getDict()
                .__iter__()
                .asIterable()) {
            PyObject value = type.getDict()
                    .__finditem__(name);

//            if (value instanceof PyFunction) {
//                if (value.__getattr__("__event_subscriber__") == Py.True) {
//                    eventBus.register();
//                    eventBus.addListener(new Object() {
//                        @SubscribeEvent
//                        public void onEvent(Event event) {
//                            value.__call__(event);
//                        }
//                    });
//                }
//            }
        }
    }

    public <T extends GenericEvent<? extends F>, F> void addGenericListener
            (Class<F> genericClassFilter, Consumer<T> consumer) {

    }

    public <T extends GenericEvent<? extends F>, F> void addGenericListener
            (Class<F> genericClassFilter, EventPriority priority, Consumer<T> consumer) {

    }

    public <T extends GenericEvent<? extends F>, F> void addGenericListener
            (Class<F> genericClassFilter, EventPriority priority, boolean receiveCancelled, Consumer<T> consumer) {

    }

    public <T extends GenericEvent<? extends F>, F> void addGenericListener
            (Class<F> genericClassFilter, EventPriority priority, boolean receiveCancelled, Class<
                    T> eventType, Consumer<T> consumer) {

    }

    public void unregister(Object object) {

    }

    public boolean post(Event event) {
        return false;
    }

    public boolean post(Event event, IEventBusInvokeDispatcher wrapper) {
        return false;
    }

    public void shutdown() {

    }

    public void start() {

    }
}