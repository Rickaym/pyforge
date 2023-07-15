import sys, os

sys.path.append(os.path.abspath("main/jython/rickaym/pyminecraft"))

from Mod import Mod

from extension import ExtensionClass, SecondExtensionClass
from more_ext import MoreExt

mod_id = "abc"

@Mod(mod_id=mod_id)
class MyMod:
    """
    Exemplary implementation of the Mod class.

    Identified using the @Mod decorator and an appropriate
    mod id.

    It's a bad idea to decorate multiple classes with
    this decorator and expose both of them publicly.
    """

    def __init__(self):
        pass

    def register(self):
        """
        Method to register things into appropriate registries
        """
        ExtensionClass.register()
        SecondExtensionClass.register()
        MoreExt.register()