import sys

sys.path.append("D:\\Programming\\Python\\Projects\\pyminecraft\\build\\libs\\pyminecraft-1.0.jar")

from rickaym.pyminecraft import Mod

from extension import ExtensionClass, SecondExtensionClass
from more_ext import MoreExt

@Mod(mod_id="abc")
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
        print "Main register called."
        ExtensionClass.register()
        SecondExtensionClass.register()
        MoreExt.register()