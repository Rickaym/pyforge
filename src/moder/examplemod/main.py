from jython.rickaym.minecraftpy import Mod

from .extension import ExtensionClass, SecondExtensionClass
from .more_ext import MoreExt

mod_id = "abc"


@Mod(mod_id=mod_id)
class ModClass:
    """
    Exemplary implementation of the Mod class.

    Decorated with the @Mod decorator with the appropriate
    mod id passed in as a parameter.

    It's usually a bad idea to decorate multiple classes with
    the decorator and expose both of them publicly.
    """
    mod_id = "abc"

    def __init__(self):
        ...

    def register(self):
        """
        Non static method to register things into appropriate registries
        """
        ExtensionClass.register()
        SecondExtensionClass.register()
        MoreExt.register()
