JamBoard
========

This is intended for contributors only (currently just me tho :))! Also includes assumptions and pre-set positions and does not guarantee to be accurate - it's why this isn't attached in the sphinx docs.

Language Providers
--------------------
`Scorge <https://github.com/MinecraftForge/Scorge>`_

root: ``src/main/scala/net/minecraftforge/scorge/lang...``
providerEntry: ``src/main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"``

`KotlinForForge <https://github.com/thedarkcolour/KotlinForForge>`_

root: ``src/main/kotlin/thedarkcolour/kotlinforforge...``
providerEntry: ``src/main/resources/META-INF/services/"net.minecraftforge.forgespi.language.IModLanguageProvider"``

Progression
================

(To) understand
------------------

.. warning::
   This may not be accurate.

HOW DO YOU INTEGRATE JYTHON CLASSES INTO JAVA?
The most common way to achieve this is by utilizing the object factory design pattern. This means that for Jython to go upstream into Java, that class object must have an implementable Java interface. By using this Java interface
you can create a Python subclass of the interface. In essence, the interface is the Java counter-part of the Python class that is needed for integration to be possible. The Python subclass will now be available to the Java code by
using the python executor of the ``jython.jar`` package.

How does the language provider interact with the internal FML system?
1. The Language Provider is placed inside the mods directory and is loaded as a ServiceLoader from the classpath
(this is guided by the ``.IModLanguageProvider`` ``inside META-INF/services`` referenced in the code as ``providerEntry``

Say the language provider is named PyLanguageProvider
2. Any class that implements ``IModLanguageProvider`` must override several methods, most importantly the ``loadMod`` method
The ``loadMod`` method is called in the system level classLoader @ net.minecraftforge.fml.ModLoader:296.
With ``loadMod``, we are given 3 useful parameters ``IModInfo info, ClassLoader modClassLoader, ModFileScanResults scanResults``

``info`` is fetched from the ``modInfoMap`` @ net.minecraftforge.fml.ModLoader:269, this seems to be generated from the mod file
``modClassLoader`` # should be completely dismissed in this
``scanResults`` has an implementable IModLocator that we can work with to implement scanning -- loaded as a service loader as well

3. ``loadMod`` proceeds depending on the Language Provider implementation - generally it returns a Mod container instance

TO BE CONTINUED

.. warning::
   To implement;
    - build.gradle
    - mods.toml

[NOTES]

Inside FML services there is a language provider called ``MinecraftModLanguageProvider`` - what purpose does it serve? Deobf maybe?

   Check out:
   `Scala Language Provider <https://github.com/MinecraftForge/Scorge>`_
   and `Kotlin Language Provider <https://github.com/thedarkcolour/KotlinForForge>`_ `Jython Docs <https://jython.readthedocs.io/en/latest>`_ , `Jython UserGuide <https://wiki.python.org/jython/UserGuide>`_ and most importantly `Chapter 10 <https://jython.readthedocs.io/en/latest/JythonAndJavaIntegration/?highlight=generics#chapter-10-jython-and-java-integration>`_ `Forge Docs <https://mcforge.readthedocs.io/en/latest>`_ and `Minecraft Forge Repository <https://github.com/MinecraftForge/MinecraftForge>`_
   `Java Doc Comment Spec <https://docs.oracle.com/en/java/javase/11/docs/specs/doc-comment-spec.html>`_