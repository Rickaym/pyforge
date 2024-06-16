# Internals Primer

Let's first understand how the modding process works in perspective to the interactions between Forge, third party mods and pyforge.

## Intermediary
PyForge is a mod loader, which means that it acts as an intermediary between the target mod (Jython) and Minecraft Forge (Java). Our target mod is able to make calls on the Forge library purely by language capabilities. The majority of the work PyForge does is to discover and load the mod into Forge. PyForge is different to other mod loaders like for those of Scala and Kotlin (it is recommended to check them out) in that the Jython classes can be loaded directly into Java, our effort is to wrap around the mod to play well with Forge.

## \_\_init\_\_.py

We scan for packages that contain the `__init__.py` file as potential mod candidates. The target mod is expected to expose their mod instance in this file. We do not have any concern to access other objects in the mod.  

Earlier, we saw this example of the init file.

```python
...
get_mod_instance = lambda: MyMod()
...
```

When we have the path to the mod's init file, we try to find the factory function `get_mod_instance` that readily returns a new mod instance.

This method is preferred over constructing the mod class ourselves in the Jython interpreter because:
1. Simplifies the whole mod loading process to finding a single factory function in a conventional init file
2. Easier to understand and regulate how the mod instance is constructed for the developer

## @Mod Class Decorator

The @Mod class decorator is implemented in Java inside pyforge. It is an "annotation" on the mod class that assigns metadata to the mod class. It does this by setting an attribute `cls.__mod_meta__` to a supplier function that returns a tuple of `(mod_id, class_name)`. Since the decorator is applied to the class, all its instances have a reference to the supplier function.
