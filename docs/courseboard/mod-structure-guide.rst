.. _modstructureguide:

Mod Folder Properties
======================

Structure
-----------
If you've previously developed a mod with Forge, you will notice that the mod folder is in a neat Java package stylee.

Similarly, it's required to setup the mod folder in a form equivalent to a Python package (excluding ``setup.py`` and other config files).
As an example provided in the examples mod folder;

.. code-block::

   moder ┓
         ┣━ __init__.py
         ┗━ examplemod ┓
                       ┣━ __init__.py
                       ┣━ main.py
                       ┣━ extension.py
                       ┗━ more_ext.py

Some of you may understand what purpose ``__init__.py`` files serve but if not, I'll discuss about that in a little bit.
Now, keep in mind that it's highly important to have the ``__init__.py`` files in all entry points, this is due to how the mod class loader is setup.

.. note::

    As per Python Docs:
    The __init__.py files are required to make Python treat directories containing the file as packages. This prevents directories with a common name, such as string, unintentionally hiding valid modules that occur later on the module search path. In the simplest case, __init__.py can just be an empty file, but it can also execute initialization code for the package or set the __all__ variable, described later.

Prerequisites
---------------

In the top level ``__init__.py`` function, declare a lambda function with the name ``getModClass`` that returns the mod class readily.
Provided in the examples folder;

.. code-block:: python

   # moder/__init__.py
   from examplemod.main import ModClass

   getModClass = lambda: ModClass

It is necessary to decorate your mod class with the ``@Mod`` decorator. This attaches required annotations to your mod class and removes any singleton functions.
Follow along closely as so far importing the ``Mod`` decorator has been a bit funky.

Add the language provider package into your ``sys.path`` as directed below. This is done to ensure that any distant imports done inside the mod files can see modules inside the langauge provider class.

.. code-block:: python

   import sys, os

   sys.path.append(os.path.abspath("main/jython/rickaym/minecraftpy"))

   from Mod import Mod # imports will now search the lang provider package

That is it so far!