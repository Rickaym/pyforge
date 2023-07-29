<image src="https://raw.githubusercontent.com/Rickaym/pyforge/main/docs/logo.png" height="200px">

<a href="https://www.curseforge.com/minecraft/mc-mods/pyforge">![Static Badge](https://img.shields.io/badge/Download-1a73e8?style=for-the-badge)</a>
# Getting Started

**PyForge** is a Python language provider crafted for Minecraft Forge. It leverages the Jython implementation of Python that runs on the JVM. The module is compatible with Python 2.7.x and is designed to support a comprehensive implementation of a Forge Mod. [See an example mod using here](https://github.com/Rickaym/pymod).

I created pyforge as a way to benchmark the capabilities of Jython and introduce Python developers to the exciting world of Minecraft modding.


```diff
- This project is in the alpha stage, it is not yet ready to be used.
```

## Directory Structure

The directory structure of a mod using pyforge is the same as that of a mod implemented in Java. The only difference is that the package name will be the same as the mod id rather than in Java convention, i.e.,`com.rickaym.mod`.

E.g.
```py
  root ┓
       ┣━ ...
       ┣━ build.gradle
       ┗━ src ┓
              ┗━ pymod ┓  # - id of your mod
                       ┣━ __init__.py
                       ┣━ main.py
                       ┣━ ext.py
                       ┗━ more_ext.py
```

### Setup

The `__init__.py` file inside the `root.src.mod` directory serves as the entrypoint to the mod. To setup the mod, you have to define a function inside the `__init__.py` file called `get_mod_instance` that returns the mod instance. This is the mod instance that used by the mod loader and it must be decorated with the `@Mod` decorator.

```py
# __init__.py

from main import MyMod

get_mod_instance = lambda: MyMod()
```

The implementation of a mod class should look like this:

```py
# main.py

import org.apache.logging.log4j.LogManager as LogManager

from rickaym.pyforge import Mod

from ext import ExtensionClass, SecondExtensionClass
from more_ext import MoreExt

@Mod(mod_id="abc")
class MyMod:
    ...

    def __init__(self):
        self.LOGGER = LogManager.getLogger()
        self.LOGGER.info("Pymod has been loaded in!")

    def register(self):
        ExtensionClass.register()
        SecondExtensionClass.register()
        MoreExt.register()
```


# Contribution and Beta Testing

Contributions are open. Contact `@rick.aym` or join [this discord server](https://discord.gg/UmnzdPgn6g).

