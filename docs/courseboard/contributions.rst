Contributions
===============

Yes! Contributions! Here I will lay down the *official* guidelines for contributions. This is not much of a set-in-stone rules or requirements but rather a general list of what you'll need to understand to get contributingg!

This is not a necessary for using this framework \\o\\

Requirements
-------------

The work here is done in ``Java``.

Python to Java porting demands a lot to a lot boilerplate code, it's completely unnecessary and messy for building this framework.

Java
^^^^^
The class loader is written in Java along with everything else - we implement things in Python only when it is the utmost necessity.
Generally writing things in Java helps lubricate the process of interpolating with the core Forge mod loading systems. It will also thin down our troubles regarding post integration scrutiny by the compiler.

When it comes to working with Forge the knowledge for Java will become increasingly fundamental. The whole framework is written in Java from head to toe.

You will find other language providers written in their respective languages - here things work a little bit different.
As described in Jython integration, the process is smooth and seamless but far from jolly good, it's desirable to port things into Python then write an intermediary factory but overcomplicating and a great faff so keep it Java fellas!

Python
^^^^^^^
However, you still need to know Python! Loads of em! The key concept here is the fiddling of both languages and the integration bridges provided for them to implement the best possible solution for Python in Forge.
Understanding Python means understanding the one side of town, hooray! I would actually insist that you will need more Python knowledge as opposed to Java as the end product is heavily collected around Python.

Jython
~~~~~~~
I combined Jython under Python because it's not a different language nor a wide area of alien expertise (though it certainly can be)
Jython introduces very minimal core concepts once understanding it's two integral languages Python and Java.

That being said, you will need to understand the core concepts in Jython such as the syntax (not necessary if you know python) and the Java and Python Integration including the Object factory pattern.

Minecraft Forge
^^^^^^^^^^^^^^^^^
Ah yes! This is the framework you will be working with in this project. It's necessary that you understand the basic aspects of Minecraft Forge if not complex mod loading mechanisms. I insist you understand the basic concepts of mod making as it will be very useful in learning harder more low level concepts within Forge.

minecraft.py
^^^^^^^^^^^^^
Yep, this project. You need to understand this project before you contribute anything. This project is heavily documented by me contemporary to development :) All the information needed to understand this project is well documented and will be provided in readthedocs after I've sorted out a few things.

Standards and Guidelines
-------------------------

Introducing New Things
^^^^^^^^^^^^^^^^^^^^^^^
I love new additions! - but only when they are maintainable. If you want to introduce a substantial rewrite or an addition, you will go through the internal team and partnering contributors for finalisation and improvements. Once discussed, the feature will be happily added!
If your proposal needs more than one person working on it, I'll be happy to arrange an internal team specifically for this feature so-long as it's under basic development.

Documentation
^^^^^^^^^^^^^^
Documentation is where you get to speak for your work, it's the representation of the code in your own words. It's the second most intimate connection you'll have as a developer with whoever using your work, first being the code itself. Document your code and only document what you can maintain!! Do it your way but don't be unorthodox!
A developer's code reads like a Hiaku - but sometimes it doesn't. Document ``why is`` and ``how is`` (especially things you know other people will find it hard to understand) and avoid unnecessary complications by introducing different modules and extra dependencies when given alternatives exist.

Your boat floats?! Awesome, contact me on discord at ``Neo#1844`` or join `this server <https://discord.gg/UmnzdPgn6g>`_. B)

~