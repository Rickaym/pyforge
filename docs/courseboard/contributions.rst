Contributions
===============

To implement a language adapter for MinecraftForge we must first understand how the modding process works in perspective of how the interactions between Forge, third party mods and the main.jython.rickaym.main Minecraft program interacts with each other.

Let's run down the general process of Forge modding.

Forge modding
-------------

In summary;

1. Install a JDK
2. Obtain an MDK from `Forge <https://files.minecraftforge.net/>`_
3. Extract the MDK to an folder and pick out necessary files for mod development, namely; ``build.gradle``, ``gradlew.bat``, ``gradlew`` and the ``gradle`` folder.
4. Move files listed above to a new folder. (this will be your mod projects folder)
5. Choose an IDE and load as Gradle project
6. Generating IDE Launch/Run Configurations with ForgeGradle

See `here <https://mcforge.readthedocs.io/en/latest/gettingstarted/>`_ for more information.

Contribution Requirements
-------------------------

Majority of the codebase is expected to be developed in ``Java``.

You will find other language providers being written in their respective languages, however, we do not write the LangaugeProvider in Jython itself. Python to Java porting demands boilerplate code and it is unnecessary in the perspective of the library. The class loader is written in Java along with everything else; we implement things in Jython only when it is in necessity. Generally writing things in Java helps ease the process of interpolating with the core Forge mod loading systems as they too are written in Java.

In any case, we will be using the concepts found in the following libraries

1. Java
2. Python
3. Jython

Minecraft Forge
^^^^^^^^^^^^^^^^^
This is the framework you will be working with. It is necessary that you understand the basic aspects of Minecraft Forge if not complex mod loading mechanisms. Understanding the basic concepts of mod making will also be very useful in learning low level concepts within Forge.

pyforge
^^^^^^^^^^^^^
Yep, this project. You need to understand this project before you contribute anything. This project is heavily documented by Ricky contemporary to development. All the information needed to understand this project is well documented and will be provided in readthedocs.

Standards and Guidelines
-------------------------

Documentation
^^^^^^^^^^^^^^
Documentation is where you get to speak for your work, it's the representation in magnitude of your words in it's accord. It's the most intimate connection you'll have as a developer with whoever using your work, first being the code itself. Document your code and only document what you can maintain - do it your way but don't be unorthodox!

A developer's code reads like a Hiaku - but sometimes it doesn't. Document ``why is`` and ``how is`` (especially things you know other people will find it hard to understand) and avoid unnecessary complications by introducing different modules and extra dependencies when given alternatives exist.

~