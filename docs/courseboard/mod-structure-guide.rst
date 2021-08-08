.. _modstructureguide:

Mod Folder Properties
======================

Structure
-----------
If you've previously developed a mod with Forge, you will notice that the mod folder is in a Java package stylee.

In contending with two very different ideas of what a package is in Java and Python, a mod folder is required to be setup in a form equivalent to a Python package. (having ``__init__.py`` in each directories with allowed relative imports)

As an example provided in the examples mod folder;

.. code-block::

   moder ┓
         ┣━ __init__.py
         ┗━ examplemod ┓
                       ┣━ __init__.py
                       ┣━ main.jython.rickaym.main.py
                       ┣━ extension.py
                       ┗━ more_ext.py

The ``__init__.py`` in the package directory and subdirectories serves as an entry point to the package. It allows for the Python interpreter know that a directory contains code for a Python module. The file can be blank. In the absence of the file, you cannot import modules from another folder into your project. It's conceptual purpose is similar to the __init__ function in a Python class.

.. note::

    As per Python Docs:
    The __init__.py files are required to make Python treat directories containing the file as packages. This prevents directories with a common name, such as string, unintentionally hiding valid modules that occur later on the module search path. In the simplest case, __init__.py can just be an empty file, but it can also execute initialization code for the package or set the __all__ variable.

Prerequisites
---------------

In the top level ``__init__.py`` function, declare a lambda function with the name ``getModClass`` that returns the mod class readily.
Provided in the examples folder;

.. code-block:: python

   # moder/__init__.py
   from examplemod.main.jython.rickaym.main import ModClass

   getModClass = lambda: ModClass

It is necessary to decorate your mod class with the ``@Mod`` decorator. This marks your class object as a mod entry - which will be accessed later by the mod loader.
Follow along closely as so far importing the ``Mod`` decorator has been a bit funky.

Add the language provider package into your ``sys.path`` as directed below. This is done to ensure that any distant imports done inside the mod files can see modules inside the langauge provider class.

.. code-block:: python

   import sys, os

   sys.path.append(os.path.abspath("main.jython.rickaym.main/jython/rickaym/minecraftpy"))

   from Mod import Mod # imports will now search the lang provider package

Imports
--------

...