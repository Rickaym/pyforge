.. _`Minecraft Forge`: https://github.com/MinecraftForge/MinecraftForge
.. _Java: https://www.java.com/

Introduction
============

Minecraft Version
-----------------

.. image:: _static/Minecraft_cover.png

This project is aimed at the version ``1.16.5`` and proceeding regressively until ``1.8.0`` MDK provided by `Minecraft Forge`_.

Java
=====

Why do I have to read about Java? Because `Java`_ is an integral part of Minecraft, and it will neither be short-lived in this project either.

Minecraft as of present is packaged with it's own Java to run on. Although this isn't shared with bundled mods as you will find out further down the document.

JVM and hybrid complications
------------------------------

Fortunately, this isn't a Java bootcamp course so I'll be skiing through the details.

Java is known to be an interpreted and compiled language.


1.  The compiler translates Java programs ``.java`` into Java Bytecode ``.class``. This is a form of machine language for the imaginary `Java Virtual Machine <https://simple.wikipedia.org/wiki/Java_virtual_machine>`_.
2.  Java Bytecode programs are then interpreted / executed at runtime by `an interpreter <https://www.javatpoint.com/java-interpreter>`_.

Did someone say Minecraft Modding?
-------------------------------

 *"Mojang and Microsoft provide little official support for this (for example, the game provides no  modding API for Java, although Mojang does provide methods for deobfuscating the game), but the EULA permits non-commercial mods."*

Introducing 3rd party modding APIs.

There is a variety of modding APIs to choose from — as of currently, to name a few; fabric, forge, sponge etc.. This project as per described in the introduction is meant on providing a language adapter for `Minecraft Forge`_.

MinecraftForge has an official documentation for modding `here <https://mcforge.readthedocs.io/en/latest/gettingstarted/>`_ that explains a great deal of information regarded to modding with it's framework.
