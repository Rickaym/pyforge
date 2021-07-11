import sys, os

from inspect import isclass

from Errors import BadModClass

sys.path.append(os.path.abspath("main/jython/rickaym/minecraftpy"))

import IPyModClass

"""
Main Mod Loader Class
"""


class Mod:
    """A Python implementation for the unique mod identifier.

    This will be used to identify your mod for third parties (other mods), it will be used to identify your mod for
    registries such as block and item registries. By default, you will have a resource domain that matches the mod_id.

    All these uses require that constraints are imposed on the format of the mod_id.
    """

    # mod identifier string
    IDENTIFIER = "pikachu"

    def __init__(self, mod_id=''):
        self.mod_id = mod_id

    def __call__(self, mod_object):
        """ Class decorator to identify a mod class

        Makes use of __str__ as an arbitrary marker.
        Any manual definition of the __str__ method will be overridden by the arbitrary
        class marker.

        Unfortunately, you will not be able to implement a nice string representable of your mod class
        upon `print` but consider implementing the __repr__ method and calling it directly on print

        The identifier will be later checked to differentiate the mod class.
        """
        def wrapper():
            if not isclass(mod_object):
                raise BadModClass("Your decorated Mod Class must be a class ...")
            decorated_mod = type("ModClass", (mod_object, IPyModClass), mod_object.__dict__)
            decorated_mod.__str__ = lambda *args: Mod.IDENTIFIER
            return decorated_mod
        return wrapper
