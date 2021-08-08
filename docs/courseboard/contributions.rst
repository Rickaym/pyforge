Contributions
===============

Yes! Contributions! Hereby, I shallth lay down the *official* guidelines for contributions. This is a general list of what you'll need to understand and follow to get contributing!

This is not a necessary for using this framework \\o\\

To implement a language adapter for MinecraftForge we must first understand how the modding process works in perspective of how the interactions between Forge, third party mods and the main.jython.rickaym.main Minecraft program interacts with each other.

Let's run down the general processes of Forge modding.

MinecraftForge modding
========================

In summary (general development);

1. Install a JDK (java development kit)
2. Obtain an MDK (mod development kit) from `Forge <https://files.minecraftforge.net/>`_
3. Extract the MDK to an folder and pick out necessary files for mod development, namely; ``build.gradle``, ``gradlew.bat``, ``gradlew`` and the ``gradle`` folder.
4. Move files listed above to a new folder. (this will be your mod projects folder)
5. Choose an IDE
6. Generating IDE Launch/Run Configurations with ForgeGradle

This concludes the rudimentary concepts of Forge modding, look at `this <https://mcforge.readthedocs.io/en/latest/gettingstarted/>`_ for more information.

Requirements
-------------

Majority of the codebase is expected to be done in ``Java``.

Python to Java porting demands a great deal of boilerplate code and it's unnecessary in perspective of a framework.

Java
^^^^^
The class loader is written in Java along with everything else - we implement things in Python only when it is in necessity. Generally writing things in Java helps lubricate the process of interpolating with the core Forge mod loading systems as they too are written in Java. Thins down our troubles regarding post integration scrutiny by the JVM compiler.

You will find other language providers written in their respective languages. As described in Jython integration, the process is smooth and seamless but far from jolly good, it's desirable to port things into Python then write an intermediary factory but overcomplicating and a great faff so keep it Java fellas!

Python
^^^^^^^
Python! Loads of em! Core concepts of the data model, IO and Object Oriented Programming are a few central concepts in understanding the fiddling of both languages as Python, and the integration bridges provided for them to implement the best possible solution for Python in Forge.
Understanding Python means understanding half of the puzzle, hooray! It would be precise to insist that the understanding of Python is more necessary than Java, ultimately the end product is heavily collected around Python.

Jython
~~~~~~~
Jython is a flavor of Python, meaning it's not a different language nor an absolutely alien body of mechanisms. Jython introduces Java implemented core concepts that are found in CPYthon, and also it's own helper features.

You will need to understand the core concepts in Jython such as the syntax (not necessary if you know python) and the Java and Python Integration including the Object factory pattern.

Minecraft Forge
^^^^^^^^^^^^^^^^^
This is the framework you will be working with. It's necessary that you understand the basic aspects of Minecraft Forge if not complex mod loading mechanisms. Understanding the basic concepts of mod making will also be very useful in learning low level concepts within Forge.

minecraft.py
^^^^^^^^^^^^^
Yep, this project. You need to understand this project before you contribute anything. This project is heavily documented by me contemporary to development :). All the information needed to understand this project is well documented and will be provided in readthedocs.

Standards and Guidelines
-------------------------

Introducing New Things
^^^^^^^^^^^^^^^^^^^^^^^
I love new additions! - but only when they are maintainable. If you want to introduce a substantial rewrite or an addition, you will go through the internal team and partnering contributors for finalisation and improvements. Once discussed, the feature will be added!
If your proposal needs more than one person working on it, I'll be happy to arrange an internal team specifically for this feature so-long as it's under basic development.

Documentation
^^^^^^^^^^^^^^
Documentation is where you get to speak for your work, it's the representation in magnitude of your words in it's accord. It's the most intimate connection you'll have as a developer with whoever using your work, first being the code itself. Document your code and only document what you can maintain - do it your way but don't be unorthodox!

A developer's code reads like a Hiaku - but sometimes it doesn't. Document ``why is`` and ``how is`` (especially things you know other people will find it hard to understand) and avoid unnecessary complications by introducing different modules and extra dependencies when given alternatives exist.

Your boat floats?! Awesome, contact me on discord at ``Neo#1844`` or join `this server <https://discord.gg/UmnzdPgn6g>`_. B)

~