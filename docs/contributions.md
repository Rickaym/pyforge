# Contributions

You will be working with Minecraft Forge so it is necessary that you
understand the basic aspects of Minecraft Forge if not complex mod
loading mechanisms. Understanding the basic concepts of mod making will
also be very useful in learning low level concepts within Forge. Check out their documentation.

## Requirements

The codebase is developed in `Java`. Most other language providers write in their respective languages, but
pyforge is not written in Python. Python to Java porting demands
boilerplate code and it is unnecessary in the perspective of the
library. The class loader is written in Java along with everything else;
so we implement things in Jython only when it is in necessity. Generally
writing things in Java helps the process of interpolating with the core
Forge mod loading systems as they too are written in Java.

In any case, we will be juggling with the concepts in all three languages:

1.  Java
2.  Python
3.  Jython

## Setup

In summary;

1.  Install a JDK
2.  Obtain an MDK from [Forge](https://files.minecraftforge.net/)
3.  Extract the MDK to an folder and pick out necessary files for mod
    development, namely; `build.gradle`, `gradlew.bat`, `gradlew` and
    the `gradle` folder.
4.  Move files listed above to a new folder. (this will be your mod
    projects folder)
5.  Choose an IDE and load as Gradle project
6.  Generating IDE Launch/Run Configurations with ForgeGradle

See [here](https://mcforge.readthedocs.io/en/latest/gettingstarted/) for
more information.
