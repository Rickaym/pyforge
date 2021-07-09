from inspect import isclass
from typing import AnyStr, Callable

from .Errors import BadModClass
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
    IDENTIFIER: str = "pikachu"

    def __init__(self, mod_id: AnyStr = ''):
        self.mod_id = mod_id

    def __call__(self, mod_object: Callable):
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
            mod_object.__str__ = lambda *args: Mod.IDENTIFIER
            return mod_object
        return wrapper
