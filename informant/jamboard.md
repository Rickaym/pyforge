# JamBoard

This is not intended for understanding the project!

## [Scala Language Adapter (Scorge)](https://github.com/MinecraftForge/Scorge)

**Info~**

1. `src/main/scala/net/minecraftforge/scorge/lang...`
2. `src/main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"`

## [Kotlin Language Adapter (KotlinForForge)](https://github.com/thedarkcolour/KotlinForForge)

**Info~**

1. `src/main/kotlin/thedarkcolour/kotlinforforge...`
2. `src/main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"`

## Progression

Make new package preferrably under the name of the language adapter being made inside<br> `src/main/package` **>>** `src/main/jython/net/minecraftforge/minecraft.py/...`.

Establish a new folder called `resources` that define metadata in `META-INF` with a copy of `"net.minecraftforge.forgespi.language.IModLanguageProvider"` inside a folder called `services` (intentions are unclear) with the path pointing to the code of the adapter language provider on the classpath.

Define the version and metadata of the language adapter in `main/resources/pack.mcmeta`.

<details>
<summary>
Example of the metapack is as follows by KotlinForForge.<br><br>
</summary>

```mcmeta
{
  "pack": {
    "description": "Kotlin for Forge resources",
    "pack_format": 4
  }
}
```

Here we see examples of the IModLanguageProvider that lives inside the services folder to be loaded.

```java
// inside Scorge // net.minecraftforge.forgespi.language.IModLanguageProvider

net.minecraftforge.scorge.lang.ScorgeModLanguageProvider

// inside KotlinForForge // net.minecraftforge.forgespi.language.IModLanguageProvider
thedarkcolour.kotlinforforge.KotlinLanguageProvider
```

<br>
</details>

<details>
<summary>
A closer inspection to IModLanguageProvider.
</summary>

It provides [an interface](../ForgeSPI-4.0/src/main/forgespi/language/IModLanguageProvider.java) attached below for loading _xyz_. Read more about the LifeCycleEvents [here](https://mcforge.readthedocs.io/en/1.16.x/concepts/lifecycle/).

```java
package net.minecraftforge.forgespi.language;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Loaded as a ServiceLoader, from the classpath. ExtensionPoint are loaded from
 * the mods directory, with the FMLTpe META-INF of LANGPROVIDER.
 *
 * Version data is read from the manifest's implementation version.
 */
public interface IModLanguageProvider
{
    String name();

    Consumer<ModFileScanData> getFileVisitor();

    <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> consumeEvent);

    interface IModLanguageLoader {
        <T> T loadMod(IModInfo info, ClassLoader modClassLoader, ModFileScanData modFileScanResults);
    }
}
```

</details><br>

This opens up a window to extend the same public interface in Jython which is described as down below <sup>[[1]](https://www.oreilly.com/library/view/python-in-a/0596100469/ch26.html)</sup>;

> The automatic availability of packages on Jython.syspath applies to Java’s standard libraries, third-party Java libraries you have installed, and Java classes you have coded yourself. You can extend Java with C using the Java Native Interface (JNI), and such extensions will be available to Jython code, just as if they were coded in pure Java rather than in JNI-compliant C.

A project post regarding the same idea back in 2015 in the [Minecraft Forums](https://www.minecraftforum.net/) will give you an idea of approach and understanding on this topic <sup>[[2]](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2532785-jython-for-modding)</sup>.

### Plausible Option — Re-making [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events)

1. This option requires implementation of the [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events) to be multilingual for Jython, requiring a large overhead of things that comes with re-implementing [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events). If done successfully, it may be possible to register events in Python classes that are directly relayed into the game code. This sort of abstains in using the ForgeSPI provided mod loader and such. It is still very ambiguous how re-making [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events) would affect the remaining utilities that comes with [MinecraftForge](https://github.com/MinecraftForge/MinecraftForge).

### Plausible Option — Extending [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events)

1. For the implementation to be maintanable whilst avoiding morbidity, the particulars of [`Registers`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/) or [`RegisteryEvents`](https://mcforge.readthedocs.io/en/1.16.x/concepts/registries/#register-events) have to be understood.

2. It may be possible to use a Jython to Java adapter (probably an object factory class) to implement the Python mod class into a Java class or object whilst persisting the Python implementation of a Registery responsibility into Java.
