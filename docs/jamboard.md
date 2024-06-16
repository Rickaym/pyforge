# JamBoard

This is intended for contributors only (currently just me tho :))! Also
includes assumptions and pre-set positions and does not guarantee to be
accurate - it\'s why this isn\'t attached in the sphinx docs.

## Language Providers

Other existing language providers for Forge; may be useful for common
practices.

[Scorge](https://github.com/MinecraftForge/Scorge)

root:
`src/main.jython.rickaym.main/scala/net/minecraftforge/scorge/lang...`
providerEntry:
`src/main.jython.rickaym.main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"`

[KotlinForForge](https://github.com/thedarkcolour/KotlinForForge)

root:
`src/main.jython.rickaym.main/kotlin/thedarkcolour/kotlinforforge...`
providerEntry:
`src/main.jython.rickaym.main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"`

# Information

## How do you integrate Jython classes into Java?

The most common way to achieve this is by utilizing the object factory
design pattern. This means that for Jython to go upstream into Java,
that class object must have an implementable Java interface. By using
this Java interface you can create a Python subclass of the interface.
In essence, the interface is the Java counter-part of the Python class
that is needed for integration to be possible. The Python subclass will
now be available to the Java code by using the python executor of the
`jython.jar` package.

\"1.16.5 looks for the mods.toml file. In there the loader field
indicates which language provider is responsible for loading, which is
javafml javafml then reads the mods.toml and builds the mods that way
Then, the specified file system is scanned by javafml for a class with
the \@Mod annotation An \@Mod without a corresponding mod entry is
marked as an error. The same happens viceversa The run configuration
injects some Metadata into the environment variables in the Form of
MOD_CLASSES \"

## How does the internal FML system load through LanguageProviders?

Note: `fml` is `net.minecraftforge.fml` in the codebase

1.  Mod loading starts in `fml.Client.ClientModLoader:103`, calling
    `fml.ModLoader.gatherAndInitializeMods(...)`
2.  `fml.ModLoader.gatherAndInitializeMods:175` calls
    `fml.ModLoader.buildMods(...)` to get `` `ModContainer ``s
3.  `fml.ModLoader.buildMods:272` calls
    `fml.ModLoader.buildModContainerFromTOML(...)` to build
    `ModContainer`s
4.  in the `fml.ModLoader.buildModContainerFromTOML` function, the
    `IModLanguageLoader` is parsed from the `mods.toml` file, it is then
    loaded as a service loader from the classpath
5.  the `IModLanguageLoader` is then used to load the mod container by
    calling `IModLanguageLoader.loadMod(...)`.

## How does a language provider work?

1.  The Language Provider is placed inside the mods directory and is
    loaded as a ServiceLoader from the classpath - as with all service
    loaders, it is guided by the
    `net.minecraftforge.forgespi.language.IModLanguageProvider` file
    inside services,`META-INF/services` referenced in the code as
    `providerEntry`. Watch
    `here <https://youtu.be/iLPIUaNV-Kc>`{.interpreted-text
    role="ref"}\_ and read
    `read here <https://stackoverflow.com/questions/4544899/java-meta-inf-services>`{.interpreted-text
    role="ref"}\_ to learn more about how this system works.

Let\'s assume that we\'ve made a language provider called
UServiceProvided and have inserted it into the meta-inf folder.

2\. Any class that implements `IModLanguageProvider` must override
several methods, most importantly the `loadMod` method The `loadMod`
method is called in the system level classLoader @
net.minecraftforge.fml.ModLoader:296. With `loadMod`, we are given 3
useful parameters
`IModInfo info, ClassLoader modClassLoader, ModFileScanResults scanResults`

Parameter 1, `info` is fetched from the `modInfoMap` @
net.minecraftforge.fml.ModLoader:269, this is generated from the mod
file

Parameter 2, `modClassLoader` java class loader in the correct context

Parameter 3, `scanResults` internal mod scanning stuff done somewhere in
the system mod loader and has an implementable IModLocator that we can
work with to implement scanning \-- loaded as a service loader as well

3.  `loadMod` proceeds depending on the Language Provider
    implementation - generally it returns a Mod container instance

(TO BE CONTINUED)

::: warning
::: title
Warning
:::

To implement; - build.gradle - mods.toml
:::

::: note
::: title
Note
:::

Check out: \| [Scala Language
Provider](https://github.com/MinecraftForge/Scorge) \| [Kotlin Language
Provider](https://github.com/thedarkcolour/KotlinForForge) \| [Jython
Docs](https://jython.readthedocs.io/en/latest) \| [Jython
UserGuide](https://wiki.python.org/jython/UserGuide) and importantly
[Chapter
10](https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#chapter-10-jython-and-java-integration)
\| [Forge Docs](https://mcforge.readthedocs.io/en/latest) and [Minecraft
Forge Repository](https://github.com/MinecraftForge/MinecraftForge) \|
[Java Doc Comment
Spec](https://docs.oracle.com/en/java/javase/11/docs/specs/doc-comment-spec.html)
:::
